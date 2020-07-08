package com.BCP.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cambio")
public class TipoCambio implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_origen")
	private Moneda origen;
	
	@ManyToOne
	@JoinColumn(name = "id_destino")
	private Moneda destino;
	
	@Column(name = "tipo_cambio")
	private Double valor;
	
	public TipoCambio() {
		
	}

	public TipoCambio(Integer id, Moneda origen, Moneda destino, Double valor) {
		this.id = id;
		this.origen = origen;
		this.destino = destino;
		this.valor = valor;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Moneda getOrigen() {
		return origen;
	}

	public void setOrigen(Moneda origen) {
		this.origen = origen;
	}

	public Moneda getDestino() {
		return destino;
	}

	public void setDestino(Moneda destino) {
		this.destino = destino;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
