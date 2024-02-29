package com.nicolaihoffmann.springbootlearnings.customer.entity;

import com.nicolaihoffmann.springbootlearnings.customer.dto.Kontaktform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private UUID externalId = UUID.randomUUID();
    private Kontaktform kontaktform;
    @OneToOne(cascade = CascadeType.ALL)
    private Namen namen;
    @OneToOne(cascade = CascadeType.ALL)
    private Kontaktmoeglichkeiten contact;

}
