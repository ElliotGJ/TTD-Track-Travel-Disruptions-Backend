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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private Journey journey1;
    private Journey journey2;
    Set<DayOfWeek> days;
    JourneyLeg journeyLeg1;
    JourneyLeg journeyLeg2;
    Set<JourneyLeg> journeyLegs;
    TransportProvider nationalRail;
    TransportProvider transportForLondon;


    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(journeyController).build();
        mapper = new ObjectMapper();

        journey1 = new Journey(1L, true, "Origin 1", "Destination 1", days, "08:00 AM", journeyLegs);
        journey2 = new Journey(2L, false, "Origin 2", "Destination 2", days, "09:00 AM", journeyLegs);
        days = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        journeyLeg1 = new JourneyLeg(1L, "Oxford", "Reading", 1, nationalRail, null);
        journeyLeg2 = new JourneyLeg(2L, "Reading", "Paddington", 2, nationalRail, null);
        journeyLegs = new HashSet<>(Arrays.asList(journeyLeg1, journeyLeg2));
        nationalRail = new TransportProvider(1L, "National Rail", "https://www.nationalrail.co.uk/", null);
        transportForLondon = new TransportProvider(2L, "Transport for London", "https://tfl.gov.uk/", null);
    }

    @Test
    @DisplayName("GetAllJourneyTest")
    void getAllJourney() throws Exception {
        //Arrange
        List<Journey> journeys = List.of(journey1, journey2);
        // Act
        when(mockJourneyServiceImpl.getAllJourneys()).thenReturn(journeys);

        // Assert

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/journey"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].origin").value("Origin 1"))
                .andExpect(jsonPath("$[1].destination").value("Destination 2"))
                .andExpect(jsonPath("$[1].departureTime").value("09:00 AM"));

        verify(mockJourneyServiceImpl, times(1)).getAllJourneys();
    }


    @Test
    @DisplayName("GetJourneyByIdTest")
    void getJourneyById() throws Exception {
        when(mockJourneyServiceImpl.getJourneyById(1L)).thenReturn(journey1);

        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/journey/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.origin").value("Origin 1"))
                .andExpect(jsonPath("$.destination").value("Destination 1"))
                .andExpect(jsonPath("$.departureTime").value("08:00 AM"));

    }

    @Test
    @DisplayName("AddNewJourneyTest")
    public void addNewJourneyTest() throws Exception {
        Journey journey3 = new Journey(3L, true, "Origin 3", "Destination 3", days, "11:00 AM", journeyLegs);

        when(mockJourneyServiceImpl.addNewJourney(journey3)).thenReturn(journey3);

        mockMvcController.perform(MockMvcRequestBuilders.post("/api/v1/journey")
                .contentType("application/json")
                .content(mapper.writeValueAsString(journey3)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("UpdateJourneyById")
    public void updateJourneyByIdTest() throws Exception {
      Journey updatedJourney = new Journey(1L, false, "Origin 4", "Destination 4", days, "09:00 AM", journeyLegs);

        when(mockJourneyServiceImpl.updateJourneyById(any(Long.class), any(Journey.class))).thenReturn(updatedJourney);

        mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/journey/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(journey1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedJourney.getId()))
                .andExpect(jsonPath("$.origin").value(updatedJourney.getOrigin()))
                .andExpect(jsonPath("$.destination").value(updatedJourney.getDestination()));
    }

    @Test
    @DisplayName("UpdateJourneyById_Fail")
    public void updateJourneyByIdTest_wrongId() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ID_UPDATE"))
                .when(mockJourneyServiceImpl).updateJourneyById(eq(99L), any(Journey.class));

        mockMvcController.perform(MockMvcRequestBuilders.put("/api/v1/journey/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(journey1)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"Invalid ID_UPDATE\"", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("DeleteJourneyById")
    public void deleteJourneyByIdTest() throws Exception {
        doNothing().when(mockJourneyServiceImpl).deleteJourneyById(1L);

        mockMvcController.perform(MockMvcRequestBuilders.delete("/api/v1/journey/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}