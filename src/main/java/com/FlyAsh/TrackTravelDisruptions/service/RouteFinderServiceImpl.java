package com.FlyAsh.TrackTravelDisruptions.service;

import org.springframework.stereotype.Service;

@Service
public class RouteFinderServiceImpl {


    public String findRoute(String source, String destination) {
        return "Route found from " + source + " to " + destination;
    }
}
