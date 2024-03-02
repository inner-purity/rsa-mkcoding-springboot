package com.rsa.controller;

import com.rsa.dto.RsaKeyQrcodeDTO;
import com.rsa.entity.RsaKeyQrcode;
import com.rsa.result.Result;
import com.rsa.common.utils.AliOssUtil;
import com.rsa.service.RsaKeyQrcodeService;
import com.rsa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/users/common")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RsaKeyQrcodeService rsaKeyQrcodeService;

    @PostMapping("/uploadImage")
    public Result<String> uploadImage(MultipartFile file) {
        log.info("图片上传： {}",file);
        String originalFilename = file.getOriginalFilename();
        String fillPath = null;
        try {
            fillPath = aliOssUtil.uploadImage(file.getBytes(), UUID.randomUUID().toString()
                    + originalFilename.substring(originalFilename.lastIndexOf(".")));
            return Result.success(fillPath,"图片上传成功");
        } catch (IOException e) {
            log.error("文件上传失败: {}",e);
        }
        return Result.error("图片上传失败");
    }

    @PostMapping("/uploadText")
    public Result<String> uploadText(@RequestParam String text) {
        log.info("文件上传： {}",text);
        String originalFilename = text;
        String fillPath = null;
        try {
            fillPath = aliOssUtil.uploadText(text, UUID.randomUUID().toString());
            return Result.success(fillPath,"文件上传成功");
        } catch (Exception e) {
            log.error("文件上传失败: {}",e);
        }
        return Result.error("文件上传失败");
    }

    @PostMapping("/downloadText")
    public Result<String> downloadText(@RequestParam String url) {
        log.info("文件下载： {}",url);
        String key = null;
        try {
            key = aliOssUtil.downloadText(url);
            return Result.success(key,"文件下载成功");
        } catch (Exception e) {
            log.error("文件下载失败: {}",e);
        }
        return Result.error("文件下载失败");
    }

    /**
     * 为用户邮箱发送验证码
     * @param email
     * @return
     */
    @GetMapping("/sendEmailCode/{email}/{type}")
    public Result sendEmailCode(@PathVariable String email, @PathVariable Integer type){
        userService.sendEmailCode(email, type);
        return Result.success("验证码发送成功");
    }

    /**
     * 插入限时二维码并返回随机访问路径码
     * @param rsaKeyQrcode
     * @return
     */
    @PostMapping("/getUnicode")
    public Result<String> getUnicode(@RequestBody RsaKeyQrcode rsaKeyQrcode){
        String unicode = rsaKeyQrcodeService.insert(rsaKeyQrcode);
        return Result.success(unicode,"");
    }

    /**
     * 获取二维码携带的密钥数据
     * @param unicode
     * @return
     */
    @GetMapping("/getQrData/{unicode}")
    public Result<RsaKeyQrcodeDTO> getQrData(@PathVariable String unicode){
        RsaKeyQrcodeDTO rsaKeyQrcodeDTO = rsaKeyQrcodeService.getByUnicode(unicode);
        return Result.success(rsaKeyQrcodeDTO,"密钥数据传输成功");
    }

}

