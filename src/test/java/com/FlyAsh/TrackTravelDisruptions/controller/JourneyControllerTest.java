package com.FlyAsh.TrackTravelDisruptions.controller;

import com.FlyAsh.TrackTravelDisruptions.models.Journey;
import com.FlyAsh.TrackTravelDisruptions.models.JourneyLeg;
import com.FlyAsh.TrackTravelDisruptions.models.TransportProvider;
import com.FlyAsh.TrackTravelDisruptions.service.JourneyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class JourneyControllerTest {

    @Mock
    private JourneyServiceImpl mockJourneyServiceImpl;

    @InjectMocks
    private JourneyController journeyController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(journeyController).build();
        mapper = new ObjectMapper();

    }

    @Test
    @DisplayName("GetAllJourneyTest")
    void getAllJourney() throws Exception {
        //Arrange
        TransportProvider nationalRail = new TransportProvider(1L, "National Rail", "https://www.nationalrail.co.uk/", null);
        TransportProvider transportForLondon = new TransportProvider(2L, "Transport for London", "https://tfl.gov.uk/", null);
        Set<DayOfWeek> days = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        JourneyLeg journeyLeg1 = new JourneyLeg(1L, "Oxford", "Reading", 1, nationalRail, null);
        JourneyLeg journeyLeg2 = new JourneyLeg(2L, "Reading", "Paddington", 2, nationalRail, null);
        Set<JourneyLeg> journeyLegs = new HashSet<>(Arrays.asList(journeyLeg1, journeyLeg2));
        List<Journey> journeys = List.of(
                new Journey(1L, true, "Origin 1", "Destination 1", days, "08:00 AM", journeyLegs),
                new Journey(2L, false, "Origin 2", "Destination 2", days, "09:00 AM", journeyLegs)
        );
        // Act
        when(mockJourneyServiceImpl.getAllJourneys()).thenReturn(journeys);

        // Assert

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/journey"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].origin").value("Origin 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].destination").value("Destination 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departureTime").value("09:00 AM"));

        verify(mockJourneyServiceImpl, times(1)).getAllJourneys();
    }


    @Test
    @DisplayName("GetJourneyByIdTest")
    void getJourneyById() throws Exception {
        TransportProvider nationalRail = new TransportProvider(1L, "National Rail", "https://www.nationalrail.co.uk/", null);
        TransportProvider transportForLondon = new TransportProvider(2L, "Transport for London", "https://tfl.gov.uk/", null);
        Set<DayOfWeek> days = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        JourneyLeg journeyLeg1 = new JourneyLeg(1L, "Oxford", "Reading", 1, nationalRail, null);
        JourneyLeg journeyLeg2 = new JourneyLeg(2L, "Reading", "Paddington", 2, nationalRail, null);
        Set<JourneyLeg> journeyLegs = new HashSet<>(Arrays.asList(journeyLeg1, journeyLeg2));
        Journey journey = new Journey(1L, true, "Origin 1", "Destination 1", days, "08:00 AM", journeyLegs);

        when(mockJourneyServiceImpl.getJourneyById(1L)).thenReturn(journey);

        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/journey/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.origin").value("Origin 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value("Destination 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureTime").value("08:00 AM"));

    }

    @Test
    @DisplayName("AddNewJourneyTest")
    public void addNewJourneyTest() throws Exception {
        TransportProvider nationalRail = new TransportProvider(1L, "National Rail", "https://www.nationalrail.co.uk/", null);
        TransportProvider transportForLondon = new TransportProvider(2L, "Transport for London", "https://tfl.gov.uk/", null);
        Set<DayOfWeek> days = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        JourneyLeg journeyLeg1 = new JourneyLeg(1L, "Oxford", "Reading", 1, nationalRail, null);
        JourneyLeg journeyLeg2 = new JourneyLeg(2L, "Reading", "Paddington", 2, nationalRail, null);
        Set<JourneyLeg> journeyLegs = new HashSet<>(Arrays.asList(journeyLeg1, journeyLeg2));
        Journey journey = new Journey(1L, true, "Origin 1", "Destination 1", days, "08:00 AM", journeyLegs);

        when(mockJourneyServiceImpl.addNewJourney(journey)).thenReturn(journey);

        mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/journey")
                .contentType("application/json")
                .content(mapper.writeValueAsString(journey)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


}