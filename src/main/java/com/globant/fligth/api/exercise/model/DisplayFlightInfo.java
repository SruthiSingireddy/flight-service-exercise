package com.globant.fligth.api.exercise.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DisplayFlightInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String from;
	private String to;
	private String airline;
	private String flightNumber;
	private String departureDateTime;
	private String gate;
	private String airLineCode;
}
