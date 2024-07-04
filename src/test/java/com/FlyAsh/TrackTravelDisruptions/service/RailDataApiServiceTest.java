package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.dto.RailDataDTO;
import org.junit.jupiter.api.Test;

class RailDataApiServiceTest {




    @Test
    void getNextFastestServiceBetween() {
        RailDataApiService railDataApiService = new RailDataApiService();
        RailDataDTO railDataDTO = railDataApiService.getNextFastestServiceBetween("BHM", "EUS");
        System.out.println(railDataDTO.toString());
        System.out.println(railDataDTO.destinationStationCrs);
    }
}