package com.two.vote.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name="article")
public class Article {
    @Id
    private Long id;
    private String title;
    private Integer type;
    private Date createtime;
    private Date begintime;
    private Date endtime;
    private Long userid;
    private String img;
    private Long hot;
}
