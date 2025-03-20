package com.ecxus.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "apartamento")
public class ApartamentoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(unique=true)
	private int numero;

	@Enumerated(EnumType.STRING)
	private EstadoApartamento estado = EstadoApartamento.LIVRE;
	
	public ApartamentoEntity() { }
	
	public ApartamentoEntity(int numero) {
		this.numero = numero;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public EstadoApartamento getEstado() {
		return estado;
	}

	public void setEstado(EstadoApartamento estado) {
		this.estado = estado;
	}

}
