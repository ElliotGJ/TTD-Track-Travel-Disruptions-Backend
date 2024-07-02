package com.FlyAsh.TrackTravelDisruptions.service;

import com.FlyAsh.TrackTravelDisruptions.dto.ExternalApiResponseDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ExternalApiService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String apiKey = "Oo96oKSKErZlbl6RVLn53CX1Fs5TTD3ceL1NMj1GAnMm4srE";

    public ExternalApiResponseDTO getNextDeparturesWithDetails(String departure, String destination) throws IOException {
        String url = String.format("https://api1.raildata.org.uk/1010-live-next-departure-board/LDBWS/api/20220120/GetNextDeparturesWithDetails/%s/%s", departure, destination);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-apikey", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return objectMapper.readValue(response.body().string(), ExternalApiResponseDTO.class);
        }
    }
}