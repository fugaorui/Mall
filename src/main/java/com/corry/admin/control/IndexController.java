package com.corry.admin.control;

import com.corry.base.control.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("")
public class IndexController extends BaseController {

    @RequestMapping("/login.do")
    private String test(HttpServletRequest request, HttpServletResponse response){
        System.out.println("-------ssl-----");
        return "login";
    }
}
