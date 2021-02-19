package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.annotation.LogSave;
import com.zzk.shiroadmin.common.constant.Constants;
import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.utils.AjaxResponse;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import com.zzk.shiroadmin.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件 前端控制器
 *
 * @author zzk
 * @create 2021-02-19 15:55
 */
@RestController
@RequestMapping("/api/file")
@Api(tags = "文件模块相关接口")
public class FileController {
    @Autowired
    private FileService fileService;

    @LogSave(title = "文件模块", action = "文件上传接口")
    @LogPrint(description = "文件上传接口")
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传接口")
    public AjaxResponse upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        String userId = JwtTokenUtils.getUserId(request.getHeader(JwtConstants.ACCESS_TOKEN));
        Integer type = request.getIntHeader(Constants.FILE_TYPE);
        fileService.upload(file, userId, type);
        return AjaxResponse.success();
    }
}
