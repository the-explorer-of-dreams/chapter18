package com.smart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name="t_board")
public class Board extends BaseDomain {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Integer boardId;

    @Column(name="board_name")
    private String boardName;

    @Column(name="board_desc")
    private String boardDesc;

    @Column(name="topic_num")
    private Integer topicNum;




}
