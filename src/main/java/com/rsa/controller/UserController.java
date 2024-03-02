package com.rsa.controller;

import com.rsa.annotation.Validate;
import com.rsa.dto.UserPageDTO;
import com.rsa.result.PageResult;
import com.rsa.result.Result;
import com.rsa.common.properties.JwtProperties;
import com.rsa.common.utils.JwtUtil;
import com.rsa.entity.User;
import com.rsa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @Value("${rsa.app-jwt.ttl}")
    private Long appJwtTtl;
    /**
     * 用户账号密码登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        log.info("用户登录：{}", user);
        User loginUser = userService.login(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        log.info("claims_put_id:{}", claims);
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );
        loginUser.setToken(token);
        return Result.success(loginUser);
    }

    /**
     * app端用户账号密码登录
     *
     * @param user
     * @return
     */
    @PostMapping("/appLogin")
    public Result<User> appLogin(@RequestBody User user) {
        log.info("用户登录：{}", user);
        User loginUser = userService.login(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        log.info("claims_put_id:{}", claims);
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                appJwtTtl,
                claims
        );
        loginUser.setToken(token);
        return Result.success(loginUser);
    }

    /**
     * 用户邮箱登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login/emailLogin")
    public Result<User> emailLogin(@RequestBody User user) {
        log.info("用户登录：{}", user);
        User loginUser = userService.emailLogin(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        log.info("claims_put_id:{}", claims);
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );
        loginUser.setToken(token);
        return Result.success(loginUser);
    }

    /**
     * app用户邮箱登录
     *
     * @param user
     * @return
     */
    @PostMapping("/appLogin/emailLogin")
    public Result<User> appEmailLogin(@RequestBody User user) {
        log.info("用户登录：{}", user);
        User loginUser = userService.emailLogin(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        log.info("claims_put_id:{}", claims);
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                appJwtTtl,
                claims
        );
        loginUser.setToken(token);
        return Result.success(loginUser);
    }

    /**
     * 用户登录后，清除当前邮箱登录的记录
     */
    @PostMapping("/removeValidation")
    public void removeValidation(){
        userService.removeUserEmailLoginValidation();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/sign")
    public Result sign(@RequestBody User user) {
        userService.sign(user);
        return Result.success("注册成功!请登录");
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    @GetMapping("/getUserLogo")
    public Result<String> getUserLogo() {
        String logoURL = userService.getUserLogo();
        return Result.success(logoURL);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PutMapping("/exit")
    public Result exit() {
        userService.updateUser();
        return Result.success();
    }

    /**
     * 获取用户基本信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result<User> getUserInfo() {
        User user = userService.getUserInfo();
        return Result.success(user);
    }

    /**
     * 修改用户信息
     * @return
     */
    @PutMapping("/updateUserBaseInfo")
    public Result updateUserBaseInfo(@RequestBody User user){
        userService.updateUserBaseInfo(user);
        return Result.success("个人信息更新成功");
    }

    /**
     * 修改用户密码
     * @param originPassword
     * @param newPassword
     * @return
     */
    @PutMapping("/updateUserPassword")
    public Result updateUserPassword(String originPassword,String newPassword){
        userService.updateUserPassword(originPassword,newPassword);
        return Result.success("密码更改成功");
    }

    /**
     * 根据输入的密码验证用户真实性
     */
    @PostMapping("/identifyByPassword")
    public Result identifyByPassword(@RequestParam String password){
        userService.identifyByPassword(password);
        return Result.success();
    }

    /**
     * 分页查询管理用户
     * @param userPageDTO
     * @return
     */
    @PostMapping("/getUserByPage")
    public Result<PageResult> getUserByPage(@RequestBody UserPageDTO userPageDTO){
        PageResult pageResult = userService.getUserByPage(userPageDTO);
        return Result.success(pageResult,"");
    }

    /**
     * 更新用户账号状态
     * @param user
     * @return
     */
    @Validate
    @PutMapping("/updateUserDeleting")
    public Result updateUserDeleting(@RequestBody User user){
        userService.updateDeleting(user);
        return Result.success("更新成功");
    }

    /**
     * 更新用户权限等级
     * @param user
     * @return
     */
    @Validate
    @PutMapping("/updateUserValidate")
    public Result updateUserValidate(@RequestBody User user){
        userService.updateValidate(user);
        return Result.success("更新成功");
    }

    /**
     * 删除用户
     * @param userList
     * @return
     */
    @Validate
    @PostMapping("/deleteBatchUser")
    public Result deleteUser(@RequestBody List<User> userList){
        userService.deleteUser(userList);
        return Result.success("删除成功");
    }

    /**
     * 添加用户
     * @param userList
     * @return
     */
    @Validate
    @PostMapping("/addBatchUser")
    public Result addBatchUser(@RequestBody List<User> userList){
        userService.addBatchUser(userList);
        return Result.success("添加成功!");
    }

    /**
     * 批量更新用户
     * @param userList
     * @return
     */
    @Validate
    @PutMapping("/updateBatch")
    public Result updateBatch(@RequestBody List<User> userList){
        userService.updateBatch(userList);
        return Result.success("更新成功!");
    }

    /**
     * 分页查询开发者
     * @param userPageDTO
     * @return
     */
    @PostMapping("/getAdminByPage")
    public Result<PageResult> getAdminByPage(@RequestBody UserPageDTO userPageDTO){
        PageResult pageResult = userService.getAdminByPage(userPageDTO);
        return Result.success(pageResult,"");
    }
}
