package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String moodysRating;

    private String sandPRating;

    private String fitchRating;

    private Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

// TODO: Map columns in data table RATING with corresponding java fields
}
