package com.example.schooladmin.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Long>{

    Optional<Role> findByName(RoleEnum name);
    
}
