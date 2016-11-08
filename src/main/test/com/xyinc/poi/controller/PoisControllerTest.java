package com.xyinc.poi.controller;

import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyinc.poi.entity.Poi;
import com.xyinc.poi.repository.PoiRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PoisControllerTest {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private PoiRepository repository;

	@Before
	public void setUp() throws Exception {

		this.base = new URL("http://localhost:" + port + "/poi");
	}

	@Test
	public void findAllTest() throws Exception {

		ResponseEntity<Poi[]> responseEntity = template.getForEntity(this.base + "/findAll", Poi[].class);

		Assert.assertEquals(0, responseEntity.getBody().length);
	}

	@Test
	public void savePoiTest() throws Exception {

		Poi poi = new Poi("Test", 10L, 10L);
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		// Creating http entity object with request body and headers
		HttpEntity<String> httpEntity = new HttpEntity<String>(mapper.writeValueAsString(poi), requestHeaders);

		ResponseEntity<Poi> ret = template.postForEntity(this.base.toURI(), httpEntity, Poi.class);

		Assert.assertNotNull(ret.getBody());
		Assert.assertNotNull(ret.getBody().getId());

		poi = this.repository.findOne(ret.getBody().getId());

		Assert.assertNotNull(poi);
	}
}
