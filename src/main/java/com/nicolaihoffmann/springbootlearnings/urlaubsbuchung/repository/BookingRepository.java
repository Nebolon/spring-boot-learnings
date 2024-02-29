package com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.repository;

import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByExternalId(UUID id);
    @Transactional
    void deleteByExternalId(UUID externalId);
}
