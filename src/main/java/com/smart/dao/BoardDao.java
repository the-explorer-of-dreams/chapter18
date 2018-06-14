package com.smart.dao;

import com.smart.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

/**
 * Board DAO类
 */

@Repository
public class BoardDao extends BaseDao<Board> {
    public static final String GET_BOARD_NUM = "select count(t.boardId) from Board t";

    /**
     * 获取论坛模板数量
     * @return 论坛模板数量
     */
    public Integer getBoardNum(){
        Iterator iterator = getHibernateTemplate().iterate(GET_BOARD_NUM);
        return  ((Integer) iterator.next());
    }
}
