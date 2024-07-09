package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.dto.RailDataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;



@Service
public class RailDataApiService {


    private final String baseUrl = "https://api1.raildata.org.uk/1010-live-fastest-departures/LDBWS/api/20220120/GetFastestDeparturesWithDetails/";
    private final WebClient webClient = WebClient.builder().baseUrl(baseUrl).defaultHeader("X-apikey", "VPSXD1WTxiGdA7kSIOmG0MIz3brZ1TlUSTKqwQkEKRb1Gw3c").build();


    public RailDataDTO getNextFastestServiceBetween(String origin, String destination, long timeOffset) {
        ResponseEntity<RailDataDTO> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment(origin + "/" + destination)
                        .queryParam("timeOffset", timeOffset)
                        .build())
                .retrieve()
                .toEntity(RailDataDTO.class)
                .block();
        assert response != null;
        return response.getBody();
    }

    public boolean checkApiHealth() {
        try {
            ResponseEntity<RailDataDTO> response = webClient.get()
                    .uri(baseUrl + "MAN/RDG")
                    .header("X-apikey", "VPSXD1WTxiGdA7kSIOmG0MIz3brZ1TlUSTKqwQkEKRb1Gw3c")
                    .retrieve()
                    .toEntity(RailDataDTO.class)
                    .block();
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}