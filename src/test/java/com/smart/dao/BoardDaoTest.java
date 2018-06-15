package com.smart.dao;

import com.smart.domain.Board;
import com.smart.test.dataset.util.XlsDataSetBeanFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import java.util.List;

public class BoardDaoTest extends BaseDaoTest{
    @SpringBean("boardDao")
    private BoardDao boardDao;

    @Test
    @ExpectedDataSet("XiaoChun.ExpectedBoards.xls")
    public void addBoard() throws Exception{
        //通过XlsDataSetBeanFactory数据集绑定工厂创建测试实体
        List<Board> boards = XlsDataSetBeanFactory.createBeans(BoardDaoTest.class, "XiaoChun.SaveBoards.xls", "t_board", Board.class);
        for (Board board : boards) {
            boardDao.update(board);
        }
    }

    @Test
    @DataSet(value = "XiaoChun.Boards.xls")//准备数据
    @ExpectedDataSet(value = "XiaoChun.ExpectedBoards.xls")
    public void removeBoard(){
        Board board = boardDao.get(7);
        boardDao.remove(board);
    }

    @Test
    @DataSet("XiaoChun.Boards.xls")
    public void getBoard(){
        Board board = boardDao.get(1);
        Assert.assertNotNull(board);
        Assert.assertEquals(board.getBoardName(),"SpringMVC");
    }



}
