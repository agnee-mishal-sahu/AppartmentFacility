package com.appartment.facilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appartment.facilities.entity.Manager;
@Repository
public interface ManagerRepository  extends JpaRepository<Manager, Integer>{

}
