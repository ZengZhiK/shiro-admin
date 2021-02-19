package com.zzk.shiroadmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图 前端控制器
 *
 * @author zzk
 * @create 2020-12-30 14:44
 */
@Controller
@RequestMapping("/view")
public class ViewController {
    /**
     * 跳转至403错误页面
     */
    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    /**
     * 跳转至404错误页面
     */
    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }

    /**
     * 跳转至500错误页面
     */
    @GetMapping("/500")
    public String error500() {
        return "error/500";
    }

    /**
     * 跳转至登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转至主页页面
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * 获取主页主体内容
     */
    @GetMapping("/main")
    public String main() {
        return "main";
    }

    /**
     * 跳转至菜单权限管理页面
     */
    @GetMapping("/menus")
    public String menu() {
        return "menus/menu";
    }

    /**
     * 跳转至角色管理页面
     */
    @GetMapping("/roles")
    public String role() {
        return "roles/role";
    }

    /**
     * 跳转至部门管理页面
     */
    @GetMapping("/depts")
    public String dept() {
        return "depts/dept";
    }

    /**
     * 跳转至用户管理页面
     */
    @GetMapping("/users")
    public String user() {
        return "users/user";
    }

    /**
     * 跳转至日志管理页面
     */
    @GetMapping("/logs")
    public String log() {
        return "logs/log";
    }

    /**
     * 跳转至用户信息页面
     */
    @GetMapping("/user/info")
    public String userInfo() {
        return "users/user_edit";
    }

    /**
     * 跳转至密码修改页面
     */
    @GetMapping("/user/pwd")
    public String userPwd() {
        return "users/pwd_edit";
    }

    /**
     * 跳转至轮播图管理页面
     */
    @GetMapping("/rotations")
    public String rotations() {
        return "rotations/rotation";
    }

    /**
     * 跳转至轮播图前台展示页面
     */
    @GetMapping("/rotations/show")
    public String rotationShow() {
        return "rotations/rotation_show";
    }

    /**
     * 跳转至我的文件页面
     */
    @GetMapping("/files")
    public String file() {
        return "files/my_file";
    }
}
