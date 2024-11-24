package com.test.api.democurrency.controller;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.service.BitcoinPriceService;
import com.test.api.democurrency.service.CurrencyNameService;
import com.test.api.democurrency.webservice.request.CurrencyRequest;
import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * author: Isaac
 * description: Bitcoin currency value related applications
 */
@RestController
@RequestMapping("/api/bitcoinPrice")
public class CurrencyController {

	@Autowired
	private BitcoinPriceService bitcoinPriceService;

	@Autowired
	private CurrencyNameService currencyNameService;

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


	// Create
	@PostMapping("/create")
	public ResponseEntity<String> createCurrencyCode(@RequestBody CurrencyRequest request) {
		try {
			CurrencyName currencyName = currencyNameService.addCurrencyItem(request.getCode(), request.getName());
			if (currencyName != null) {
				return ResponseEntity.ok("Complete!");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add data.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception!");
		}
	}
}
