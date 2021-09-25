package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Class that manage the entity {@link Trade}
 *
 * @author Christine Duarte
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade {
    /**
     * An Integer that contain the id of the {@link Trade}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id", nullable = false)
    private Integer tradeId;

    /**
     * A String containing the account of Trade
     */
    @NotBlank(message = "Account is mandatory")
    private String account;

    /**
     * A String containing the type of Trade
     */
    @NotBlank(message = "Type is mandatory")
    private String type;

    /**
     * A Double containing buy quantity
     */
    @NotNull(message = "must not be null")
    private Double buyQuantity;

    /**
     * A Double containing sell quantity
     */
    @Column(name = "sell_quantity")
    private Double sellQuantity;

    /**
     * A Double containing buy price
     */
    @Column(name = "buy_price")
    private Double buyPrice;

    /**
     * A Double containing sell price
     */
    @Column(name = "sell_price")
    private Double sellPrice;

    /**
     * A String containing benchmark
     */
    private String benchmark;

    /**
     * A Timestamp containing trade date
     */
    @Column(name = "trade_date")
    private Timestamp tradeDate;

    /**
     * A String containing security
     */
    private String security;

    /**
     * A String containing status
     */
    private String status;

    /**
     * A String containing trader
     */
    private String trader;

    /**
     * A String containing book
     */
    private String book;

    /**
     * A String containing creation name
     */
    @Column(name = "creation_name")
    private String creationName;

    /**
     * A Timestamp containing creation date
     */
    @Column(name = "creation_date")
    private Timestamp creationDate;

    /**
     * A String containing revision name
     */
    @Column(name = "revision_name")
    private String revisionName;

    /**
     * A Timestamp containing revision date
     */
    @Column(name = "revision_date")
    private Timestamp revisionDate;

    /**
     * A String containing deal name
     */
    @Column(name = "deal_name")
    private String dealName;

    /**
     * A String containing deal type
     */
    @Column(name = "deal_type")
    private String dealType;

    /**
     * A String containing source list id
     */
    @Column(name = "source_list_id")
    private String sourceListId;

    /**
     * A String containing side
     */
    private String side;
}
