package com.smart.web;

import com.smart.domain.Board;
import com.smart.domain.User;
import com.smart.service.ForumService;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumManageController extends BaseController{
    private ForumService forumService;
    private UserService userService;
    @Autowired
    public void setForumService(ForumService forumService) {
        this.forumService = forumService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 列出所有论坛模块
     * @return  返回所有论坛模板
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView listAllBoards(){
        ModelAndView mav =  new ModelAndView();
        List<Board> allBoardsList = this.forumService.getAllBoard();
        mav.addObject("boards",allBoardsList);
        mav.setViewName("/listAllBoards");
        return mav;
    }

    /**
     * 指定管理员页面
     */
    @RequestMapping(value = "/forum/setBoardManagerPage", method = RequestMethod.GET)
    public ModelAndView setBoardManagerPage(){
        ModelAndView mav = new ModelAndView();
        List<Board> boards = this.forumService.getAllBoard();
        List<User> users = this.userService.getAllUsers();
        mav.addObject("boards", boards);
        mav.addObject("users", users);
        mav.setViewName("/setBoardManager");
        return mav;
    }

    /**
     * 设置版块管理
     */
    @RequestMapping(value = "/forum/setBoardManager", method = RequestMethod.POST)
    public ModelAndView setBoardManager(@RequestParam("userName") String userName,@RequestParam("boardId") String  boardId){
        ModelAndView mav = new ModelAndView();
        User user = this.userService.getUserByUserName(userName);
        if(user == null){
            mav.addObject("errorMsg", "用户名(" + userName
                    + ")不存在");
            mav.setViewName("/fail");
        }else{
            Board board = this.forumService.getBoardById(Integer.parseInt(boardId));
            user.getManBoards().add(board);
            this.userService.update(user);
            mav.setViewName("/success");
        }

        return  mav;
    }

    /**
     * 用户锁定及解锁管理页面
     * @return mav:/userLockManage
     */
    @RequestMapping(value = "/forum/userLockManagePage", method = RequestMethod.GET)
    public ModelAndView userLockManagePage() {
        ModelAndView view =new ModelAndView();
        List<User> users = userService.getAllUsers();
        view.setViewName("/userLockManage");
        view.addObject("users", users);
        return view;
    }

    /**
     * 用户锁定及解锁设定
     * @return mav
     */
    @RequestMapping(value = "/forum/userLockManage", method = RequestMethod.POST)
    public ModelAndView userLockManage(@RequestParam("userName") String userName
            ,@RequestParam("locked") String locked) {
        ModelAndView view =new ModelAndView();
        User user = userService.getUserByUserName(userName);
        if (user == null) {
            view.addObject("errorMsg", "用户名(" + userName
                    + ")不存在");
            view.setViewName("/fail");
        } else {
            user.setLocked(Integer.parseInt(locked));
            userService.update(user);
            view.setViewName("/success");
        }
        return view;
    }

    /**
     *  添加版块页面
     * @return
     */
    @RequestMapping(value = "/forum/addBoardPage", method = RequestMethod.GET)
    public String addBoardPage() {
        return "/addBoard";
    }

    /**
     * 添加一个版块
     * @param board
     * @return
     */
    @RequestMapping(value = "/forum/addBoard", method = RequestMethod.POST)
    public String addBoard(Board board) {
        forumService.addBoard(board);
        return "/addBoardSuccess";
    }

}
