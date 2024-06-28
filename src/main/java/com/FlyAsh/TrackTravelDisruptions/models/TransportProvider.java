package com.FlyAsh.TrackTravelDisruptions.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "TRASNPORT_PROVIDERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String website;

    @OneToMany(mappedBy = "transportProvider", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JourneyLeg> journeyLegs;
}
