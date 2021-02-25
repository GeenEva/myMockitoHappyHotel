package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class Test01FirstMocks {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock,
                bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_CalculateCorrectPrice_When_CorrectInput(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 5), 2, false);
        double expected = 4 * 2 * 50.0; // 4 nights * 2 guests * 50 USD (BASE_PRICE_USD in BookingService);
        //when
        double actual = bookingService.calculatePrice(bookingRequest);
        //then
        assertEquals(expected, actual);
    }
}
