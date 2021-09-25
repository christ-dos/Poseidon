package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Class that manage the entity {@link CurvePoint}
 *
 * @author Christine Duarte
 */
@Entity
@Table(name = "curve_point")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CurvePoint {

    /**
     * An Integer that contain the id of the {@link CurvePoint}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * A String containing curve id of CurvePoint
     */
    @NotNull(message = "must not be null")
    @Column(name = "curve_id")
    private  Integer curveId;

    /**
     * A Timestamp with the as fo date
     */
    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    /**
     * A Double containing the term
     */
    private  Double term;

    /**
     * A Double containing the value
     */
    private  Double value;

    /**
     * A Timestamp with the creation date
     */
    @Column(name = "creation_date")
    private  Timestamp creationDate;
}
