package com.xyinc.poi.controller;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyinc.poi.entity.Poi;
import com.xyinc.poi.repository.PoiRepository;

@RestController
@RequestMapping("/poi")
public class PoiController {

	@Autowired
	private PoiRepository repository;

	@RequestMapping("/findAll")
	public Collection<Poi> findAll() {

		return (Collection<Poi>) this.repository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Poi save(Poi poi) {

		return this.repository.save(poi);
	}

	@RequestMapping("/findPoisNearTo")
	public Collection<Poi> findPoisNearTo(@RequestParam(value = "x", required = true) Long x,
			@RequestParam(value = "y", required = true) Long y,
			@RequestParam(value = "maxDistance", required = false, defaultValue = "0") Long maxDistance) {

		if ((x == null || x < 0) || (y == null || y < 0) || (maxDistance != null && maxDistance < 0)) {
			return Collections.emptyList();
		}

		return (Collection<Poi>) this.repository.findAll();
	}
}
