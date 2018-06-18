package com.smart.web;

import com.smart.domain.Board;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByType;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ForumManageControllerTest extends BaseWebTest{
    @SpringBeanByType
    private ForumManageController forumManageController;

    /**
     * index page : load all boards
     */
    @Test
    public void listAllBoards() throws Exception{
        //initial request and response
        this.request.setRequestURI("/index");
        this.request.setMethod("GET");
        //call handlerAdaptor
        ModelAndView mav = (ModelAndView) handlerAdapter.handle(request,response,handlerAdapter.);
        List<Board> boards = (List<Board>) mav.getModelMap().get("boards");

        //assert the result
        assertNotNull(mav);
        assertEquals(mav.getViewName(), "/listAllBoards");
        assertNotNull(boards);
    }
}
