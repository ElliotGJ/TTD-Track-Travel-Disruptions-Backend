package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.dto.JourneyDTO;
import com.FlyAsh.TrackTravelDisruptions.dto.JourneyDTOWithRailDataDTO;
import com.FlyAsh.TrackTravelDisruptions.dto.Mapper;
import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import com.FlyAsh.TrackTravelDisruptions.repositories.JourneyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        journeyRepository.findAllByUserId(id).forEach(journeyList::add);
        if (journeyList.isEmpty()) {
            throw new EntityNotFoundException("No journeys found for user with ID: " + id);
        }
        return journeyList;
    }

    @Override
    public Journey addNewJourney(Journey journey) {
        journey.getJourneyLegs().forEach(journeyLeg -> journeyLeg.setJourney(journey));
        journey.getJourneyLegs().forEach(journeyLeg -> railDataApiService.getNextFastestServiceBetween(journeyLeg.getOriginCRS(), journeyLeg.getDestinationCRS(), 0));

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

        railDataApiService.getNextFastestServiceBetween(journeyToUpdate.getOriginCRS(), journeyToUpdate.getDestinationCRS(), 0);
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
        return journeys.stream().map(journey -> {
                    long timeOffset = ChronoUnit.MINUTES.between(LocalTime.now(), journey.getDepartureTime());

                    if (timeOffset > 10 && timeOffset < 120 && journey.getDays().contains(DayOfWeek.from(LocalDateTime.now().plusMinutes(timeOffset)))) {
                        return Mapper.mapToJourneyDTOWithRailDataDTO(journey, railDataApiService.getNextFastestServiceBetween(journey.getOriginCRS(), journey.getDestinationCRS(), timeOffset));
                    } else if (timeOffset >= -30 ) {
                        return Mapper.mapToJourneyDTOWithRailDataDTO(journey, railDataApiService.getNextFastestServiceBetween(journey.getOriginCRS(), journey.getDestinationCRS(), 0));
                    }
                    return Mapper.mapToJourneyDTOWithRailDataDTO(journey, null);

                }
        ).toList();
    }

    @Override
    public JourneyDTOWithRailDataDTO getJourneyWithRailDataByUserIdAndJourneyId(Long userId, Long journeyId) {
        Journey journey = journeyRepository.findByIdAndUserId(journeyId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Journey not found with ID: " + journeyId + " for user with ID: " + userId));

        return Mapper.mapToJourneyDTOWithRailDataDTO(journey, railDataApiService.getNextFastestServiceBetween(journey.getOriginCRS(), journey.getDestinationCRS(), 0));
    }
}
