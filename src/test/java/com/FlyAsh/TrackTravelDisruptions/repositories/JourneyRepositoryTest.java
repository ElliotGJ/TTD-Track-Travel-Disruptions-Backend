package com.FlyAsh.TrackTravelDisruptions.repositories;


import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import com.FlyAsh.TrackTravelDisruptions.models.JourneyLeg;
import com.FlyAsh.TrackTravelDisruptions.models.TransportProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class JourneyRepositoryTest {

    @Autowired
    JourneyRepository journeyRepository;

    @Test
    void testJourneyRepository() {
        TransportProvider nationalRail = new TransportProvider(1L, "National Rail", "https://www.nationalrail.co.uk/", null);
        TransportProvider transportForLondon = new TransportProvider(2L, "Transport for London", "https://tfl.gov.uk/", null);

        Journey journey = new Journey();
        journey.setId(1L);
        journey.setOrigin("Oxford");
        journey.setDestination("Piccadilly Circus");
        journey.setDepartureTime("8:00");
        JourneyLeg leg1 = new JourneyLeg(1L, "Oxford", "Reading", nationalRail, null);
        JourneyLeg leg2 = new JourneyLeg(2L, "Reading", "Paddington", nationalRail, null);
        nationalRail.setJourneyLegs(Set.of(leg1, leg2));
        JourneyLeg leg3 = new JourneyLeg(3L, "Paddington", "Piccadilly Circus", transportForLondon, null);
        transportForLondon.setJourneyLegs(Set.of(leg3));
        journey.setJourneyLegs(Set.of(leg1, leg2, leg3));
        journey.setNotificationsEnabled(true);
        journey.setDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));

        journey.getJourneyLegs().forEach(journeyLeg -> journeyLeg.setJourney(journey));

        journeyRepository.save(journey);


        Journey result = journeyRepository.findById(1L).get();

        assertThat(result.getDestination()).isEqualTo(journey.getDestination());
        assertThat(result.getJourneyLegs().size()).isEqualTo(3);
        assertThat(result.getJourneyLegs().iterator().next().getTransportProvider().getName()).isEqualTo(nationalRail.getName());
    }

}