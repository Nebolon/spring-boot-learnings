package com.nicolaihoffmann.springbootlearnings.uraubsbuchung.service;

import com.nicolaihoffmann.springbootlearnings.uraubsbuchung.dto.BookingDTO;
import com.nicolaihoffmann.springbootlearnings.uraubsbuchung.entity.Booking;
import com.nicolaihoffmann.springbootlearnings.uraubsbuchung.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookingService {

    /***
     * Wenn Ihr das so mal findet, trozdem nicht nach machen.
     * Das ist ganz wichtig zum Testen, dass Ihr immer den Konstruktor füllt.
     * @return
     */
    /*@Autowired
    private BookingRepository bookingRepository;*/

    @Setter
    private BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(UUID externalId) {
        Booking booking = bookingRepository.findByExternalId(externalId).orElse(null);
        return (booking != null) ? convertToDTO(booking) : null;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        booking.setExternalId(UUID.randomUUID());
        Booking createdBooking = bookingRepository.save(booking);
        return convertToDTO(createdBooking);
    }

    public BookingDTO updateBooking(UUID externalId, BookingDTO newBookingDTO) {
        Booking existingBooking = bookingRepository.findByExternalId(externalId).orElse(null);
        if (existingBooking != null) {
            Booking updatedBooking = convertToEntity(newBookingDTO);
            updatedBooking.setId(existingBooking.getId());
            Booking savedBooking = bookingRepository.save(updatedBooking);
            return convertToDTO(savedBooking);
        }
        return null;
    }

    public void deleteBooking(UUID externalId) {
        bookingRepository.deleteByExternalId(externalId);
    }

    private BookingDTO convertToDTO(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        modelMapper.addConverter(new ExternalIdConverter());
        return modelMapper.map(bookingDTO, Booking.class);
    }

    public class ExternalIdConverter extends AbstractConverter<UUID, Long> {
        @Override
        protected Long convert(UUID source) {
            if (source == null) {
                return null;
            }
            // Hier kannst du die Logik für die Konvertierung von UUID zu Long implementieren.
            // Zum Beispiel kannst du die most significant bits und least significant bits kombinieren.
            // Hier ist ein einfaches Beispiel:
            return source.getMostSignificantBits() ^ source.getLeastSignificantBits();
        }
    }

}