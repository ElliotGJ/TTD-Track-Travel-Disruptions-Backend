package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import com.FlyAsh.TrackTravelDisruptions.repositories.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JourneyServiceImpl implements JourneyService {

    @Autowired
    JourneyRepository journeyRepository;

    @Override
    public List<Journey> getAllJourneys() {
        List<Journey> journeyList = new ArrayList<>();
        journeyRepository.findAll().forEach(journeyList::add);
        return journeyList;
    }


    @Override
    public Journey getJourneyById(Long id) {
        Optional<Journey> journeyOptional = this.journeyRepository.findById(id);
        if (journeyOptional.isEmpty()) {
            throw new EntityNotFoundException("Journey not found with ID: " + id);
        }
        return journeyOptional.get();
    }

    @Override
    public Journey addNewJourney(Journey journey) {
        //Notify will be boolean and default false
        if (journey.getOrigin() == null || journey.getDestination() == null || journey.getDays() == null || journey.getDepartureTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incomplete info");
        }
        return journeyRepository.save(journey);
    }

    @Override
    public Journey updateJourneyById(Long id, Journey journey) {
        Journey journeyToUpdate = getJourneyById(id);
        if (journey.getOrigin() != null) {
            journeyToUpdate.setOrigin(journey.getOrigin());
        }
        if (journey.getDestination() != null) {
            journeyToUpdate.setDestination(journey.getDestination());
        }
        if (journey.getDays() != null) {
            journeyToUpdate.setDays(journey.getDays());
        }
        if (journey.getNotificationsEnabled() != null) {
            journeyToUpdate.setNotificationsEnabled(journey.getNotificationsEnabled());
        }
        if (journey.getDepartureTime() != null) {
            journeyToUpdate.setDepartureTime(journey.getDepartureTime());
        }

        return this.journeyRepository.save(journeyToUpdate);
    }

    @Override
    public void deleteJourneyById(Long id) {
        Journey journey = getJourneyById(id);
        journeyRepository.delete(journey);
    }
}
