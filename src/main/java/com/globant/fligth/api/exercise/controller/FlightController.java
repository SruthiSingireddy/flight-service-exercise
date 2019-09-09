package com.globant.fligth.api.exercise.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globant.fligth.api.exercise.service.FlightService;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/api/flights",produces = APPLICATION_JSON_UTF8_VALUE)
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private Gson jsonBuilder;
	
	@GetMapping(value = "/airline")
	public String findFlightsByAirlineCode(@RequestParam("code") String airlineCode) {
		return jsonBuilder.toJson(flightService.findFlightsByAirlineCode(airlineCode));
	}
	
	@GetMapping(value = "/departureDate")
	public String findFlightsByDepartureDate(@RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
		return jsonBuilder.toJson(flightService.findFlightsByDepartureDate(date));
	}
	
}
