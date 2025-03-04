package net.petstore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import net.petstore.model.Order;
import net.petstore.model.OrderStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreApiControllerTest {

    @Mock
    private StoreApi storeApi;
    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HttpServletRequest mockRequest;

    @BeforeEach
    public void setUp() {
        storeApi = new StoreApiController(objectMapper, mockRequest);
    }

    @Test
    public void testPlaceOrderWithJsonAcceptHeader() throws IOException {
        // Mock order object
        Order order = new Order();
        order.setId(0L);
        order.setPetId(1L);
        order.setQuantity(6);
        order.setShipDate(OffsetDateTime.parse("2000-01-23T04:56:07.000+00:00"));
        order.setStatus(OrderStatusEnum.PLACED);
        order.setComplete(false);

        // Mock request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        when(mockRequest.getHeader("Accept")).thenReturn("application/json");

        // Mock ObjectMapper behavior
        when(objectMapper.readValue("{  \"shipDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"quantity\" : 6,  \"id\" : 0,  \"petId\" : 1,  \"complete\" : false,  \"status\" : \"placed\"}", Order.class)).thenReturn(order);

        // Execute the API call
        ResponseEntity<Order> response = storeApi.placeOrder(order);

        // Assert response status and body
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
        assertEquals(order, response.getBody());
    }



    @Test
    public void testPlaceOrderWithoutAcceptHeader() {
        // Mock order object (doesn't matter for this test)
        Order order = new Order();

        // No headers set

        // Execute the API call
        ResponseEntity<Order> response = storeApi.placeOrder(order);

        // Assert response status
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }
}