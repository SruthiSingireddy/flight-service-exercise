package com.globant.fligth.api.exercise.service;

import java.time.LocalDate;
import java.util.List;

import com.globant.fligth.api.exercise.model.DisplayFlightInfo;

public interface FlightService {
	public List<DisplayFlightInfo> findFlights();

	public List<DisplayFlightInfo> findFlightsByAirlineCode(String airlineCode);

	public List<DisplayFlightInfo> findFlightsByDepartureDate(LocalDate date);
}
