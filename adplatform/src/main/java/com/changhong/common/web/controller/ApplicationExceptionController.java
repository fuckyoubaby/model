package com.changhong.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-24
 * Time: 上午9:30
 */
@Controller
public class ApplicationExceptionController {

    @RequestMapping("/error.html")
    protected ModelAndView handleErrorRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("error");
    }

    @RequestMapping("/backend/error.html")
    protected ModelAndView handleErrorRequest1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("error");
    }
    
    @RequestMapping("/backend/static/error.html")
    protected ModelAndView handleErrorRequest2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("error");
    }
}
