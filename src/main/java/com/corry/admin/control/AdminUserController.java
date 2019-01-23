package com.corry.admin.control;

import com.corry.admin.pojo.User;
import com.corry.admin.service.UserMapper;
import com.corry.base.control.BaseController;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Controller
public class AdminUserController extends BaseController {
    public  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/admin.do")
    private void amdin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        gotoLogin(request,response);
    }

    /**
     * GET请求登录
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/admin/login.do",method = RequestMethod.GET)
    private void toLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("get方式登录");
        gotoLogin(request,response);
    }
    /**
     * post请求登录
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/admin/login.do",method = RequestMethod.POST)
    public String toLogin(User user, Boolean rememberme, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        // 如果已经登录，则跳转到管理首页
        if (isLogin(request)) {
            gotoLogin(request,response);
        }
        //return redirectIndex(request, local, companyCode);//"redirect:/index?url="+ request.getParameter("url");
        //设置locale,companyCode放入到index里处理。
        return "admin/login";
    }
    @RequestMapping("/admin/perms/test.do")
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

    public static void gotoLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{

        WebUtils.issueRedirect(request,response,"login.jsp",null,false,true);
        return;
    }

    private void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        String loginUrl = "login.jsp";
        WebUtils.issueRedirect(request, response, loginUrl,null,false,true);
        return;
    }
}
