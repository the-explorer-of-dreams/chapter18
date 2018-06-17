package com.smart.web;

import com.smart.domain.User;
import com.smart.exception.UserExistException;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController extends BaseController{
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * register user
     *
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView register(HttpServletRequest request,User user){
        ModelAndView mav = new ModelAndView();
        try {
            this.userService.register(user);
            mav.setViewName("/success");
            setSessionUser(request,User);
        }catch(UserExistException e){
            mav.addObject("errorMsg", "UserName has already existed");
            mav.setViewName("forward:/register.jsp");
        }

        return mav;

    }
}
