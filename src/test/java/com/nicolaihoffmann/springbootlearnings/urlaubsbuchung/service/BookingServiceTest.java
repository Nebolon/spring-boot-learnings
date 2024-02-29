package com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.service;

import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.dto.BookingDTO;
import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.entity.Booking;
import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingServiceTest {

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @InjectMocks
    private BookingService bookingService;

    @Test
    void testGetAllBookings() {
        // Mock-Daten für Repository
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        // Test durchführen
        List<BookingDTO> result = bookingService.getAllBookings();

        // Überprüfen
        assertEquals(2, result.size());

        // Verifizieren, dass Repository-Methode aufgerufen wurde
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingById_ExistingBooking() {
        UUID externalId = UUID.randomUUID();
        Booking existingBooking = new Booking();
        when(bookingRepository.findByExternalId(externalId)).thenReturn(Optional.of(existingBooking));

        BookingDTO result = bookingService.getBookingById(externalId);

        assertNotNull(result);
    }

    @Test
    void testGetBookingById_NonExistingBooking() {
        UUID nonExistingExternalId = UUID.randomUUID();
        when(bookingRepository.findByExternalId(nonExistingExternalId)).thenReturn(Optional.empty());

        BookingDTO result = bookingService.getBookingById(nonExistingExternalId);

        assertNull(result);
    }

    @Test
    void testCreateBooking() {
        // Mock-Daten für createBooking
        BookingDTO bookingDTO = new BookingDTO();
        Booking booking = new Booking();
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Test durchführen
        BookingDTO result = bookingService.createBooking(bookingDTO);

        // Überprüfen
        assertNotNull(result);
        assertNotNull(result.getExternalId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testUpdateBooking_ExistingBooking() {
        UUID externalId = UUID.randomUUID();
        BookingDTO newBookingDTO = new BookingDTO();
        Booking existingBooking = new Booking();
        when(bookingRepository.findByExternalId(externalId)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(existingBooking);

        // Test durchführen
        BookingDTO result = bookingService.updateBooking(externalId, newBookingDTO);

        // Überprüfen
        assertNotNull(result);
        verify(bookingRepository, times(1)).findByExternalId(externalId);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testUpdateBooking_NonExistingBooking() {
        UUID nonExistingExternalId = UUID.randomUUID();
        BookingDTO newBookingDTO = new BookingDTO();
        when(bookingRepository.findByExternalId(nonExistingExternalId)).thenReturn(Optional.empty());

        // Test durchführen
        BookingDTO result = bookingService.updateBooking(nonExistingExternalId, newBookingDTO);

        // Überprüfen
        assertNull(result);
        verify(bookingRepository, times(1)).findByExternalId(nonExistingExternalId);
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void testDeleteBooking() {
        UUID externalId = UUID.randomUUID();

        // Test durchführen
        bookingService.deleteBooking(externalId);

        // Überprüfen
        verify(bookingRepository, times(1)).deleteByExternalId(externalId);
    }

}
