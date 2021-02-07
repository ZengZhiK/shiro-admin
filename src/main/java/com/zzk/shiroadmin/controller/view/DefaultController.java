package com.zzk.shiroadmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 默认视图 前端控制器
 *
 * @author zzk
 * @create 2021-02-07 11:48
 */
@Controller
public class DefaultController {
    @GetMapping({"/", "/index"})
    public String toLogin() {
        return "redirect:/view/login";
    }
}
