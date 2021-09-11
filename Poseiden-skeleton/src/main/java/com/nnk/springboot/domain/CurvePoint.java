package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "Curveid")
    private  Integer curveId;

    private Timestamp asOfDate;

    private  Double term;

    private  Double value;

    private  Timestamp creationDate;

    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

// TODO: Map columns in data table CURVEPOINT with corresponding java fields
}
