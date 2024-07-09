package com.FlyAsh.TrackTravelDisruptions.controller;

import com.FlyAsh.TrackTravelDisruptions.dto.JourneyDTOWithRailDataDTO;
import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import com.FlyAsh.TrackTravelDisruptions.service.JourneyServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/journey")
public class JourneyController {

    @Autowired
    JourneyServiceImpl journeyServiceImpl;
    @Autowired
    ObjectMapper mapper;

    @GetMapping
    public ResponseEntity<List<Journey>> getAllJourneys() {
        return new ResponseEntity<>(journeyServiceImpl.getAllJourneys(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<JourneyDTOWithRailDataDTO>> getJourneysWithRailDataByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(journeyServiceImpl.getJourneysWithRailDataByUserId(id), HttpStatus.OK);
    }


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

    @GetMapping("/{userId}/journey/{journeyId}")
    public ResponseEntity<JourneyDTOWithRailDataDTO> getJourneyWithRailDataByUserIdAndJourneyId(
            @PathVariable Long userId, @PathVariable Long journeyId) {
        JourneyDTOWithRailDataDTO result = journeyServiceImpl.getJourneyWithRailDataByUserIdAndJourneyId(userId, journeyId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}