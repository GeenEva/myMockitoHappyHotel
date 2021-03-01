package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class Test13StrictStubbing {

    @InjectMocks  //also injects spies
    private BookingService bookingService;

    @Mock
    private PaymentService paymentServiceMock;

    @Mock
    private RoomService roomServiceMock;

    @Mock
    private BookingDAO bookingDAOMock;

    @Mock
    private MailSender mailSenderMock;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;



    @Test
    void should_InvokePayment_When_Prepaid(){
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 5), 2, false);

        /* Because prepaid variable is set to false, the pay() method will not be used. So Mockito will throw an
        * exception, saying there is Unnecessary Stubbing detected.
        * If you however do want to stub this method (so evaluate the behaviour of the method), you can
        * use the lenient() method.*/

        lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");

        //when
        bookingService.makeBooking(bookingRequest);

        //then
        //no exception is thrown
    }


}
