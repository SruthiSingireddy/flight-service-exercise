package com.globant.fligth.api.exercise.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.fligth.api.exercise.model.APIFlightInfo;

@Component
public class FlightUtil {
	public List<APIFlightInfo> readSampleFromFile(String fileName)
			throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse(new FileReader(fileName));
		ObjectMapper m = new ObjectMapper();
		List<APIFlightInfo> transactions = m.readValue(a.toString(), new TypeReference<List<APIFlightInfo>>() {});
		return transactions;
	}
	
}
