package com.rsa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.dto.UserPageDTO;
import com.rsa.entity.User;
import com.rsa.result.PageResult;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 获取用户头像
     * @return
     */
    String getUserLogo();

    /**
     * 更新用户在线状态
     */
    void updateUser();

    /**
     * 查询用户信息
     * @return
     */
    User getUserInfo();

    /**
     * 修改用户信息
     * @return
     */
    void updateUserBaseInfo(User user);

    /**
     * 修改用户密码
     * @param originPassword
     * @param newPassword
     * @return
     */
    void updateUserPassword(String originPassword, String newPassword);

    /**
     * 根据输入的密码验证用户真实性
     * @param password
     */
    void identifyByPassword(String password);

    /**
     * 分页查询用户管理
     * @param userPageDTO
     * @return
     */
    PageResult getUserByPage(UserPageDTO userPageDTO);

    /**
     * 更新用户账号状态
     * @param user
     * @return
     */
    void updateDeleting(User user);

    /**
     * 更新用户权限等级
     * @param user
     * @return
     */
    void updateValidate(User user);

    /**
     * 删除用户
     * @param userList
     * @return
     */
    void deleteUser(List<User> userList);

    /**
     * 添加用户
     * @param userList
     * @return
     */
    void addBatchUser(List<User> userList);

    /**
     * 批量更新用户
     * @param userList
     * @return
     */
    void updateBatch(List<User> userList);

    /**
     * 分页查询开发者
     * @param userPageDTO
     * @return
     */
    PageResult getAdminByPage(UserPageDTO userPageDTO);


    /**
     * 为用户邮箱发送验证码
     * @param email
     * @return
     */
    void sendEmailCode(String email, Integer type);

    /**
     * 检查邮箱绑定情况
     * @param user
     */
    void checkValidation(User user);

    /**
     * 注册账户
     * @param user
     */
    void sign(User user);

    /**
     * 用邮箱登录
     * @param user
     * @return
     */
    User emailLogin(User user);

    /**
     * 用户登录后，清除当前邮箱登录的记录
     */
    void removeUserEmailLoginValidation();

}
