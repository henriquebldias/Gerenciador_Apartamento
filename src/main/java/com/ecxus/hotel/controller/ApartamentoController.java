package com.ecxus.hotel.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecxus.hotel.dto.ApartamentoDTO;
import com.ecxus.hotel.entity.ApartamentoEntity;
import com.ecxus.hotel.repository.ApartamentoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("apartamento")
public class ApartamentoController {
	
	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@GetMapping
	public List<ApartamentoEntity> getAll() {
		return this.apartamentoRepository.findAll();
	}
	
	@PostMapping
	public ApartamentoEntity createApartamento(@Valid @RequestBody ApartamentoDTO data, BindingResult bindingResult) throws Exception {
		checkValidationErrors(bindingResult);
		
		ApartamentoEntity novaEntity = new ApartamentoEntity(data.getNumero());
		
		return this.apartamentoRepository.save(novaEntity);
	}
	
	@PutMapping("{id}")
	public ApartamentoEntity updateApartamento(@PathVariable String id, @Valid @RequestBody ApartamentoDTO data, BindingResult bindingResult) throws Exception {
		checkValidationErrors(bindingResult);
		
		if (!this.apartamentoRepository.existsById(id)) {
			throw new Exception("Apartamento não encontrado");
		}
		
		ApartamentoEntity novaEntity = new ApartamentoEntity();
		
		novaEntity.setId(id);
		novaEntity.setNumero(data.getNumero());
		novaEntity.setEstado(data.getEstado());		
		
		return this.apartamentoRepository.save(novaEntity);
	}
	
	@DeleteMapping("{id}")
	public void deleteApartamento(@PathVariable String id) throws Exception {
		if (!this.apartamentoRepository.existsById(id)) {
			throw new Exception("Apartamento não encontrado");
		}
		
		this.apartamentoRepository.deleteById(id);
	}

	/**
	 * Verifica por erros no payload e para a execução caso encontre.
	 * 
	 * @param bindingResult
	 * @throws Exception
	 */
	private void checkValidationErrors(BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			String errors = bindingResult.getFieldErrors().stream().map(arg0 -> arg0.getDefaultMessage()).collect(Collectors.joining(", "));
			
			throw new Exception("Erros de validação: " + errors);
		}
	}
}
