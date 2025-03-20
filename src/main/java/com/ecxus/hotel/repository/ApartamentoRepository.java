package com.ecxus.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecxus.hotel.entity.ApartamentoEntity;

public interface ApartamentoRepository extends JpaRepository<ApartamentoEntity, String> {	
}
