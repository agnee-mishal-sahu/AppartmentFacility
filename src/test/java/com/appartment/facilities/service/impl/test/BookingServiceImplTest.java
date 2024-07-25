package com.appartment.facilities.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.appartment.facilities.constants.MessageConstants;
import com.appartment.facilities.constants.ValidationConstants;
import com.appartment.facilities.dto.BookingDto;
import com.appartment.facilities.dto.CreateBookingResponseDto;
import com.appartment.facilities.entity.Booking;
import com.appartment.facilities.entity.Facility;
import com.appartment.facilities.entity.Resident;
import com.appartment.facilities.exception.BookingException;
import com.appartment.facilities.repository.BookingRepository;
import com.appartment.facilities.repository.FacilityRepository;
import com.appartment.facilities.repository.ResidentRepository;
import com.appartment.facilities.service.impl.BookingServiceImpl;

@SpringBootTest
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ResidentRepository residentRepository;

    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBookingSuccess() throws BookingException {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setFacilityId(1);
        bookingDto.setResidentId(1);

        Booking booking = new Booking();
        booking.setId(1);
        booking.setFacilityId(1);
        booking.setResidentId(1);
        booking.setStatus("Pending");

        Facility facility = new Facility();
        facility.setId(1);
        facility.setStatus("Available");

        when(residentRepository.findById(1)).thenReturn(Optional.of(new Resident()));
        when(facilityRepository.findById(1)).thenReturn(Optional.of(facility));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        CreateBookingResponseDto responseDto = bookingService.createBooking(bookingDto);

        assertEquals(MessageConstants.BOOKING_STATUS_SUCCESS, responseDto.getMessage());
        assertNotNull(responseDto.getBookingDto().getId());
        verify(facilityRepository).save(any(Facility.class));
    }

    @Test
    public void testGetAllBooking() {
        Booking booking = new Booking();
        booking.setId(1);
        booking.setFacilityId(1);
        booking.setResidentId(1);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking));

        List<BookingDto> bookings = bookingService.getAllBooking();

        assertEquals(1, bookings.size());
    }

    @Test
    public void testApproveOrRejectBooking() throws BookingException {
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStatus("Pending");

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        String result = bookingService.approveOrRejectBooking(1, true);

        assertEquals("booking with id:1 is accepted", result);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    public void testGetBookingById() throws BookingException {
        Booking booking = new Booking();
        booking.setId(1);
        booking.setFacilityId(1);
        booking.setResidentId(1);

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        BookingDto bookingDto = bookingService.getBookingById(1);

        assertEquals(1, bookingDto.getId());
    }

    @Test
    public void testCancelBooking() throws BookingException {
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStatus("Pending");

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        BookingDto bookingDto = bookingService.cancelBooking(1);

        assertEquals(MessageConstants.BOOKING_CANCELLED, bookingDto.getStatus());
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    public void testCreateBookingWithInvalidFacility() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setFacilityId(1);
        bookingDto.setResidentId(1);

        when(facilityRepository.findById(1)).thenReturn(Optional.empty());

        BookingException exception = assertThrows(BookingException.class, () -> {
            bookingService.createBooking(bookingDto);
        });

        assertEquals(ValidationConstants.INVALID_FACILITY_FOR_BOOKING, exception.getMessage());
    }

    @Test
    public void testCreateBookingWithInvalidResident() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setFacilityId(1);
        bookingDto.setResidentId(1);

        Facility facility = new Facility();
        facility.setId(1);
        facility.setStatus("Available");

        when(facilityRepository.findById(1)).thenReturn(Optional.of(facility));
        when(residentRepository.findById(1)).thenReturn(Optional.empty());

        BookingException exception = assertThrows(BookingException.class, () -> {
            bookingService.createBooking(bookingDto);
        });

        assertEquals(ValidationConstants.INVALID_RESIDENT_FOR_BOOKING, exception.getMessage());
    }


}

