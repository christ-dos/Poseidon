package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "MoodysRating is mandatory")
    @Column(name = "moodys_rating")
    private String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    @Column(name = "sand_p_rating")
    private String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    @Column(name = "fitch_rating")
    private String fitchRating;

    @NotNull(message = "must not be null")
    @Column(name = "order_number")
    private Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
