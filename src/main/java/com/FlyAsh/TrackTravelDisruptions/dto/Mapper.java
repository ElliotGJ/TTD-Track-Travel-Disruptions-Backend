package com.FlyAsh.TrackTravelDisruptions.dto;

import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import org.springframework.stereotype.Service;


public class Mapper {

    public static JourneyDTO mapToJourneyDTO(Journey journey) {
        return new JourneyDTO(
                journey.getId(),
                journey.getUserId(),
                journey.getOriginCRS(),
                journey.getDestinationCRS(),
                journey.getDepartureTime(),
                journey.getDays(),
                journey.getNotificationsEnabled()
        );
    }

    public static JourneyDTOWithRailDataDTO mapToJourneyDTOWithRailDataDTO(Journey journey, RailDataDTO railDataDTO) {
        return new JourneyDTOWithRailDataDTO(
                mapToJourneyDTO(journey), railDataDTO
        );
    }

}
