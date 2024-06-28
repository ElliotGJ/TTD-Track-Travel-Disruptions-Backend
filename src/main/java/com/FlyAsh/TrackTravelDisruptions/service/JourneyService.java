package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.models.Journey;

import java.util.List;

public interface JourneyService {
    List<Journey> getAllJourneys();

    Journey getJourneyById(Long id);

    Journey addNewJourney(Journey journey);

    Journey updateJourneyById(Long id, Journey journey);

    void deleteJourneyById(Long id);
}
