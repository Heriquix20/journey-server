package com.journey_back.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O titulo nao pode ser nulo")
    @Column(nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "trip_id")
    private Integer tripId;

    public ActivityModel(String title, String occursAt, Integer tripId){
        this.title = title;
        this.date = LocalDateTime.parse(occursAt, DateTimeFormatter.ISO_DATE_TIME);
        this.tripId = tripId;
    }
}
