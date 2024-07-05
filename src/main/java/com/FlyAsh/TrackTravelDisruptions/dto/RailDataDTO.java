package com.FlyAsh.TrackTravelDisruptions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RailDataDTO {
    String generatedAt;
    String departureStationCrs;
    String departureStationName;
    String destinationStationCrs;
    String destinationStationName;
    String etd;
    String std;
    String platform;
    String eta;
    String sta;
    boolean isCancelled;
    String cancelReason;
    String delayReason;
    String serviceID;
    String affectedBy;
    boolean filterLocationCancelled;

    public static class Departure {
        public Service service;
        public String crs;
    }

    public static class Service {
        public List<SubsequentCallingPoints> subsequentCallingPoints;
        public String std;
        public String etd;
        public String platform;
        boolean isCancelled;
        boolean filterLocationCancelled;
        public String cancelReason;
        public SubsequentCallingPoints.CallingPoint dest;
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

        public static class SubsequentCallingPoints {
            public List<CallingPoint> callingPoint;

            @Getter
            private static class CallingPoint {
                public String crs;
                public String locationName;
                public String st;
                public String et;
                public Boolean isCancelled;
            }
        }
    }

    public static class NrccMessage {
        public String value;
    }

    public RailDataDTO(List<Departure> departures, String generatedAt, String locationName, String crs, List<NrccMessage> nrccMessages) {
        Service service = departures.getFirst().service;
        this.generatedAt = generatedAt;
        this.departureStationName = locationName;
        this.departureStationCrs = crs;
        this.destinationStationCrs = departures.getFirst().crs;
        Service.SubsequentCallingPoints.CallingPoint dest = service.subsequentCallingPoints.getFirst().callingPoint.stream().filter(callingPoint -> callingPoint.crs.equals(destinationStationCrs)).findAny().get();
        this.destinationStationName = dest.locationName;
        this.etd = service.etd;
        this.std = service.std;
        this.platform = service.platform;
        this.eta = dest.et;
        this.sta = dest.st;
        this.isCancelled = service.isCancelled;
        this.cancelReason = service.cancelReason;
        this.delayReason = service.delayReason;
        this.serviceID = service.serviceID;
        this.affectedBy = service.affectedBy;
        this.filterLocationCancelled = dest.isCancelled;
    }


    @Override
    public String toString() {
        return "RailDataDTO{" +
                "generatedAt='" + generatedAt + '\'' +
                ", departureStationCrs='" + departureStationCrs + '\'' +
                ", departureStationName='" + departureStationName + '\'' +
                ", destinationStationCrs='" + destinationStationCrs + '\'' +
                ", destinationStationName='" + destinationStationName + '\'' +
                ", etd='" + etd + '\'' +
                ", std='" + std + '\'' +
                ", platform='" + platform + '\'' +
                ", eta='" + eta + '\'' +
                ", sta='" + sta + '\'' +
                ", isCancelled=" + isCancelled +
                ", cancelReason='" + cancelReason + '\'' +
                ", delayReason='" + delayReason + '\'' +
                ", serviceID='" + serviceID + '\'' +
                ", affectedBy='" + affectedBy + '\'' +
                ", filterLocationCancelled=" + filterLocationCancelled +
                '}';
    }
}
