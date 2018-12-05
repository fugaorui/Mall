package com.mall.admin.control;

import com.mall.admin.service.UserMapper;
import com.mall.base.control.BaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/admin")
public class AdminUserController extends BaseController {

    Log log = LogFactory.getLog(getClass());
    public  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login.do")
    private String toLogin(HttpServletRequest request, HttpServletResponse response){

        return "admin/login";
    }
    @RequestMapping("/perms/test.do")
    private void perms(HttpServletRequest request, HttpServletResponse response){
        System.out.println("-------perms-----");

    }
    @RequestMapping("/port/test.do")
    private void port(HttpServletRequest request, HttpServletResponse response){
        System.out.println("-------port-----");

    }
    @RequestMapping("/ssl/test.do")
    private void ssl(HttpServletRequest request, HttpServletResponse response){
        System.out.println("-------ssl-----");

    }
    @RequestMapping("/user/test.do")
        private void saveUser(HttpServletRequest request, HttpServletResponse response){
        System.out.println("-------user-----");

    }
}
