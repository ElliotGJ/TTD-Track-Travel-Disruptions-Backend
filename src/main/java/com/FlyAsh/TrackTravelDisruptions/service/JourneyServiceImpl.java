package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.dto.JourneyDTO;
import com.FlyAsh.TrackTravelDisruptions.dto.JourneyDTOWithRailDataDTO;
import com.FlyAsh.TrackTravelDisruptions.dto.Mapper;
import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import com.FlyAsh.TrackTravelDisruptions.repositories.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JourneyServiceImpl implements JourneyService {

    @Autowired
    JourneyRepository journeyRepository;

    @Autowired
    RailDataApiService railDataApiService;

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
    public List<Journey> getJourneysByUserId(Long id) {
        List<Journey> journeyList = new ArrayList<>();
        journeyRepository.findAll().forEach(journeyList::add);
        if (journeyList.isEmpty()) {
            throw new EntityNotFoundException("No journeys found for user with ID: " + id);
        }
        return journeyList;
    }

    @Override
    public Journey addNewJourney(Journey journey) {
        journey.getJourneyLegs().forEach(journeyLeg -> journeyLeg.setJourney(journey));
        return journeyRepository.save(journey);
    }

    @Override
    public Journey updateJourneyById(Long id, Journey journey) {
        Journey journeyToUpdate = getJourneyById(id);
        if (journey.getOriginCRS() != null) {
            journeyToUpdate.setOriginCRS(journey.getOriginCRS());
        }
        if (journey.getDestinationCRS() != null) {
            journeyToUpdate.setDestinationCRS(journey.getDestinationCRS());
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

    @Override
    public JourneyDTO getJourneyDTOById(Long id) {
        return Mapper.mapToJourneyDTO(getJourneyById(id));
    }


    @Override
    public List<JourneyDTOWithRailDataDTO> getJourneysWithRailDataByUserId(Long id) {
        List<Journey> journeys = getJourneysByUserId(id);
        return journeys.stream().map(journey ->
                Mapper.mapToJourneyDTOWithRailDataDTO(journey, railDataApiService.getNextFastestServiceBetween(journey.getOriginCRS(), journey.getDestinationCRS()))
        ).toList();
    }


}
