package com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.dto.BookingDTO;
import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.entity.Booking;
import com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    public BookingControllerTest() {
    }


    @Test
    public void getAllBookings_ReturnsListOfBookings() throws Exception {
        BookingDTO bookingDTO1 = new BookingDTO(UUID.randomUUID(), "John Doe", "Paris");
        BookingDTO bookingDTO2 = new BookingDTO(UUID.randomUUID(), "Jane Doe", "London");
        List<BookingDTO> bookings = Arrays.asList(bookingDTO1, bookingDTO2);

        when(bookingService.getAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/v1/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].guestName").value("John Doe"))
                .andExpect(jsonPath("$[0].destination").value("Paris"))
                .andExpect(jsonPath("$[1].guestName").value("Jane Doe"))
                .andExpect(jsonPath("$[1].destination").value("London"));

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    public void createBooking_CreatesBookingAndReturnsDTO() throws Exception {
        BookingDTO newBookingDTO = new BookingDTO(null, "New Guest", "New Destination");

        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(newBookingDTO);

        mockMvc.perform(post("/v1/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newBookingDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.guestName").value("New Guest"))
                .andExpect(jsonPath("$.destination").value("New Destination"));

        verify(bookingService, times(1)).createBooking(any(BookingDTO.class));
    }

    @Test
    public void updateBooking_UpdatesBookingAndReturnsUpdatedDTO() throws Exception {
        UUID externalId = UUID.randomUUID();
        Booking existingBooking = new Booking(999L, externalId, "Existing Guest", "Existing Destination");
        BookingDTO updatedBookingDTO = new BookingDTO(externalId, "Updated Guest", "Updated Destination");

        when(bookingService.updateBooking(eq(externalId), any(BookingDTO.class))).thenReturn(updatedBookingDTO);

        mockMvc.perform(put("/v1/api/bookings/{externalId}", externalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBookingDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.guestName").value("Updated Guest"))
                .andExpect(jsonPath("$.destination").value("Updated Destination"));

        verify(bookingService, times(1)).updateBooking(eq(externalId), any(BookingDTO.class));
    }

    @Test
    public void deleteBooking_DeletesBookingAndReturnsNoContent() throws Exception {
        UUID externalId = UUID.randomUUID();

        mockMvc.perform(delete("/v1/api/bookings/{externalId}", externalId))
                .andExpect(status().isNoContent());

        verify(bookingService, times(1)).deleteBooking(externalId);
    }

    @Test
    public void getNonExistingBooking_ReturnsNotFound() throws Exception {
        UUID nonExistingExternalId = UUID.randomUUID();

        when(bookingService.getBookingById(nonExistingExternalId)).thenReturn(null);

        mockMvc.perform(get("/v1/api/bookings/{externalId}", nonExistingExternalId))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).getBookingById(nonExistingExternalId);
    }

    // Hilfsmethode zum Konvertieren von Objekten in JSON-Strings
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
