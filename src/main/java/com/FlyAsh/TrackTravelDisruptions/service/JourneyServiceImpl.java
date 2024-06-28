package com.FlyAsh.TrackTravelDisruptions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JourneyServiceImpl {

    @Autowired
    JourneyRepository journeyRepository;

    @Override
    public List<Journey> getAllJourney(){
        List<Journey> journeyList = new ArrayList<>();
        journeyRepository.findAll().forEach(journeyList::add);
        return journeyList;
    }

    @Override
    public Journey getJourneyById(Long id) {
        Optional<Journey> journeyOptional = this.journeyRepository.findById(id);
        if(journeyOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Journey not found");
        }
        return journeyOptional.get();
    }

    @Override
    public Journey addNewJourney(Journey journey) {
        //Notify will be boolean and default false
        //LeaveTime is optional and can be null
        if(journey.getStartPoint() == null || journey.getEndPoint() == null || journey.getFrequency() == null || journey.getLeaveTime() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incomplete info");
        }
        return journeyRepository.save(journey);
    }

    @Override
    public Journey updateJourneyById(Long id, Journey journey) {
        Optional<Journey> journeyOptional = this.journeyRepository.findById(id);
        if (journeyOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ID_UPDATE");
        }
        Journey journeyToUpdate = journeyOptional.get();
        if(journey.getStartPoint() != null){
            journeyToUpdate.setStartPoint(journey.getStartPoint());
        }
        if(journey.getEndPoint() != null){
            journeyToUpdate.setEndPoint(journey.getEndPoint());
        }
        if(journey.getFrequency() != null){
            journeyToUpdate.setFrequency(journey.getFrequency());
        }
        if(journey.getNotify() != null){
            journeyToUpdate.setNotify(journey.getNotify());
        }
        if(journey.getDepartureTime() != null){
            journeyToUpdate.setDepartureTime(journey.getDepartureTime());
        }

        return this.journeyRepository.save(journeyToUpdate);
    }

    @Override
    public Journey deleteJourneyById(Long id){
        Optional<Journey> journeyToDeleteOptional = this.journeyRepository.findById(id);
        if (journeyToDeleteOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete, Journey not found");
        } else {
            Journey deleteJourney = journeyToDeleteOptional.get();
            this.journeyRepository.delete(deleteJourney);
            return deleteJourney;
        }
    }
}
