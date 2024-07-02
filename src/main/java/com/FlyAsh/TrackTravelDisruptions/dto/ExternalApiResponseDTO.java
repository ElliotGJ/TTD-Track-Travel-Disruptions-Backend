package com.FlyAsh.TrackTravelDisruptions.dto;

import java.util.List;

public class ExternalApiResponseDTO {
    public List<Departure> departures;
    public Xmlns xmlns;
    public String generatedAt;
    public String locationName;
    public String crs;
    public String filterType;
    public List<NrccMessage> nrccMessages;
    public boolean platformAvailable;
    public boolean areServicesAvailable;

    public static class Departure {
        public String crs;
    }

    public static class Xmlns {
        public int count;
    }

    public static class NrccMessage {
        public String value;
    }
}
