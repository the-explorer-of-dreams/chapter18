package com.smart.web;

import com.smart.domain.Board;

import com.smart.domain.User;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;
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
    public void listAllBoards() throws Exception{//initial request and response
        this.request.setRequestURI("/index");
        this.request.setMethod("GET");

        ModelAndView mav = handlerAdapter.handle(request,response,this.forumManageController);
        List<Board> boards = (List<Board>) mav.getModelMap().get("boards");
        //assert the result
        assertNotNull(mav);
        assertEquals(mav.getViewName(), "/listAllBoards");
        assertNotNull(boards);
    }

    @Test
    public void addBoardPage()throws Exception {
        request.setRequestURI("/forum/addBoardPage");
        request.setMethod("GET");

        ModelAndView mav =  handlerAdapter.handle(request,response,this.forumManageController);
        assertEquals(mav.getViewName(), "/addBoard");
    }

    @Test
    public void addBoard()throws Exception {
        request.setRequestURI("/forum/addBoard");
        request.setMethod("POST");
        Board board = new Board();
        board.setBoardName("SpringMVC");
        board.setBoardDesc("SpringMVC经验~~");
        board.setTopicNum(0);
        String viewName = forumManageController.addBoard(board);
        assertEquals(viewName, "/addBoardSuccess");
    }


    @Test
    public void setBoardManagerPage()throws Exception {
        request.setRequestURI("/forum/setBoardManagerPage");
        request.setMethod("GET");

        ModelAndView mav = forumManageController.setBoardManagerPage();
        List<Board> boards = (List<Board>)mav.getModelMap().get("boards");
        List<User> users = (List<User>)mav.getModelMap().get("users");

        assertNotNull(mav);
        assertEquals(mav.getViewName(), "/setBoardManager");

        assertNotNull(boards);
        assertNotNull(users);
    }


    @Test
    public void setBoardManager()throws Exception {
        request.setRequestURI("/forum/setBoardManager");
        request.setMethod("POST");

        ModelAndView mav = forumManageController.setBoardManager("tom","1");
        assertNotNull(mav);
        assertEquals(mav.getViewName(), "/success");
    }


    @Test
    public void userLockManagePage()throws Exception {
        request.setRequestURI("/forum/userLockManagePage");
        request.setMethod("GET");
        ModelAndView mav = forumManageController.userLockManagePage();
        List<User> users = (List<User>)mav.getModelMap().get("users");

        assertNotNull(mav);
        assertNotNull(users);
        assertEquals(mav.getViewName(), "/userLockManage");
    }

    @Test
    public void userLockManage()throws Exception {
        request.setRequestURI("/forum/userLockManage");
        request.addParameter("locked", "1");
        request.addParameter("userName", "tom");
        request.setMethod("POST");

        ModelAndView mav = forumManageController.userLockManage("tom","1");
        assertNotNull(mav);
        assertEquals(mav.getViewName(), "/success");
    }


}
