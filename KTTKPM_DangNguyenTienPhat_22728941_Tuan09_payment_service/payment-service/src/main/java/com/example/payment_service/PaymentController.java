package com.example.payment_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
class PaymentController {

	private final PaymentFlowService paymentFlowService;

	PaymentController(PaymentFlowService paymentFlowService) {
		this.paymentFlowService = paymentFlowService;
	}

	@PostMapping("/payments")
	ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
		return ResponseEntity.ok(paymentFlowService.processPayment(request));
	}
}
