package com.rsa.controller;

import com.rsa.annotation.Validate;
import com.rsa.dto.RequestPathPageDTO;
import com.rsa.result.PageResult;
import com.rsa.result.Result;
import com.rsa.entity.RequestPath;
import com.rsa.service.RequestPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestPathService requestPathService;

    /**
     * 分页查询所有接口路径
     * @param requestPathPageDTO
     * @return
     */
    @PostMapping("/getRequestPathByPage")
    public Result<PageResult> getRequestPathByPage(@RequestBody RequestPathPageDTO requestPathPageDTO) {
        PageResult pageResult = requestPathService.getRequestPathByPage(requestPathPageDTO);
        return Result.success(pageResult,"查询成功");
    }

    /**
     * 更新接口路径参数
     * @param requestPath
     * @return
     */
    @Validate
    @PutMapping("/updateRequestPath")
    public Result updateRequestPath(@RequestBody RequestPath requestPath){
        requestPathService.updateRequestPath(requestPath);
        return Result.success("更改成功");
    }


    /**
     * 删除单条或批量删除接口路径配置
     * @param requestPathList
     * @return
     */
    @Validate
    @PostMapping("/deleteBatchRequestPath")
    public Result deleteBatchRequestPath(@RequestBody List<RequestPath> requestPathList){
            requestPathService.deleteBatch(requestPathList);
        return Result.success("已删除");
    }


    /**
     *添加接口配置
     * @param requestPath
     * @return
     */
    @Validate
    @PostMapping("/addRequestPath")
    public Result addRequestPath(@RequestBody RequestPath requestPath){
        requestPathService.addRequestPath(requestPath);
        return Result.success("添加成功");
    }


    /**
     * 批量更新接口配置
     * @param requestPathList
     * @return
     */
    @Validate
    @PutMapping("/updateBatch")
    public Result updateBatch(@RequestBody List<RequestPath> requestPathList){
        requestPathService.updateBatch(requestPathList);
        return Result.success("更新成功");
    }
}
