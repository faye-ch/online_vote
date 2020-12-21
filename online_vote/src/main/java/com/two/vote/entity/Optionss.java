package com.two.vote.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name="optionss")
public class Optionss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String optionvalue;
    private Long articleid;
}
