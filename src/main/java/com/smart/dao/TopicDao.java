package com.smart.dao;

/**
 * topic DAO 类
 */

import com.smart.domain.Topic;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDao extends BaseDao<Topic> {
    private final static String GET_BOARD_DIGEST_TOPICS = "from Topic t where t.boardId = ? and t.digest > 0 order by t.lastPost desc,digest desc";
    private static final String GET_PAGED_TOPICS = "from Topic where boardId = ? order by lastPost desc";
    private static final String QUERY_TOPIC_BY_TITILE = "from Topic  where topicTitle like ? order by lastPost desc";
    /**
     * 指定页获取论坛模块的精华帖，按最后回复时间和精华帖级别降序排列
     * @param boardId 论坛版块ID
     * @return 所有精华帖中指定页的数据
     */
    public Page getBoardDigestTopics(int boardId,int pageNo,int pageSize){
        return pagedQuery(GET_BOARD_DIGEST_TOPICS,pageNo,pageSize,boardId);
    }

    /**
     * 指定页获取论坛模块的全部帖子，按最后回复时间降序排列
     * @param boardId 论坛版块ID
     * @return 所有帖子中指定页的数据
     */
    public Page getPagedTopics(int boardId,int pageNo,int pageSize) {
        return pagedQuery(GET_PAGED_TOPICS,pageNo,pageSize,boardId);
    }

    /**
     * 根据主题帖标题查询所有模糊匹配的主题帖
     * @param title 标题的查询条件
     * @param pageNo 页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    public Page queryTopicByTitle(String title, int pageNo, int pageSize) {
        return pagedQuery(QUERY_TOPIC_BY_TITILE,pageNo,pageSize,title);
    }
}
