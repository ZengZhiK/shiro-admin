package com.zzk.shiroadmin.controller.api;

import com.zzk.shiroadmin.common.annotation.LogPrint;
import com.zzk.shiroadmin.common.constant.JwtConstants;
import com.zzk.shiroadmin.common.utils.JwtTokenUtils;
import com.zzk.shiroadmin.model.vo.resp.HomeRespVO;
import com.zzk.shiroadmin.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 主页 前端控制器
 *
 * @author zzk
 * @create 2021-01-01 21:38
 */
@RestController
@RequestMapping("/api/home")
@Api(tags = "主页模块相关接口")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @LogPrint(description = "主页数据获取接口")
    @ApiOperation(value = "主页数据获取接口")
    @GetMapping
    public HomeRespVO getHomeInfo(HttpServletRequest request) {
        String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
        // 通过access_token拿userId
        String userId = JwtTokenUtils.getUserId(accessToken);

        return homeService.getHomeInfo(userId);
    }
}
