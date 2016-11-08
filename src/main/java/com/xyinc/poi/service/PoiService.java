package com.xyinc.poi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyinc.poi.entity.Poi;
import com.xyinc.poi.repository.PoiRepository;

@Service
public class PoiService {

	@Autowired
	private PoiRepository repository;

	/**
	 * Metodo que retorna todos os Poi's cadastrados.
	 * 
	 * @return Collection<Poi>
	 */
	public Collection<Poi> findAll() {

		return (Collection<Poi>) this.repository.findAll();
	}

	/**
	 * Metodo que salva um Poi. Se tiver alguma regra de negocio para salvar, a
	 * mesma estara nessa classe.
	 * 
	 * @param poi
	 * @return Poi
	 */
	public Poi save(Poi poi) {

		return this.repository.save(poi);
	}

	/**
	 * Metodo que retorna os Poi's que estao proximos ao ponto informando,
	 * respeitando a distancia maxima informada.
	 * 
	 * @param x
	 * @param y
	 * @param maxDistance
	 * 
	 * @return Collection<Poi>
	 */
	public Collection<Poi> findPoisNearTo(Long x, Long y, Long maxDistance) {

		if ((x == null || x < 0) || (y == null || y < 0) || (maxDistance != null && maxDistance < 0)) {
			return Collections.emptyList();
		}

		Collection<Poi> pois = (Collection<Poi>) this.repository.findAll();

		return this.getPoisNearTo(pois, x, y, maxDistance);
	}

	/**
	 * Metodo que realiza o calculo para verificar os pontos que estao dentro da
	 * distancia maxima do ponto informado.
	 * 
	 * @param pois
	 * @param x
	 * @param y
	 * @param maxDistance
	 * 
	 * @return Collection<Poi>
	 */
	private Collection<Poi> getPoisNearTo(Collection<Poi> pois, Long x, Long y, Long maxDistance) {

		List<Poi> poisToReturn = new ArrayList<>();
		for (Poi poi : pois) {

			double d = Math.sqrt(Math.pow((x - poi.getX()), 2) + Math.pow(y - poi.getY(), 2));

			if (d <= maxDistance.doubleValue()) {
				poisToReturn.add(poi);
			}
		}

		return poisToReturn;
	}
}
