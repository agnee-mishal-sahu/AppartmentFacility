package com.appartment.facilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appartment.facilities.entity.User;
@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{

	User findByUserName(String username);

}
