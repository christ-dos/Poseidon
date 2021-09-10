package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
@Getter
@Setter
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;


// TODO: Map columns in data table RULENAME with corresponding java fields
}
