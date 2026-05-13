package com.example.payment_service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
class PaymentFlowService {

	private final Map<Long, PaymentResponse> payments = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong(5000);
	private final Random random = new Random();

	PaymentResponse processPayment(PaymentRequest request) {
		boolean success = random.nextInt(100) >= 30;
		if (!success) {
			return new PaymentResponse(false, null, null, "FAILED", "Payment rejected by gateway", request.bookingId(), request.amount());
		}

		long paymentId = sequence.getAndIncrement();
		String paymentCode = "PAY-" + paymentId + "-" + LocalDateTime.now().getSecond();
		PaymentResponse response = new PaymentResponse(true, paymentId, paymentCode, "PAID", "Payment completed successfully", request.bookingId(), request.amount());
		payments.put(paymentId, response);
		return response;
	}
}
