package com.xyinc.poi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe que representa um ponto de interesse.
 * 
 * @author jmarques
 *
 */
@Entity
public class Poi {

	private Long id;

	private String name;

	private Long x;

	private Long y;

	public Poi() {

	}

	public Poi(String name, Long x, Long y) {

		this.name = name;
		this.x = x;
		this.y = y;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
	@NotNull
	@Size(min = 3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "x")
	@Min(0)
	public Long getX() {
		return x;
	}

	public void setX(Long x) {
		this.x = x;
	}

	@Column(name = "y")
	@Min(0)
	public Long getY() {
		return y;
	}

	public void setY(Long y) {
		this.y = y;
	}
}