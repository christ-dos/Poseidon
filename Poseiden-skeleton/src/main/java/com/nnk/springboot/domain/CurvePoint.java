package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@NoArgsConstructor
@Getter
@Setter
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Curveid")
    private  Integer curveId;

    private Timestamp asOfDate;

    private  Double term;

    private  Double value;

    private  Timestamp creationDate;


// TODO: Map columns in data table CURVEPOINT with corresponding java fields
}
