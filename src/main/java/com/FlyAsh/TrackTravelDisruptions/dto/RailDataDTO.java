package com.FlyAsh.TrackTravelDisruptions.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RailDataDTO {
    public String destinationStationCrs;
    public String generatedAt;
    public String departureStation;
    public String departureCrs;
    public List<String> nrccMessages;
    public Service service;

    public static class Departure {
        public Service service;
        public String crs;
    }

    public static class Service {
        public String std;
        public String etd;
        public String sta;
        public String eta;
        public String platform;
        boolean isCancelled;
        boolean filterLocationCancelled;
        public String cancelReason;
        public String delayReason;
        public String adhocAlerts;
        public String serviceID;
        public String affectedBy;

        @Override
        public String toString() {
            return "Service{" +
                    "std='" + std + '\'' +
                    ", etd='" + etd + '\'' +
                    ", platform=" + platform +
                    ", isCancelled=" + isCancelled +
                    ", filterLocationCancelled=" + filterLocationCancelled +
                    ", cancelReason='" + cancelReason + '\'' +
                    ", delayReason='" + delayReason + '\'' +
                    ", adhocAlerts='" + adhocAlerts + '\'' +
                    ", serviceID='" + serviceID + '\'' +
                    ", affectedBy='" + affectedBy + '\'' +
                    '}';
        }
    }

    public static class NrccMessage {
        public String value;
    }

    public RailDataDTO(List<Departure> departures, String generatedAt, String locationName, String crs, List<NrccMessage> nrccMessages) {
        this.destinationStationCrs = departures.getFirst().crs;
        this.generatedAt = generatedAt;
        this.departureStation = locationName;
        this.departureCrs = crs;
        if (nrccMessages != null) {
            this.nrccMessages = nrccMessages.stream().map(nrccMessage -> nrccMessage.value).collect(Collectors.toList());
            this.service = departures.getFirst().service;
        } else {
            this.nrccMessages = new ArrayList<>();
            this.service = departures.getFirst().service;
        }
    }


    @Override
    public String toString() {
        return "RailDataDTO{" +
                ", generatedAt='" + generatedAt + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", departureCrs='" + departureCrs + '\'' +
                ", nrccMessages=" + nrccMessages +
                ", service=" + service +
                '}';
    }
}
