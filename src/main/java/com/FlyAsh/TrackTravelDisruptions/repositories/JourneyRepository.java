package com.FlyAsh.TrackTravelDisruptions.repositories;

import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JourneyRepository extends CrudRepository<Journey, Long> {
    List<Journey> findAllByUserId(Long userId);
    Optional<Journey> findByIdAndUserId(Long journeyId, Long userId);

}
