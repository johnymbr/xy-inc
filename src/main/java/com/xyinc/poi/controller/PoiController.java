package com.xyinc.poi.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyinc.poi.entity.Poi;
import com.xyinc.poi.service.PoiService;

@RestController
@RequestMapping("/poi")
public class PoiController {

	@Autowired
	private PoiService service;

	@RequestMapping("/findAll")
	public Collection<Poi> findAll() {

		return (Collection<Poi>) this.service.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Poi save(@RequestBody Poi poi) {

		return this.service.save(poi);
	}

	@RequestMapping("/findPoisNearTo")
	public Collection<Poi> findPoisNearTo(@RequestParam(value = "x", required = true) Long x,
			@RequestParam(value = "y", required = true) Long y,
			@RequestParam(value = "maxDistance", required = false, defaultValue = "0") Long maxDistance) {

		return this.service.findPoisNearTo(x, y, maxDistance);
	}
}
