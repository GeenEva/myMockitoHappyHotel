package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Test08Spies {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOSpy;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOSpy = spy(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock,
                bookingDAOSpy, mailSenderMock);
    }

    @Test
    void should_MakeBooking_When_InputOK(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 5), 2, true);

        //when
        String bookingId = bookingService.makeBooking(bookingRequest);

        //then
        verify(bookingDAOSpy).save(bookingRequest);
        System.out.println("bookingId = " + bookingId);
    }

    @Test
    void should_CancelBooking_When_InputOK(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 5), 2, true);

        bookingRequest.setRoomId("1.3");
        String bookingId = "1";

        //modify behaviour of the spy
        doReturn(bookingRequest).when(bookingDAOSpy).get(bookingId);

        //when
        bookingService.cancelBooking(bookingId);

        //then
        verify(bookingDAOSpy, never()).save(bookingRequest);
        verify(bookingDAOSpy, times(1)).get(bookingId);
        System.out.println("bookingId = " + bookingId);
    }

}
