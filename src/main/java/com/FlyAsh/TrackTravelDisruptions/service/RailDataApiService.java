package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.dto.RailDataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RailDataApiService {


    private final WebClient webClient = WebClient.create();

    public RailDataDTO callThirdPartyAPI(String url) {
        ResponseEntity<RailDataDTO> response = webClient.get()
                .uri(url)
                .header("X-apikey", "VPSXD1WTxiGdA7kSIOmG0MIz3brZ1TlUSTKqwQkEKRb1Gw3c")
                .retrieve()
                .toEntity(RailDataDTO.class)
                .block();
        return response.getBody();
    }
}