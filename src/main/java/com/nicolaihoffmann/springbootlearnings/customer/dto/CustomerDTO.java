package com.nicolaihoffmann.springbootlearnings.customer.dto;

import com.nicolaihoffmann.springbootlearnings.customer.entity.Kontaktmoeglichkeiten;
import com.nicolaihoffmann.springbootlearnings.customer.entity.Namen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {

    UUID externalId;
    Kontaktform kontaktform;
    Namen namen;
    Kontaktmoeglichkeiten contact;
}
