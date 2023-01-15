package com.gordwilling.futurestay;

import com.gordwilling.futurestay.web.BookingsRestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FutureStayWebAppTest {

    @Inject
    BookingsRestController bookingsRestController;

    @Test
    @DisplayName("The spring app should initialize itself successfully")
    void contextLoads() {
        assertNotNull(bookingsRestController);
    }
}
