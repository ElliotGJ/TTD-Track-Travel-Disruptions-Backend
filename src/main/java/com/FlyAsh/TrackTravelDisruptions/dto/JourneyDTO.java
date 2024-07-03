package com.FlyAsh.TrackTravelDisruptions.dto;

import jakarta.validation.constraints.*;
import java.time.DayOfWeek;
import java.util.Set;

public record JourneyDTO
        (
                @NotBlank(message = "Journey name cannot be blank") long journeyId,
                @NotBlank(message = "Origin cannot be blank") String origin,
                @NotBlank(message = "Destination cannot be blank") String destination,
                @NotBlank(message = "Departure time cannot be blank") String departureTime,
                @NotEmpty(message = "Days cannot be empty") Set<DayOfWeek> days,
                @NotNull(message = "Notifications cannot be null") Boolean notificationsEnabled
        )
{
}
