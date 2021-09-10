package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@NoArgsConstructor
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




// TODO: Map columns in data table RATING with corresponding java fields
}
