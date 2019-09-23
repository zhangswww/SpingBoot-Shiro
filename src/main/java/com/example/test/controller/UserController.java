package com.example.test.controller;


import com.example.test.entity.User;
import com.example.test.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/doLogin")
    public String doLogin(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                          @RequestParam(name = "rememberMe", required = false) Integer rememberMe) {
        System.err.println("走这里了！！！");
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            System.out.println("checkbox" + rememberMe);
            if (rememberMe != null && rememberMe == 1) {
                System.out.println("记住了");
                token.setRememberMe(true);
            }
            subject.login(token);
            System.out.println("登录成功!");
            return "redirect:index";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败!");
            return "redirect:login";
        }
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model, HttpSession session) {
        session.setAttribute("test", "value========>>>");

        List<User> list = userService.list();
        model.addAttribute("list", list);
        return "index";
    }

    @RequestMapping("/unauthorizedurl")
    public String unauthorizedurl() {
        return "unauthorizedurl";
    }


//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping("/admin")
    public String admin() {
        return "this is admin access";
    }

    @RequiresRoles({"user"})
    @ResponseBody
    @RequestMapping("/user")
    public String user() {
        return "this is user access";
    }

    @RequiresPermissions({"admin:view:all"})
    @ResponseBody
    @RequestMapping("/all")
    public String all() {
        return "this is user all";
    }

    @RequiresPermissions({"user:view:1"})
    @ResponseBody
    @RequestMapping("/view1")
    public String view1() {
        return "this is user view1";
    }
}
