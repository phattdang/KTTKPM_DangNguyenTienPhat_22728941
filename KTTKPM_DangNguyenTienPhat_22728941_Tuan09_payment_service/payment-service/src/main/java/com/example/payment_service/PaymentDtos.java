package com.example.payment_service;

import java.math.BigDecimal;

record PaymentRequest(Long bookingId, String bookingCode, Long userId, Long tourId, BigDecimal amount, String paymentMethod, String payerName) {
}

record PaymentResponse(
    boolean success,
    Long paymentId,
    String paymentCode,
    String status,
    String message,
    Long bookingId,
    BigDecimal amount) {
}
