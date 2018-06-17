package com.smart.web;


import com.smart.cons.CommonConstant;
import com.smart.domain.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ForumFilter implements Filter {
    private static final String FILTERED_REQUEST = "@@session_context_filtered_request";
    //define the resource urls without logging in
    private static final String[] INHERENT_ESCAPE_URIS = {
            "/index.jsp","/index.html","/login/doLogin.html","/register.jsp",
            "/register.html", "/board/listBoardTopics-", "/board/listTopicPosts-"
    };

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //make sure that a request is  filtered by only once;
        if(request != null && request.getAttribute(FILTERED_REQUEST) != null){
            chain.doFilter(request,response);
        }else{
            //sign a mark that it is filtered
            request.setAttribute(FILTERED_REQUEST,Boolean.TRUE);
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            User userInContext = getSessionUser(httpServletRequest);
            //judge the user login statu
            if(userInContext ==  null && !isURILogin(httpServletRequest.getRequestURI(),httpServletRequest)){
                String toUrl = httpServletRequest.getRequestURL().toString();
                if(!StringUtils.isEmpty(httpServletRequest.getQueryString())){
                    toUrl += "?" + httpServletRequest.getQueryString();
                }
                httpServletRequest.getSession().setAttribute(CommonConstant.LOGIN_TO_URL,toUrl);
                request.getRequestDispatcher("/login.jsp").forward(request,response);
                return;
            }
            chain.doFilter(request,response);
        }
    }

    protected User getSessionUser(HttpServletRequest request){
        return (User)request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
    }

    /**
     * 当前URL是否需要登录才能访问
     * @param requestUri
     * @param request
     * @return isReachable
     */

    private boolean isURILogin(String requestURI,HttpServletRequest request){
        if(request.getContextPath().equalsIgnoreCase(requestURI)
                ||(request.getContextPath()+"/").equalsIgnoreCase(requestURI)){
            return  true;
        }

        for (String uri: INHERENT_ESCAPE_URIS) {
            if(requestURI != null && requestURI.indexOf(uri) >= 0){
                return  true;
            }
        }

        return false;
    }

}
