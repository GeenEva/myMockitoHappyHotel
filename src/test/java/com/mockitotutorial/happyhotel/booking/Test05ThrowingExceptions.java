package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test05ThrowingExceptions {

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
    void should_ThrowException_When_NoRoomAvailable(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 5), 2, false);
        when(roomServiceMock.findAvailableRoomId(bookingRequest))
                .thenThrow(BusinessException.class);
        //when

        /*
        org.junit.jupiter.api.function
        @FunctionalInterface
        @API(status = Status.STABLE, since = "5.0")
        public interface Executable

        Executable is a functional interface that can be used to implement any
        generic block of code that potentially throws a Throwable.
        The Executable interface is similar to Runnable, except that an Executable can throw any kind of exception.
        Rationale for throwing Throwable instead of Exception
        Although Java applications typically throw exceptions that are instances of Exception, RuntimeException, Error,
        or AssertionError (in testing scenarios), there may be use cases where an Executable needs to explicitly throw
        a Throwable.In order to support such specialized use cases, execute() is declared to throw Throwable.*/
        Executable executable = () -> bookingService.makeBooking(bookingRequest);
        //then
        assertThrows(BusinessException.class, executable);
    }
}
