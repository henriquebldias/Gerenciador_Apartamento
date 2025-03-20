package com.ecxus.hotel.dto;

import com.ecxus.hotel.entity.EstadoApartamento;
import com.ecxus.hotel.validator.ValueOfEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ApartamentoDTO {
	@NotNull(message = "O número do apartamento deve ser preenchido.")
	@Min(value = 0, message = "O número do apartamento não pode ser negativo.")
	Integer numero;
	
	@ValueOfEnum(enumClass = EstadoApartamento.class, message = "Estado do apartamento é inválido. Apenas: LIVRE ou LOCADO")
	String estado;
	
	public Integer getNumero() {
		return numero;
	}

	public EstadoApartamento getEstado() {
		if (this.estado == null) {
			return EstadoApartamento.LIVRE;
		}
		
		return EstadoApartamento.valueOf(this.estado);
	}
}
