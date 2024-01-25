package com.nicolaihoffmann.springbootlearnings.uraubsbuchung.repository;

import com.nicolaihoffmann.springbootlearnings.uraubsbuchung.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByExternalId(UUID id);
    void deleteByExternalId(UUID externalId);
}
