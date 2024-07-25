package com.appartment.facilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appartment.facilities.entity.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer>{

}
