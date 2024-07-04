package com.FlyAsh.TrackTravelDisruptions.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "JOURNEY_LEG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JourneyLeg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String origin;

    @Column
    private String originCRS;

    @Column(nullable = false)
    private String destination;

    @Column
    private String destinationCRS;

    @Column(nullable = false)
    private int legOrder;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TransportProvider transportProvider;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    private Journey journey;

}
