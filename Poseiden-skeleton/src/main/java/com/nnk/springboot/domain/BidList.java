package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bid_list")
public class BidList {
    /**
     * An Integer that contain the id of the {@link BidList}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    private  String account;

    @NotBlank(message = "Type is mandatory")
    private  String type;

    @Column(name = "bid_quantity")
    private  Double bidQuantity;

    @Column(name = "ask_quantity")
    private  Double askQuantity;

    private  Double bid;

    private  Double ask;

    private String benchmark;

    @Column(name = "bid_list_date")
    private Timestamp bidListDate;

    private String commentary;

    private String security;

    private String status;

    private String trader;

    private String book;

    @Column(name = "creation_name")
    private String creationName;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "revision_name")
    private String revisionName;

    @Column(name = "revision_date")
    private Timestamp revisionDate;

    @Column(name = "deal_name")
    private String dealName;

    @Column(name = "deal_type")
    private String dealType;

    @Column(name = "source_list_id")
    private String sourceListId;

    private String side;

    @Override
    public String toString() {
        return "BidList{" +
                "bidListId=" + bidListId +
                ", account='" + account + '\'' +
                ", type='" + type + '\'' +
                ", bidQuantity=" + bidQuantity +
                ", askQuantity=" + askQuantity +
                ", bid=" + bid +
                ", ask=" + ask +
                ", benchmark='" + benchmark + '\'' +
                ", bidListDate=" + bidListDate +
                ", commentary='" + commentary + '\'' +
                ", security='" + security + '\'' +
                ", status='" + status + '\'' +
                ", trader='" + trader + '\'' +
                ", book='" + book + '\'' +
                ", creationName='" + creationName + '\'' +
                ", creationDate=" + creationDate +
                ", revisionName='" + revisionName + '\'' +
                ", revisionDate=" + revisionDate +
                ", dealName='" + dealName + '\'' +
                ", dealType='" + dealType + '\'' +
                ", sourceListId='" + sourceListId + '\'' +
                ", side='" + side + '\'' +
                '}';
    }
}
