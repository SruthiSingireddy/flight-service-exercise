package com.globant.fligth.api.exercise.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.globant.fligth.api.exercise.model.APIFlightInfo;
import com.globant.fligth.api.exercise.model.DisplayFlightInfo;
import com.globant.fligth.api.exercise.util.FlightUtil;



@RunWith(MockitoJUnitRunner.class)
public class FlightServiceImplTest {

	@InjectMocks
	FlightServiceImpl flightService = new FlightServiceImpl();

	@Mock
	RestTemplate restTemplate;

	@Mock
	ResponseEntity<List<APIFlightInfo>> responseEntityStr;

	List<APIFlightInfo> apiFlights;

	List<DisplayFlightInfo> displayFlights;

	FlightUtil flightUtil;

	String flightServiceURL = "/xyz/";

	@Before
	public void init() throws Exception {
		ReflectionTestUtils.setField(flightService, "flightsServciceUrl", flightServiceURL);
		flightUtil = new FlightUtil();
		apiFlights = flightUtil.readSampleFromFile("./src/test/resources/flights.json");
	}

	@Test
	public void test_findFlights() {

		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(),
				ArgumentMatchers.<ParameterizedTypeReference<List<APIFlightInfo>>>any())).thenReturn(responseEntityStr);
		Mockito.when(responseEntityStr.getBody()).thenReturn(apiFlights);

		List<DisplayFlightInfo> displayFlights = flightService.findFlights();
		assertNotNull(displayFlights);
		assertEquals(5, displayFlights.size());
	}

	@Test
	public void test_findFlightsByAirlineCode() {
		String airlineCode = "DA";
		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(),
				ArgumentMatchers.<ParameterizedTypeReference<List<APIFlightInfo>>>any())).thenReturn(responseEntityStr);
		Mockito.when(responseEntityStr.getBody()).thenReturn(apiFlights);

		List<DisplayFlightInfo> displayFlights = flightService.findFlightsByAirlineCode(airlineCode);
		assertNotNull(displayFlights);
	}

	@Test
	public void test_findFlightsByDepartureDate() {
		LocalDate departureDate = LocalDate.parse("2019-10-15");
		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(),
				ArgumentMatchers.<ParameterizedTypeReference<List<APIFlightInfo>>>any())).thenReturn(responseEntityStr);
		Mockito.when(responseEntityStr.getBody()).thenReturn(apiFlights);

		List<DisplayFlightInfo> displayFlights = flightService.findFlightsByDepartureDate(departureDate);
		assertNotNull(displayFlights);
	}
	
	@Test
	public void test_findFlightsByAirlineCodeWithNoFlights() {
		String airlineCode = "DAM";
		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(),
				ArgumentMatchers.<ParameterizedTypeReference<List<APIFlightInfo>>>any())).thenReturn(responseEntityStr);
		Mockito.when(responseEntityStr.getBody()).thenReturn(apiFlights);

		List<DisplayFlightInfo> displayFlights = flightService.findFlightsByAirlineCode(airlineCode);
		assertThat(displayFlights.isEmpty(), is(true));
	}
	
	@Test
	public void test_findFlightsByDepartureDateWithNoFlights() {
		LocalDate departureDate = LocalDate.parse("2019-12-15");
		Mockito.when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
				ArgumentMatchers.<HttpEntity<?>>any(),
				ArgumentMatchers.<ParameterizedTypeReference<List<APIFlightInfo>>>any())).thenReturn(responseEntityStr);
		Mockito.when(responseEntityStr.getBody()).thenReturn(apiFlights);

		List<DisplayFlightInfo> displayFlights = flightService.findFlightsByDepartureDate(departureDate);
		assertThat(displayFlights.isEmpty(), is(true));
	}

}
