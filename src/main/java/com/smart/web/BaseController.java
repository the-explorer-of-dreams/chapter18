package com.smart.web;

import com.smart.cons.CommonConstant;
import com.smart.domain.User;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    protected static final String ERROR_MSG_KEY  = "errorMsg";

    /**
     * get the user in the current session
     * @param request
     */
    protected User getSessionUser(HttpServletRequest request){
        return (User)request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
    }

    /**
     * save the current user to the session
     * @param request
     * @param user
     */
    protected void setSessionUser(HttpServletRequest request,User user){
        request.getSession().setAttribute(CommonConstant.USER_CONTEXT,user);
    }

    /**
     * get the absolutely url
     * @param request
     * @param url
     */
    public final String getAppbaseUrl(HttpServletRequest request,String url){
        Assert.hasText(url);
        Assert.isTrue(url.startsWith("/"),"必须以/开头");
        return request.getContextPath() + url;
    }}
