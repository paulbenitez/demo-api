package com.BCP.domain;

import java.io.Serializable;

public class CambioResponse implements Serializable
{
	private Double montoInicial;
	private Double montoFinal;
	private TipoCambio tipoCambio;
	
	public CambioResponse() {
		
	}

	public CambioResponse(Double montoInicial, Double montoFinal, TipoCambio tipoCambio) {
		this.montoInicial = montoInicial;
		this.montoFinal = montoFinal;
		this.tipoCambio = tipoCambio;
	}
	
	public Double getMontoInicial() {
		return montoInicial;
	}

	public void setMontoInicial(Double montoInicial) {
		this.montoInicial = montoInicial;
	}

	public Double getMontoFinal() {
		return montoFinal;
	}

	public void setMontoFinal(Double montoFinal) {
		this.montoFinal = montoFinal;
	}

	public TipoCambio getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(TipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
}
