package com.smart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.*;
import java.util.Date;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name="t_topic")
public class Topic extends BaseDomain{
public static final int DIGEST_TOPIC = 1;
public static final int NOT_DIGEST_TOPIC=0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="topic_id")
    private Integer topicId;

    @Column(name="topic_title")
    private String topicTitle;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="board_id")
    private Integer boardId;

    @Transient
    private MainPost mainPost = new MainPost();

    @Column(name="last_post")
    private Date lastPost = new Date();

    @Column(name="create_time")
    private Date createTime = new Date();

    @Column(name="topic_views")
    private Integer views;

    @Column(name="topic_replies")
    private Integer replies;

    private Integer digest = NOT_DIGEST_TOPIC;

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public MainPost getMainPost() {
        return mainPost;
    }

    public void setMainPost(MainPost mainPost) {
        this.mainPost = mainPost;
    }

    public Date getLastPost() {
        return lastPost;
    }

    public void setLastPost(Date lastPost) {
        this.lastPost = lastPost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getReplies() {
        return replies;
    }

    public void setReplies(Integer replies) {
        this.replies = replies;
    }

    public Integer getDigest() {
        return digest;
    }

    public void setDigest(Integer digest) {
        this.digest = digest;
    }
}
