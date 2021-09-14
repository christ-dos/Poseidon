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

    @NotNull(message = "must not be null")
    @Column(name = "curve_id")
    private  Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    private  Double term;

    private  Double value;

    @Column(name = "creation_date")
    private  Timestamp creationDate;

}
