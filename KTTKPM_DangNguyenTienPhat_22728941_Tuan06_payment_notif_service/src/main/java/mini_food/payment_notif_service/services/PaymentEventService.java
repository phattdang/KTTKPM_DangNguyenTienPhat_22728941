package mini_food.payment_notif_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Random;
import mini_food.payment_notif_service.entities.Payment;
import mini_food.payment_notif_service.events.BookingCreatedEvent;
import mini_food.payment_notif_service.events.BookingFailedEvent;
import mini_food.payment_notif_service.events.PaymentCompletedEvent;
import mini_food.payment_notif_service.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String paymentCompletedTopic;
    private final String bookingFailedTopic;
    private final Random random = new Random();

    public PaymentEventService(
            PaymentRepository paymentRepository,
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            @Value("${app.kafka.topic.payment-completed}") String paymentCompletedTopic,
            @Value("${app.kafka.topic.booking-failed}") String bookingFailedTopic
    ) {
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.paymentCompletedTopic = paymentCompletedTopic;
        this.bookingFailedTopic = bookingFailedTopic;
    }

    @KafkaListener(topics = "${app.kafka.topic.booking-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void processPayment(String payload) {
        BookingCreatedEvent event;
        try {
            event = objectMapper.readValue(payload, BookingCreatedEvent.class);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Khong the parse BOOKING_CREATED event", ex);
        }
        boolean isSuccess = random.nextBoolean();
        Payment payment = new Payment();
        payment.setBookingId(event.getBookingId());
        payment.setPaymentMethod("SIMULATED");
        payment.setStatus(isSuccess ? "SUCCESS" : "FAILED");
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        if (isSuccess) {
            PaymentCompletedEvent completedEvent = new PaymentCompletedEvent();
            completedEvent.setBookingId(event.getBookingId());
            completedEvent.setUserId(event.getUserId());
            completedEvent.setUsername(event.getUsername());
            completedEvent.setMovieTitle(event.getMovieTitle());
            completedEvent.setSeatNumber(event.getSeatNumber());
            completedEvent.setPaidAt(LocalDateTime.now());
            sendJson(paymentCompletedTopic, String.valueOf(event.getBookingId()), completedEvent);
            return;
        }

        BookingFailedEvent failedEvent = new BookingFailedEvent();
        failedEvent.setBookingId(event.getBookingId());
        failedEvent.setUserId(event.getUserId());
        failedEvent.setUsername(event.getUsername());
        failedEvent.setMovieTitle(event.getMovieTitle());
        failedEvent.setSeatNumber(event.getSeatNumber());
        failedEvent.setReason("Simulated payment failure");
        failedEvent.setFailedAt(LocalDateTime.now());
        sendJson(bookingFailedTopic, String.valueOf(event.getBookingId()), failedEvent);
    }

    private void sendJson(String topic, String key, Object event) {
        try {
            kafkaTemplate.send(topic, key, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Khong the serialize event: " + topic, ex);
        }
    }
}
