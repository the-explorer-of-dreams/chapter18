package com.smart.web;

import com.smart.cons.CommonConstant;
import com.smart.domain.User;
import com.smart.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/doLogin")
    public ModelAndView login(HttpServletRequest request,User user){
        //prepare the MAV object
        ModelAndView mav = new ModelAndView();
        mav.setViewName("forward:/login.jsp");
        //verify the user info
        User dbUser = this.userService.getUserByUserName(user.getUserName());
        if(dbUser == null){
            mav.addObject("errorMsg","User does not exsit.");
        }else if(!dbUser.getPassword().equals(user.getPassword())){
            mav.addObject("errorMsg","The password is incorrect.");
        }else if(dbUser.getLocked() == User.USER_LOCK){
            mav.addObject("errorMsg","The user is locked.");
        }else{
            dbUser.setLastIp(request.getRemoteAddr());
            dbUser.setLastVisit(new Date());
            this.userService.loginSuccess(dbUser);

            //login success
            setSessionUser(request,dbUser);
            String toUrl = (String)request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
            request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);

            //if the toUrl is not null,reach to the url
            if(StringUtils.isEmpty(toUrl)){
                toUrl = "/index.html";
            }
            mav.setViewName("redirect:"+toUrl);
        }
        return mav;
    }

    /**
     * logout the user
     * @param session
     */
    public String logout(HttpSession session){
        session.removeAttribute(CommonConstant.USER_CONTEXT);
        return "forward:/index.jsp";
    }


}
