package com.journey_back.model;

import com.journey_back.request.TripRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TripModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O destino não poder nulo")
    @Column(nullable = false)
    private String destination;

    @NotNull(message = "A data de inicio não poder nulo")
    @Column(name = "starts_at", nullable = false)
    private LocalDate startsAt;

    @NotNull(message = "A data final não poder nulo")
    @Column(name = "ends_at", nullable = false)
    private LocalDate endsAt;

    @Column(name = "is_confirmed")
    private Boolean isConfirmed;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "user_id")
    private Integer userId;
}
