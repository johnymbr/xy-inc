package com.xyinc.poi.repository;

import org.springframework.data.repository.CrudRepository;

import com.xyinc.poi.entity.Poi;

/**
 * Interface que contem os metodos para operacoes em banco dos pontos de
 * interesse.
 * 
 * @author jmarques
 *
 */
public interface PoiRepository extends CrudRepository<Poi, Long> {

	/**
	 * Metodo que procura um poi baseado no nome, na coordenada x e na
	 * coordenada y informadas.
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * 
	 * @return Poi
	 */
	Poi findByNameAndXAndY(String name, Long x, Long y);
}