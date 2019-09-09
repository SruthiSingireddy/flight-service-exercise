package com.globant.fligth.api.exercise.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIFlightInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String departureCity;
	private String destinationCity;
	private String departureDateTime;
	private String flight_number;
	private String airline;
	private String airlineCode;
	private String gate;
}
