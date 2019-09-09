package com.globant.fligth.api.exercise.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.globant.fligth.api.exercise.model.APIFlightInfo;
import com.globant.fligth.api.exercise.model.DisplayFlightInfo;

@Service
public class FlightServiceImpl implements FlightService {
	@Value("${flights_url}")
	private String flightsServciceUrl;

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * <p>
	 * findFlights method retrieves all flights from
	 * FlightService  http://demo3998236.mockable.io/flights;
	 * </p>
	 * 
	 * @param
	 * @return list of Display Flights from the API
	 * @since 1.0
	 */
	@Override
	public List<DisplayFlightInfo> findFlights() {			
		ResponseEntity<List<APIFlightInfo>> response = restTemplate.exchange(flightsServciceUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<APIFlightInfo>>() {
				});

		return Optional.ofNullable(translateFlightAPI(response.getBody())).orElse(new ArrayList<>());
	}
	
	/**
	 * <p>
	 * findFlightsByAirlineCode method filters flights by airlineCode requested
	 * FlightService  http://demo3998236.mockable.io/flights;
	 * 
	 * If service doesn't have any data for requested airline code returns empty list
	 * </p>
	 * 
	 * @param
	 * @return list of filtered Display Flights by airline code from the API
	 * @since 1.0
	 */
	@Override
	public List<DisplayFlightInfo> findFlightsByAirlineCode(String airlineCode) {
		List<DisplayFlightInfo> flights = findFlights();
		return flights.stream().filter(checkAirlineCode(airlineCode)).collect(Collectors.toList());
	}
	
	/**
	 * <p>
	 * findFlightsByDepartureDate method filters flights by departureDate requested
	 * FlightService  http://demo3998236.mockable.io/flights;
	 * 
	 * If service doesn't have any data for requested departure date returns empty list
	 * </p>
	 * 
	 * @param
	 * @return list of filtered Display Flights by departureDate from the API
	 * @since 1.0
	 */
	@Override
	public List<DisplayFlightInfo> findFlightsByDepartureDate(LocalDate departureDate) {
		List<DisplayFlightInfo> flights = findFlights();
		return flights.stream().filter(checkDate(departureDate)).collect(Collectors.toList());
	}
	
	public Predicate<DisplayFlightInfo> checkAirlineCode(String code) {
		return x -> code.equals(Optional.ofNullable(x.getAirLineCode()).orElse(null));
	}

	public Predicate<DisplayFlightInfo> checkDate(LocalDate date) {
		return x -> date.equals(parseDate(Optional.ofNullable(x.getDepartureDateTime()).orElse(null)));
	}

	private Object parseDate(String date) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

		if (Optional.ofNullable(date).isPresent()) {
			return LocalDate.parse(outputFormatter.format(LocalDate.parse(date, inputFormatter)), outputFormatter);
		}
		return null;
	}
	
	public List<DisplayFlightInfo> translateFlightAPI(List<APIFlightInfo> flightsAPIResponse) {
		List<DisplayFlightInfo> flights = new ArrayList<>();
		for (APIFlightInfo flightAPIResponse : flightsAPIResponse) {
			DisplayFlightInfo flight = new DisplayFlightInfo();
			flight.setAirline(flightAPIResponse.getAirline());
			flight.setDepartureDateTime(flightAPIResponse.getDepartureDateTime());
			flight.setFlightNumber(flightAPIResponse.getFlight_number());
			flight.setFrom(flightAPIResponse.getDepartureCity());
			flight.setTo(flightAPIResponse.getDestinationCity());
			flight.setGate(flightAPIResponse.getGate());
			flight.setAirLineCode(flightAPIResponse.getAirlineCode());
			flights.add(flight);
		}
		return flights;
	}
}
