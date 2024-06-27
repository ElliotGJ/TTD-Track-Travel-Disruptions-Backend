package com.FlyAsh.TrackTravelDisruptions.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/journey/")
public class JourneyController {
    @Autowired
    JourneyService journeyService;

    @GetMapping("/")
    public ResponseEntity<List<Journey>> getAllJourney(){
        return new ResponseEntity<>(journeyService.getAllJourney(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getJourneyById(@PathVariable Long id){
        try{
            Journey journeyById = journeyService.getJourneyById(id);
            return new ResponseEntity<>(journeyById, HttpStatus.OK);
        } catch (ResponseStatusException ex){
            return new ResponseEntity<>(ex.getReason(), HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/journeyname/{journeyName}")
//    public ResponseEntity<Journey> getJourneyByName(@PathVariable String journeyName){
//        Journey journeyByName = journeyService.getByJourneyName(journeyName);
//        return new ResponseEntity<>(journeyByName, HttpStatus.OK);
//    }

    @PostMapping("/addnew")
    public ResponseEntity<?> addNewJourney(@RequestBody Journey journey){
        try{
            Journey addNewJourney = journeyService.addNewJourney(journey);
            return new ResponseEntity<>(addNewJourney, HttpStatus.CREATED);
        } catch (ResponseStatusException ex){
            return new ResponseEntity<>(ex.getReason(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJourneyById(@PathVariable Long id, @RequestBody Journey journey){
        try{
            Journey updateJourneyById = journeyService.updateJourneyById(id, journey);
            return new ResponseEntity<>(updateJourneyById, HttpStatus.OK);
        } catch (ResponseStatusException ex){
            return new ResponseEntity<>(ex.getReason(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJourneyById(@PathVariable Long id){
        try{
            Journey deleteJourneyById = journeyService.deleteJourneyById(id);
            return new ResponseEntity<>(deleteJourneyById, HttpStatus.ACCEPTED);
        } catch (ResponseStatusException ex){
            return new ResponseEntity<>(ex.getReason(), HttpStatus.NOT_FOUND);
        }
    }
}