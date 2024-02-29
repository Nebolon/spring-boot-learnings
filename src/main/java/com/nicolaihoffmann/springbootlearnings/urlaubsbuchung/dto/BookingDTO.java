package com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class BookingDTO {
    private UUID externalId;
    private String guestName;
    private String destination;
}
