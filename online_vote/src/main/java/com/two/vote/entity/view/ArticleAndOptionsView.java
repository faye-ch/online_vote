package com.two.vote.entity.view;

import com.two.vote.entity.Article;
import com.two.vote.entity.Optionss;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class ArticleAndOptionsView extends Article {
    private List<Optionss> optionList;
    private List<OptionAndNumView> optionAndNumViews;
}