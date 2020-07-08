package com.BCP.service;

import com.BCP.domain.CambioResponse;
import com.BCP.domain.Moneda;
import com.BCP.domain.TipoCambio;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITipoCambioService 
{
	Flux<Moneda> listarMonedas();
	Mono<Moneda> obtenerMonedaById(Integer id);
	Mono<Moneda> saveMoneda(Moneda moneda);
	
	Flux<TipoCambio> listarCambios();
	Mono<CambioResponse> calcularCambio(CambioResponse cambio);
	Mono<TipoCambio> obtenerCambioId(Integer id);
	Mono<TipoCambio> actualizarValorCambio(TipoCambio tipoCambio);
	Mono<TipoCambio> saveTipoCambio(TipoCambio tipoCambio);
}
