package com.task08;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import com.fasterxml.jackson.databind.*;

public class ApiHandler {
	private static final String API_URL =
			"https://api.open-meteo.com/v1/forecast?latitude=50.43752&longitude=30.5&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m";

	private HttpClient httpClient;
	private ObjectMapper objectMapper;
	public ApiHandler(){
		this.httpClient=HttpClient.newHttpClient();
		this.objectMapper=new ObjectMapper();
	}

	public JsonNode getWeatherData() throws Exception {
		HttpRequest httpRequest=HttpRequest.newBuilder()
				.uri(URI.create(API_URL))
				.GET()
				.header("Accept", "application/json")
				.build();

		CompletableFuture<HttpResponse<String>> responseCompletableFuture = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

		HttpResponse<String> response=responseCompletableFuture.get();
		if (response.statusCode()==200){
			return objectMapper.readTree(response.body());
		}
		else {
			throw new RuntimeException("Failed to get weather data: " + response.statusCode());
		}

	}

}