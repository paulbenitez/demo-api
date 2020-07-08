package com.BCP.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.BCP.domain.CambioResponse;
import com.BCP.domain.Moneda;
import com.BCP.domain.TipoCambio;
import com.BCP.repo.IMonedaRepo;
import com.BCP.repo.ITipoCambioRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class TipoCambioServiceImpl implements ITipoCambioService
{
	@Autowired
	private IMonedaRepo monedaRepo;
	
	@Autowired
	private ITipoCambioRepo cambioRepo;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	@Qualifier("jdbcScheduler")
	private Scheduler jdbcScheduler;

	@Override
	public Flux<Moneda> listarMonedas() {
		return Flux.fromIterable(monedaRepo.findAll()).subscribeOn(jdbcScheduler);
	}

	@Override
	public Flux<TipoCambio> listarCambios() {
		return Flux.fromIterable(cambioRepo.findAll()).subscribeOn(jdbcScheduler);
	}

	@Override
	public Mono<TipoCambio> obtenerCambioId(Integer id) 
	{
		Mono<TipoCambio> retorno;
		
		try {
			TipoCambio tc = new TipoCambio();
			tc = cambioRepo.findById(id).get();
			
			retorno = Mono.just(tc);
		}catch(Exception e) {
			retorno = Mono.empty();
		}
		
		return retorno.subscribeOn(jdbcScheduler);
	}
	
	@Override
	public Mono<CambioResponse> calcularCambio(CambioResponse cambio) 
	{
		Mono<CambioResponse> retorno;
		
		try {
			List<TipoCambio> listafiltrada = new ArrayList<>();
			listafiltrada = cambioRepo.findByOrigenAndDestino(cambio.getTipoCambio().getOrigen(), cambio.getTipoCambio().getDestino());
			
			retorno = Flux.fromIterable(listafiltrada)
						.collectList()
						.flatMap(e -> {
							CambioResponse cr = new CambioResponse();
							Double valor = e.get(0).getValor();
							Double monto = cambio.getMontoInicial();
							Double montoFinal = valor * monto;
							
							montoFinal = Double.parseDouble(String.format("%.2f", montoFinal));
							cr.setMontoFinal(montoFinal);
							cr.setMontoInicial(monto);
							cr.setTipoCambio(e.get(0));
							
							return Mono.just(cr);
						});
		}catch(Exception e) {
			retorno = Mono.empty();
		}
		
		return retorno.subscribeOn(jdbcScheduler);
	}

	@Override
	public Mono<TipoCambio> actualizarValorCambio(TipoCambio tipoCambio) 
	{
		Mono<TipoCambio> retorno;
		int i = 0;
		
		try {
			if(tipoCambio.getId() != null && tipoCambio.getValor() != null) {
				retorno = Mono.fromCallable(() -> transactionTemplate.execute(action -> {
							TipoCambio tc = new TipoCambio();
							tc = cambioRepo.findById(tipoCambio.getId()).get();
							
							tc.setValor(tipoCambio.getValor());
							return cambioRepo.save(tc);
							}));
				i = 1;
			}else{
				retorno = Mono.empty();
			}
		}catch(Exception e) {
			retorno = Mono.empty();
		}finally {
			if(i == 0)
				retorno = Mono.empty();
		}
		
		return retorno.subscribeOn(jdbcScheduler);
	}
	
	@Override
	public Mono<TipoCambio> saveTipoCambio(TipoCambio tipoCambio) 
	{
		Mono<TipoCambio> retorno;
		int i = 0;
		
		try {
			if(!(tipoCambio.getValor() == null || tipoCambio.getOrigen() == null || tipoCambio.getDestino() == null)) {
				retorno = Mono.just(transactionTemplate.execute(action -> {
								return cambioRepo.save(tipoCambio);
							}));
				i=1;
			}else {
				retorno = Mono.empty();
			}
		}catch(Exception e) {
			retorno = Mono.empty();
		}finally {
			if(i == 0)
				retorno = Mono.empty();
		}
		
		return retorno.subscribeOn(jdbcScheduler);
	}

	@Override
	public Mono<Moneda> saveMoneda(Moneda moneda) 
	{
		Mono<Moneda> retorno;
		int i = 0;
		
		try {
			if(moneda.getDescripcion() != null) {
				retorno = Mono.just(transactionTemplate.execute(action -> {
								return monedaRepo.save(moneda);
							}));
				i=1;
			}else {
				retorno = Mono.empty();
			}
		}catch(Exception e) {
			retorno = Mono.empty();
		}finally {
			if(i == 0)
				retorno = Mono.empty();
		}
		
		return retorno.subscribeOn(jdbcScheduler);
	}

	@Override
	public Mono<Moneda> obtenerMonedaById(Integer id) {
		Mono<Moneda> retorno;
		
		try {
			Moneda md = new Moneda();
			md = monedaRepo.findById(id).get();
			
			retorno = Mono.just(md);
		}catch(Exception e) {
			retorno = Mono.empty();
		}
		
		return retorno.subscribeOn(jdbcScheduler);
	}
}
