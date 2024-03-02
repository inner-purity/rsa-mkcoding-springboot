package com.rsa.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsa.annotation.LogDataException;
import com.rsa.common.Code;
import com.rsa.common.ExceptionMessage;
import com.rsa.common.exception.BusinessException;
import com.rsa.context.BaseContext;
import com.rsa.dto.UserPageDTO;
import com.rsa.entity.User;
import com.rsa.mapper.UserMapper;
import com.rsa.result.PageResult;
import com.rsa.service.UserService;
import com.rsa.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    //插件中所含的bean,用于抄送邮件
    @Autowired
    @Qualifier("qqMailSender")
    private JavaMailSender qqMailSender;

    @Autowired
    @Qualifier("_163MailSender")
    private JavaMailSender _163MailSender;

    @Autowired
    @Qualifier("_126MailSender")
    private JavaMailSender _126MailSender;

    @Autowired
    ValidationService validationService;

    @Value("${spring.qq.mail.username}")
    private String qqEmailFrom;

    @Value("${spring.mail163.mail.username}")
    private String _163EmailFrom;

    @Value("${spring.mail126.mail.username}")
    private String _126EmailFrom;
    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @LogDataException
    @Override
    public User login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        User exeamUser = userMapper.getByUsername(username);

        if (exeamUser == null) {
            throw new BusinessException(Code.GET_ERR, ExceptionMessage.NO_SUCH_USER);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(exeamUser.getPassword())) {
            throw new BusinessException(Code.GET_ERR, ExceptionMessage.ERROR_PASSWORD);
        }

        if (exeamUser.getDeleting() == 1) {
            throw new BusinessException(Code.GET_ERR, ExceptionMessage.USER_HAD_BEEN_DELETED_OR_BANNED);
        }

        exeamUser.setLastLoginTime(LocalDateTime.now());
        exeamUser.setStatus(1);
        userMapper.updateById(exeamUser);


        exeamUser.setPassword("******");
        exeamUser.setInviteKey("******");
        log.info("返回用户数据：{}", exeamUser);
        return exeamUser;
    }

    /**
     * 为用户邮箱发送验证码
     *
     * @param email
     * @return
     */
    @LogDataException
    @Override
    public void sendEmailCode(String email, Integer type) {
        Date now = new Date();
        Long checkedId = validationService.checkSame(email, type, now);
        if (checkedId != null) {
            throw new BusinessException(Code.EMAIL_CODE_HAD_BEEN_SEND, ExceptionMessage.EMAIL_CODE_HAD_BEEN_SEND);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        if (email.endsWith("@qq.com")) {
            message.setFrom(qqEmailFrom);
        } else if (email.endsWith("@163.com")) {
            message.setFrom(_163EmailFrom);
        } else if (email.endsWith("@126.com")) {
            message.setFrom(_126EmailFrom);
        }else {
            throw new BusinessException(Code.EMAIL_TYPE_NOT_ALLOWED,ExceptionMessage.EMAIL_TYPE_NOT_ALLOWED);
        }
        message.setTo(email);
        message.setSentDate(now);
        message.setSubject("【RSA-mkCoding】账户 登录/注册 邮箱验证");
        String code = RandomUtil.randomNumbers(5);//随机一个四位数长度验证码;
        message.setText("您本次操作的验证码是：\n \n " + code + "\n \n 请妥善保管,切勿泄露！ 有效期5分钟。 \n \n RSA-mkCoding平台欢迎您");
        if (email.endsWith("@qq.com")) {
            qqMailSender.send(message);
        } else if (email.endsWith("@163.com")) {
            _163MailSender.send(message);
        } else if (email.endsWith("@126.com")) {
            _126MailSender.send(message);
        }

        validationService.save(email, code, type, DateUtil.offsetMinute(now, 5));//当前时间向后推迟5分钟，实现5分钟过期的时间验证
    }

    /**
     * 检查邮箱绑定情况
     *
     * @param user
     */
    @LogDataException
    @Override
    public void checkValidation(User user) {
        validationService.getValidation(user);
    }

    /**
     * 注册账户
     *
     * @param user
     */
    @LogDataException
    @Override
    public void sign(User user) {
        //验证用户名和邮箱的唯一性
        if (userMapper.getByUsername(user.getUsername()) != null) {
            throw new BusinessException(Code.USER_NOT_UNIQUE,ExceptionMessage.USER_NOT_UNIQUE);
        } else if (userMapper.getByEmail(user.getEmail()) != null) {
            throw new BusinessException(Code.USER_NOT_UNIQUE,ExceptionMessage.EMAIL_NOT_UNIQUE);
        }

        //验证邮箱绑定状态
        checkValidation(user);

        //正式注册账户

        //随机获取一个头像
        List<String> list = new ArrayList<>();
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfeQ49ur9TjhQsMs9e80_kaHHkQD2OBY_buw&usqp=CAU");
        list.add("https://img.58tg.com/up/allimg/tx29/08151110374049662.png");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo5D" +
                "_7hzsxXVz8s5C4NEHqAv8C1Aak0NHv9w2Wyy4xADreK6fzNhm_tPefDS7LBLlNCsM&usqp=CAU");
        user.setUserLogo(list.get(new Random().nextInt(3)));
        //对密码进行md5加密
        String md5_password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5_password);
        //插入用户
        userMapper.insert(user);
    }

    /**
     * 用邮箱登录
     *
     * @param user
     * @return
     */
    @Override
    public User emailLogin(User user) {
        checkValidation(user);
        User result = userMapper.getByEmail(user.getEmail());
        if (result == null) {
            validationService.removeByEmailAndType(user);
            throw new BusinessException(Code.NO_SUCH_EMAIL_USER,ExceptionMessage.NO_SUCH_EMAIL_USER);
        }
        result.setLastLoginTime(LocalDateTime.now());
        result.setStatus(1);
        userMapper.updateById(result);

        result.setPassword("******");
        result.setInviteKey("******");
        log.info("返回用户数据：{}", result);
        return result;
    }

    /**
     * 用户登录后，清除当前邮箱登录的记录
     */
    @Override
    public void removeUserEmailLoginValidation() {
        User user = userMapper.getById(BaseContext.getCurrentId());
        validationService.removeByEmailAndType(user);
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    @Override
    @LogDataException
    public String getUserLogo() {
        Integer id = BaseContext.getCurrentId();
        User user = userMapper.getById(id);
        return user.getUserLogo();
    }

    /**
     * 更新用户在线状态
     */
    @Override
    @LogDataException
    public void updateUser() {
        Integer userId = BaseContext.getCurrentId();
        User user = new User();
        user.setId(userId);
        user.setStatus(0);
        userMapper.updateById(user);
    }

    /**
     * 查询用户信息
     *
     * @return
     */
    @Override
    @LogDataException
    public User getUserInfo() {
        Integer userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);
        user.setPassword("******");
        user.setInviteKey("******");
        return user;
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    @Override
    @LogDataException
    public void updateUserBaseInfo(User user) {
        user.setId(BaseContext.getCurrentId());
        User originUser = userMapper.getByUsername(user.getUsername());
        if (originUser != null) {
            throw new BusinessException(Code.USER_NOT_UNIQUE, ExceptionMessage.USER_NOT_UNIQUE);
        }
        userMapper.updateById(user);
    }


    /**
     * 修改用户密码
     *
     * @param originPassword
     * @param newPassword
     * @return
     */
    @Override
    @LogDataException
    public void updateUserPassword(String originPassword, String newPassword) {
        User originUser = userMapper.getById(BaseContext.getCurrentId());
        String md5OriginPassword = DigestUtils.md5DigestAsHex(originPassword.getBytes());
        if (!originUser.getPassword().equals(md5OriginPassword)) {
            throw new BusinessException(Code.ERROR_PRIOR_PASSWORD, ExceptionMessage.ERROR_PRIOR_PASSWORD);
        }

        User newUser = new User();
        newUser.setId(BaseContext.getCurrentId());
        String md5NewPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        newUser.setPassword(md5NewPassword);
        userMapper.updateById(newUser);
    }

    /**
     * 根据输入的密码验证用户真实性
     */
    @Override
    @LogDataException
    public void identifyByPassword(String password) {
        User user = userMapper.getById(BaseContext.getCurrentId());
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        log.info("password:{}", password);
        log.info("md5password:{}", md5Password);
        if (!user.getPassword().equals(md5Password)) {
            log.info("user.getPassword:{}", user.getPassword());
            throw new BusinessException(Code.CHECK_PASSWORD_ERROR, ExceptionMessage.CHECK_PASSWORD_ERROR);
        }
    }

    /**
     * 分页查询用户管理
     *
     * @param userPageDTO
     * @return
     */
    @Override
    @LogDataException
    public PageResult getUserByPage(UserPageDTO userPageDTO) {
        PageHelper.startPage(userPageDTO.getPage(), userPageDTO.getPageSize());

        Page<User> userPage = userMapper.pageQuery(userPageDTO);
        Long total = userPage.getTotal();
        List<User> result = userPage.getResult();
        log.info("result:{},getResult:{}", result, userPage.getResult());
        PageResult pageResult = new PageResult(total, result);
        return pageResult;
    }

    /**
     * 更新用户账号状态
     *
     * @param user
     * @return
     */
    @Override
    @LogDataException
    public void updateDeleting(User user) {
        User updateUser = userMapper.getById(BaseContext.getCurrentId());
        user.setUpdateUser(updateUser.getUsername());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    /**
     * 更新用户权限等级
     *
     * @param user
     * @return
     */
    @Override
    @LogDataException
    public void updateValidate(User user) {
        User checkUser = userMapper.getById(BaseContext.getCurrentId());
        if (checkUser.getValidate() < 2) {
            //如果小于2，那证明是用户或者平台开发者，当然用户正常情况下不可能调用到该接口
            throw new BusinessException(Code.NO_AUTH_TO_GIVE_USER_AUTH, ExceptionMessage.NO_AUTH_TO_GIVE_USER_AUTH);
        }
        userMapper.updateById(user);
    }


    /**
     * 删除用户
     *
     * @param userList
     * @return
     */
    @Override
    @LogDataException
    public void deleteUser(List<User> userList) {
        for (User user : userList) {
            userMapper.deleteById(user);
        }
    }


    /**
     * 添加用户
     *
     * @param userList
     * @return
     */
    @Override
    @LogDataException
    public void addBatchUser(List<User> userList) {
        List<String> list = new ArrayList<>();
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfeQ49ur9TjhQsMs9e80_kaHHkQD2OBY_buw&usqp=CAU");
        list.add("https://img.58tg.com/up/allimg/tx29/08151110374049662.png");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo5D" +
                "_7hzsxXVz8s5C4NEHqAv8C1Aak0NHv9w2Wyy4xADreK6fzNhm_tPefDS7LBLlNCsM&usqp=CAU");
        User updateUser = userMapper.getById(BaseContext.getCurrentId());
        userList.forEach(user -> {
            if (user.getUsername() != null) {
                if (userMapper.getByUsername(user.getUsername()) != null) {
                    throw new BusinessException(Code.USER_NOT_UNIQUE, ExceptionMessage.USER_NOT_UNIQUE);
                }
            }
            if (user.getUsername() == null) {
                do {
                    user.setUsername("用户" + UUID.randomUUID().toString().substring(0, 10));
                } while ((userMapper.getByUsername(user.getUsername()) != null));
            }
            if (user.getPassword() == null) {
                user.setPassword(DigestUtils.md5DigestAsHex("rsa123456".getBytes()));
            }
            if (user.getUserLogo() == null) {
                user.setUserLogo(list.get(new Random().nextInt(3)));
            }

            user.setCreateTime(LocalDateTime.now());
            user.setUpdateUser(updateUser.getUsername());

            userMapper.insert(user);
        });
    }

    /**
     * 批量更新用户
     *
     * @param userList
     * @return
     */
    @Override
    @LogDataException
    public void updateBatch(List<User> userList) {
        User updateUser = userMapper.getById(BaseContext.getCurrentId());
        userList.forEach(user -> {
            user.setUpdateUser(updateUser.getUsername());
            user.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(user);
        });
    }

    /**
     * 分页查询开发者
     *
     * @param userPageDTO
     * @return
     */
    @Override
    @LogDataException
    public PageResult getAdminByPage(UserPageDTO userPageDTO) {
        PageHelper.startPage(userPageDTO.getPage(), userPageDTO.getPageSize());
        Page<User> userPage = userMapper.adminPageQuery(userPageDTO);
        Long total = userPage.getTotal();
        List<User> result = userPage.getResult();
        log.info("result:{},getResult:{}", result, userPage.getResult());
        PageResult pageResult = new PageResult(total, result);
        return pageResult;
    }
}
