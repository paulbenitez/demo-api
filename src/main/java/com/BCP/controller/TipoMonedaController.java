package com.BCP.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BCP.domain.CambioResponse;
import com.BCP.domain.Moneda;
import com.BCP.domain.TipoCambio;
import com.BCP.service.ITipoCambioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class TipoMonedaController 
{
	@Autowired
	private ITipoCambioService service;
	
	@GetMapping("/listar/monedas")
	public Mono<ResponseEntity<Flux<Moneda>>> listarMonedas(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.listarMonedas())
				);
	}
	
	@GetMapping("/listar/cambios")
	public Mono<ResponseEntity<Flux<TipoCambio>>> listarCambios(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.listarCambios())
				);
	}
	
	@GetMapping("/listar/cambios/{id}")
	public Mono<ResponseEntity<TipoCambio>> verTipoCambio(@PathVariable String id){
		return service.obtenerCambioId(Integer.parseInt(id))
				.map(mapper -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/cambio/calcular")
	public Mono<ResponseEntity<CambioResponse>> calcularCambio(@RequestBody CambioResponse tipoCambio)
	{
		return service.calcularCambio(tipoCambio)
				.map(mapper -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/cambio/save")
	public Mono<ResponseEntity<TipoCambio>> actualizarValorCambio(@RequestBody TipoCambio tipoCambio)
	{	
		return service.actualizarValorCambio(tipoCambio)
				.map(p -> ResponseEntity.created(URI.create("/api/listar/cambios/".concat(p.getId().toString())))
						  .contentType(MediaType.APPLICATION_JSON)
						  .body(p))
				.defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@PostMapping("/cambio/save")
	public Mono<ResponseEntity<TipoCambio>> regsitrarCambio(@RequestBody TipoCambio tipoCambio)
	{	
		return service.saveTipoCambio(tipoCambio)
				.map(p -> ResponseEntity.created(URI.create("/api/listar/cambios/".concat(p.getId().toString())))
						  .contentType(MediaType.APPLICATION_JSON)
						  .body(p))
				.defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@PostMapping("/moneda/save")
	public Mono<ResponseEntity<Moneda>> regsitrarMoneda(@RequestBody Moneda moneda)
	{	
		return service.saveMoneda(moneda)
				.map(p -> ResponseEntity.created(URI.create("/api/listar/monedas/".concat(p.getId().toString())))
						  .contentType(MediaType.APPLICATION_JSON)
						  .body(p))
				.defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@GetMapping("/listar/monedas/{id}")
	public Mono<ResponseEntity<Moneda>> verMoneda(@PathVariable String id){
		return service.obtenerMonedaById(Integer.parseInt(id))
				.map(mapper -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(mapper))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
