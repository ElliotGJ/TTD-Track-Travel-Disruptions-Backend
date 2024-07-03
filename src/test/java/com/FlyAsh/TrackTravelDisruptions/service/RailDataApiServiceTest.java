package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.dto.RailDataDTO;
import org.junit.jupiter.api.Test;

class RailDataApiServiceTest {




    @Test
    void callThirdPartyAPI() {
        RailDataApiService railDataApiService = new RailDataApiService();
        RailDataDTO railDataDTO = railDataApiService.callThirdPartyAPI("https://api1.raildata.org.uk/1010-live-fastest-departures/LDBWS/api/20220120/GetFastestDeparturesWithDetails/BHM/RDG");
        System.out.println(railDataDTO.toString());
        System.out.println(railDataDTO.destinationStationCrs);
    }
}