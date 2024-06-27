package com.FlyAsh.TrackTravelDisruptions.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table(name = "JOURNEY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Boolean notificationsEnabled;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable
    @Column
    private Set<DayOfWeek> days;

    @Column(nullable = false)
    private String departureTime;

    @OneToMany(mappedBy = "journey", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JourneyLeg> journeyLegs;

}
