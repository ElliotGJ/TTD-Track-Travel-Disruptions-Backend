package com.FlyAsh.TrackTravelDisruptions.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private String originCRS;

    @Column(nullable = false)
    private String destinationCRS;

    @Column(nullable = false)
    private Long userId;

    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable
    @Column
    private Set<DayOfWeek> days;

    @Column(nullable = false)
    private LocalTime departureTime;

    @OneToMany(mappedBy = "journey", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<JourneyLeg> journeyLegs;

}
