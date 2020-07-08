package com.BCP.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.BCP.domain.Moneda;
import com.BCP.domain.TipoCambio;

public interface ITipoCambioRepo extends CrudRepository<TipoCambio, Integer>{
	List<TipoCambio> findByOrigenAndDestino(Moneda origen, Moneda destino);
}
