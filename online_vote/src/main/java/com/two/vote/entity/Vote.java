package com.two.vote.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name="vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long articleid;
    private Long optionid;
    private Long voterid;
    private Date votetime;
    private String source;
    private String ip;
}
