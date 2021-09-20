package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * Class that manage the entity {@link RuleName}
 *
 * @author Christine Duarte
 */
@Entity
@Table(name = "rule_name")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleName {
    /**
     * An Integer that contain the id of the {@link RuleName}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * A String containing name of RuleName
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * A String containing description of RuleName
     */
    private String description;

    /**
     * A String containing json of RuleName
     */
    private String json;

    /**
     * A String containing template of RuleName
     */
    private String template;

    /**
     * A String containing sqlStr of RuleName
     */
    @Column(name = "sql_str")
    private String sqlStr;

    /**
     * A String containing sqlPart of RuleName
     */
    @Column(name = "sql_part")
    private String sqlPart;
}
