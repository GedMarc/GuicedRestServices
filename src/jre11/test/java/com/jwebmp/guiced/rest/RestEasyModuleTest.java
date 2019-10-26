package com.guicedee.guiced.rest;

import com.guicedee.logger.LogFactory;
import com.guicedee.undertow.JWebMPUndertow;
import io.undertow.Undertow;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class RestEasyModuleTest
{

	@Test
	void configureServlets() throws Exception
	{
		LogFactory.configureConsoleColourOutput(Level.FINE);
		Undertow undertow = https://jwebmp.com/.boot("0.0.0.0", 6003);

		//Do stuff
		HttpClient client = HttpClient.newBuilder()
		                              .connectTimeout(Duration.of(5, ChronoUnit.SECONDS))
		                              .build();
		HttpResponse response = client.send(HttpRequest.newBuilder()
		                                               .GET()
		                                               .uri(new URI("http://localhost:6003/rest/hello/world"))
		                                               .build(),
		                                    HttpResponse.BodyHandlers.discarding());

		assertEquals(200, response.statusCode());
		undertow.stop();
	}
}
