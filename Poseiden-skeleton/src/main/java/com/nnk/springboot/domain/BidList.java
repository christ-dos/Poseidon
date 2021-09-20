package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * Class that manage the entity {@link BidList}
 *
 * @author Christine Duarte
 */
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

    /**
     * A String containing account of BisList
     */
    @NotBlank(message = "Account is mandatory")
    private String account;

    /**
     * A String containing type of BisList
     */
    @NotBlank(message = "Type is mandatory")
    private String type;

    /**
     * A Double containing the bid quantity
     */
    @Column(name = "bid_quantity")
    private Double bidQuantity;

    /**
     * A Double containing the ask quantity
     */
    @Column(name = "ask_quantity")
    private Double askQuantity;

    /**
     * A Double containing the bid q
     */
    private Double bid;

    /**
     * A Double containing the ask quantity
     */
    private Double ask;

    /**
     * A String containing the benchmark
     */
    private String benchmark;

    /**
     * A Timestamp with the date of the bid list
     */
    @Column(name = "bid_list_date")
    private Timestamp bidListDate;

    /**
     * A String containing the commentary
     */
    private String commentary;

    /**
     * A String containing the security
     */
    private String security;

    /**
     * A String containing the status
     */
    private String status;

    /**
     * A String containing the trader
     */
    private String trader;

    /**
     * A String containing the book
     */
    private String book;

    /**
     * A String containing the creation name
     */
    @Column(name = "creation_name")
    private String creationName;

    /**
     * A Timestamp with the creation date
     */
    @Column(name = "creation_date")
    private Timestamp creationDate;

    /**
     * A String containing the revision name
     */
    @Column(name = "revision_name")
    private String revisionName;

    /**
     * A Timestamp with the revision date
     */
    @Column(name = "revision_date")
    private Timestamp revisionDate;

    /**
     * A String containing the deal name
     */
    @Column(name = "deal_name")
    private String dealName;

    /**
     * A String containing the deal type
     */
    @Column(name = "deal_type")
    private String dealType;

    /**
     * A String containing the source list id
     */
    @Column(name = "source_list_id")
    private String sourceListId;

    /**
     * A String containing the side
     */
    private String side;

    /**
     * Method toString
     *
     * @return a String of the object BidList
     */
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
