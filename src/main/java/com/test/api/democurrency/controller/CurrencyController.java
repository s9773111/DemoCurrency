package com.test.api.democurrency.controller;

import com.test.api.democurrency.entity.CurrencyName;
import com.test.api.democurrency.service.BitcoinPriceService;
import com.test.api.democurrency.service.CurrencyNameService;
import com.test.api.democurrency.webservice.request.CurrencyRequest;
import com.test.api.democurrency.webservice.response.BitcoinPriceResponse;
import com.test.api.democurrency.webservice.response.CurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
				return ResponseEntity.ok("Completed (create)!");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add data.");
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception!");
		}
	}

	// Query All
	@PostMapping("/queryAll")
	public ResponseEntity<List<CurrencyResponse>> queryCurrencyName() {
		try {
			// all data
			List<CurrencyName> currencyNames = currencyNameService.getAllCurrencyName();

			// transform
			List<CurrencyResponse> responses = currencyNames.stream()
					.map(currency -> new CurrencyResponse(currency.getCurrencyCode(), currency.getCurrencyName()))
					.collect(Collectors.toList());

			if (responses != null) {
				return ResponseEntity.ok(responses);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
		}
	}


	// Update
	@PostMapping("/update")
	public ResponseEntity<?> updateCurrencyName(@RequestBody CurrencyRequest request) {
		try {
			CurrencyName currencyName = currencyNameService.updateCurrencyNameByCode(request);
			CurrencyResponse response = new CurrencyResponse(currencyName.getCurrencyCode(), currencyName.getCurrencyName());

			if (response != null) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Currency code not found.");
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

	// Delete
	@PostMapping("/delete")
	public ResponseEntity<String> deleteCurrencyName(@RequestBody CurrencyRequest request) {
		boolean isDeleted = currencyNameService.deleteCurrencyNameByCode(request.getCode());
		if (isDeleted) {
			return ResponseEntity.ok("Completed (delete)!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency code not found.");
		}
	}
}
