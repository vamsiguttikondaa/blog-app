package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogapp.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
