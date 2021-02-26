package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class Test07VerifyingBehaviour {

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
    void should_InvokePayment_When_Prepaid(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 5), 2, true);
        //double price = 50 USD * 2 guests * 4 nights = 400 USD

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        verify(paymentServiceMock).pay(bookingRequest, 400.0);

        /*
        when calling with a wrong price, Mockito gives a comparison failure
        verify(paymentServiceMock).pay(bookingRequest, 500.0);
        Mockito tells that the invocation has different arguments from what it wants

        so verify not only verifies that the method has been called, but also that is called
        with the right arguments
        * */
        verifyNoMoreInteractions(paymentServiceMock);
        /*
        instead of this, you could have also added the check for interactions in the previous
        call, like this:
         */
        verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0);

    }

    @Test
    void should_NotInvokePayment_When_NotPrepaid(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 5), 2, false);

        //when
        bookingService.makeBooking(bookingRequest);
        //then
        /*
        Verify is for checking if the call actually happenend, either with or without exact parameters
         */
        //so verify that the pay() method was never invoked
        verify(paymentServiceMock, never()).pay(any(), anyDouble());
    }
}
