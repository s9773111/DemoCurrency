package com.test.api.democurrency.controller;

import com.test.api.democurrency.service.BitcoinPriceService;
import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: Isaac
 * description: Bitcoin currency value related applications
 */
@RestController
@RequestMapping("/api/bitcoinPrice")
public class CurrencyController {

	@Autowired
	BitcoinPriceService bitcoinPriceService;

	// Get coindesk API information
	@GetMapping("/getCoindesk")
	public ResponseEntity<?> getCoindest() {
		 BitcoinPriceResponse response = bitcoinPriceService.getCoindest();
		 if (response != null) {
			return ResponseEntity.ok(response);
		 } else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch Bitcoin price data.");
		 }
	}

}
