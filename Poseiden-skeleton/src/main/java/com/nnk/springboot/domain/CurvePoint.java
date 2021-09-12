package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curve_point")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "curve_id")
    private  Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    private  Double term;

    private  Double value;

    @Column(name = "creation_date")
    private  Timestamp creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
