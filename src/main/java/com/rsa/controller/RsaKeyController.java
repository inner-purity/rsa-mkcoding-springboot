package com.rsa.controller;

import com.rsa.dto.RsaKeyDTO;
import com.rsa.dto.RsaKeyPageDTO;
import com.rsa.result.PageResult;
import com.rsa.result.Result;
import com.rsa.entity.RsaKey;
import com.rsa.service.RsaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 密钥相关接口
 */
@RestController
@RequestMapping("/users/rsaKey")
@Slf4j
public class RsaKeyController {
    @Autowired
    RsaService rsaService;

    /**
     * 上传要保存的RSA密钥生成记录
     *
     * @param rsaKeyDTO
     * @return
     */
    @PostMapping("/upload")
    public Result uploadRsaKey(@RequestBody RsaKeyDTO rsaKeyDTO) {
        rsaService.uploadRsaKey(rsaKeyDTO);
        return Result.success("保存成功");
    }

    /**
     * 获取用户RSA密钥数据记录
     *
     * @return
     */
    @GetMapping("/getUserRsaKey")
    public Result<List<RsaKey>> getUserRsaKey() {
        List<RsaKey> rsaKeyList = rsaService.getUserRsaKeyByUserId();
        return Result.success(rsaKeyList);
    }

    /**
     * 删除用户指定的RSA密钥数据
     *
     * @param rsaKeyList
     * @return
     */
//    注意此处不能用Delete,因为要传数据给后端，而Get和Delete请求不支持前端axios传递"Content-Type": "multipart/form-data"的数据
//    而且注意此处不能用@RequestParam，因为这个是代表前端需要传"Content-Type": "multipart/form-data"数据，
//    并且append,append,....传来的本质还是个数组只不过后端转成了List格式，如果想直接传数组而不是一个个参数，应该还是选用Json体传数据
    @PostMapping("/deleteUserRsaKeys")
    public Result deleteUserRsaKeys(@RequestBody List<RsaKey> rsaKeyList) {
        rsaService.deleteBatchIds(rsaKeyList);
        return Result.success("删除成功");
    }

    /**
     * 删除用户指定的单条RSA密钥数据
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteUserRsaKey")
    public Result deleteUserRsaKey(@RequestParam Integer id) {
        rsaService.deleteByUserId(id);
        return Result.success("删除成功");
    }

    /**
     * 分页查询密钥数据
     * @param rsaKeyPageDTO
     * @return
     */
    @PostMapping("/pageRsaKey")
    public Result<PageResult> pageQueryRsaKey(@RequestBody RsaKeyPageDTO rsaKeyPageDTO) {
        PageResult pageResult = rsaService.pageQueryRsaKey(rsaKeyPageDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取单条密钥数据
     * @param id
     * @return
     */
    @GetMapping("/getUserOnceRsaKey/{id}")
    public Result<RsaKey> getUserOnceRsaKey(@PathVariable Integer id){
        RsaKey result = rsaService.getUserRsaKeyByKeyId(id);
        return Result.success(result);
    }
}