package com.FlyAsh.TrackTravelDisruptions.controller;

import com.FlyAsh.TrackTravelDisruptions.dto.ExternalApiResponseDTO;
import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import com.FlyAsh.TrackTravelDisruptions.service.ExternalApiService;
import com.FlyAsh.TrackTravelDisruptions.service.JourneyServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/journey")
public class JourneyController {

    @Autowired
    JourneyServiceImpl journeyServiceImpl;

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping
    public ResponseEntity<List<Journey>> getAllJourneys() {
        return new ResponseEntity<>(journeyServiceImpl.getAllJourneys(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Journey> getJourneyById(@PathVariable Long id) {
        Journey result = journeyServiceImpl.getJourneyById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @GetMapping("/report/{departure}/{destination}")
//    public ExternalApiResponseDTO getDepartures(@PathVariable String departure, @PathVariable String destination) {
//        try {
//            return externalApiService.getNextDeparturesWithDetails(departure, destination);
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching departures", e);
//        }
//    }

    @PostMapping
    public ResponseEntity<Journey> addNewJourney(@RequestBody Journey journey) {
        Journey result = journeyServiceImpl.addNewJourney(journey);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journey> updateJourneyById(@PathVariable Long id, @RequestBody Journey journey) {
        Journey result = journeyServiceImpl.updateJourneyById(id, journey);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJourneyById(@PathVariable Long id) {
        journeyServiceImpl.deleteJourneyById(id);
        return new ResponseEntity<>("Journey Deleted", HttpStatus.ACCEPTED);
    }
}