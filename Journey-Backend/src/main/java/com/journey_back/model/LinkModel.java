package com.journey_back.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;


@Entity
@Table(name = "links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O titulo nao pode ser nulo")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "A url nao pode ser nulo")
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "trip_id")
    private Integer tripId;

   public LinkModel(String title, String url, Integer trip){
       this.title = title;
       this.url = url;
       this.tripId = trip;
   }
}
