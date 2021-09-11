package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
// TODO: Map columns in data table RULENAME with corresponding java fields
}
