package mini_food.payment_notif_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mini_food.payment_notif_service.events.BookingFailedEvent;
import mini_food.payment_notif_service.events.PaymentCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationEventListener.class);
    private final ObjectMapper objectMapper;

    public NotificationEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${app.kafka.topic.payment-completed}", groupId = "notification-group")
    public void handlePaymentCompleted(String payload) {
        try {
            PaymentCompletedEvent event = objectMapper.readValue(payload, PaymentCompletedEvent.class);
            LOGGER.info("Booking #{} thành công!", event.getBookingId());
            LOGGER.info("User {} đã đặt đơn #{} thành công", event.getUsername(), event.getBookingId());
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Khong the parse PAYMENT_COMPLETED notification event", ex);
        }
    }

    @KafkaListener(topics = "${app.kafka.topic.booking-failed}", groupId = "notification-group")
    public void handleBookingFailed(String payload) {
        try {
            BookingFailedEvent event = objectMapper.readValue(payload, BookingFailedEvent.class);
            LOGGER.info("Booking #{} thất bại: {}", event.getBookingId(), event.getReason());
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Khong the parse BOOKING_FAILED notification event", ex);
        }
    }
}
