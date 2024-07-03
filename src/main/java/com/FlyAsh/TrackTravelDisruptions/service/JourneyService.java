package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.dto.JourneyDTO;
import com.FlyAsh.TrackTravelDisruptions.dto.JourneyDTOWithRailDataDTO;
import com.FlyAsh.TrackTravelDisruptions.models.Journey;

import java.util.List;

public interface JourneyService {
    List<Journey> getAllJourneys();

    Journey getJourneyById(Long id);

    Journey addNewJourney(Journey journey);

    Journey updateJourneyById(Long id, Journey journey);

    void deleteJourneyById(Long id);

    JourneyDTO getJourneyDTOById(Long id);

    JourneyDTOWithRailDataDTO getJourneyWithRailDataDTOById(Long id);
}
