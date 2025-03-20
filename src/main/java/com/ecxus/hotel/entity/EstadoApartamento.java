package com.ecxus.hotel.entity;

public enum EstadoApartamento {
	LIVRE("LIVRE"), 
	LOCADO("LOCADO");

	private String estado;

	private EstadoApartamento(String estado) {
		this.estado = estado;
	}

	public String getEstadoApartamento() {
		return estado;
	}
}
