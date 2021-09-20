package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Class that manage the entity {@link Rating}
 *
 * @author Christine Duarte
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Rating {
    /**
     * An Integer that contain the id of the {@link Rating}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * A String containing moodys rating
     */
    @NotBlank(message = "MoodysRating is mandatory")
    @Column(name = "moodys_rating")
    private String moodysRating;

    /**
     * A String containing sand P Rating
     */
    @NotBlank(message = "SandPRating is mandatory")
    @Column(name = "sand_p_rating")
    private String sandPRating;

    /**
     * A String containing fitch rating
     */
    @NotBlank(message = "FitchRating is mandatory")
    @Column(name = "fitch_rating")
    private String fitchRating;

    /**
     * A Integer with the order number
     */
    @NotNull(message = "must not be null")
    @Column(name = "order_number")
    private Integer orderNumber;

}
