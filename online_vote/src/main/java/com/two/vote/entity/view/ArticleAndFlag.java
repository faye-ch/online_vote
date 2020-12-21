package com.two.vote.entity.view;

import com.two.vote.entity.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ArticleAndFlag extends Article{
    private Integer flag;
    private String deadline;
}
