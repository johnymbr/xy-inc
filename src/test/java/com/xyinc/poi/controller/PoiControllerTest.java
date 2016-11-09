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
public class PoiControllerTest {

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

		HttpEntity<String> httpEntity = new HttpEntity<String>(mapper.writeValueAsString(poi), requestHeaders);

		ResponseEntity<Poi> ret = template.postForEntity(this.base.toURI(), httpEntity, Poi.class);

		Assert.assertNotNull(ret.getBody());
		Assert.assertNotNull(ret.getBody().getId());

		poi = this.repository.findOne(ret.getBody().getId());

		Assert.assertNotNull(poi);
	}

	@Test
	public void savePoiWithouCoordX() throws Exception {

		Poi poi = new Poi("Test", null, 10L);
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(mapper.writeValueAsString(poi), requestHeaders);

		ResponseEntity<Poi> ret = template.postForEntity(this.base.toURI(), httpEntity, Poi.class);

		Assert.assertEquals(true, ret.getStatusCode().is5xxServerError());
	}

	@Test
	public void savePoiWithouCoordY() throws Exception {

		Poi poi = new Poi("Test", 10L, null);
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(mapper.writeValueAsString(poi), requestHeaders);

		ResponseEntity<Poi> ret = template.postForEntity(this.base.toURI(), httpEntity, Poi.class);

		Assert.assertEquals(true, ret.getStatusCode().is5xxServerError());
	}

	@Test
	public void savePoiWithCoordXLTZero() throws Exception {

		Poi poi = new Poi("Test", -1L, 10L);
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(mapper.writeValueAsString(poi), requestHeaders);

		ResponseEntity<Poi> ret = template.postForEntity(this.base.toURI(), httpEntity, Poi.class);

		Assert.assertEquals(true, ret.getStatusCode().is5xxServerError());
	}

	@Test
	public void savePoiWithCoordYLTZero() throws Exception {

		Poi poi = new Poi("Test", 10L, -10L);
		ObjectMapper mapper = new ObjectMapper();

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(mapper.writeValueAsString(poi), requestHeaders);

		ResponseEntity<Poi> ret = template.postForEntity(this.base.toURI(), httpEntity, Poi.class);

		Assert.assertEquals(true, ret.getStatusCode().is5xxServerError());
	}

	@Test
	public void findPoisNearToTest() throws Exception {

		Poi p1 = new Poi("Lanchonete", 27L, 12L);
		Poi p2 = new Poi("Posto", 31L, 18L);
		Poi p3 = new Poi("Joalheria", 15L, 12L);

		p1 = this.repository.save(p1);
		p2 = this.repository.save(p2);
		p3 = this.repository.save(p3);

		ResponseEntity<Poi[]> responseEntity = template
				.getForEntity(this.base + "/findPoisNearTo?x=20&y=10&maxDistance=10", Poi[].class);

		Assert.assertEquals(2, responseEntity.getBody().length);

		Poi retP1 = responseEntity.getBody()[0];
		Poi retP2 = responseEntity.getBody()[1];

		Assert.assertEquals("Lanchonete", retP1.getName());
		Assert.assertEquals("Joalheria", retP2.getName());

		this.repository.delete(p1.getId());
		this.repository.delete(p2.getId());
		this.repository.delete(p3.getId());
	}
}
