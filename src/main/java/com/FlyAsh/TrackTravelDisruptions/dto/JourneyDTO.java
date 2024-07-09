package com.FlyAsh.TrackTravelDisruptions.dto;

import jakarta.validation.constraints.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public record JourneyDTO
        (
                Long id,
                @NotNull(message = "UserId cannot be null") Long userId,
                @NotBlank(message = "OriginCRS cannot be blank") String originCRS,
                @NotBlank(message = "DestinationCRS cannot be blank") String destinationCRS,
                @NotBlank(message = "Departure time cannot be blank") LocalTime departureTime,
                @NotEmpty(message = "Days cannot be empty") Set<DayOfWeek> days,
                @NotNull(message = "Notifications cannot be null") Boolean notificationsEnabled
        )
{
}
