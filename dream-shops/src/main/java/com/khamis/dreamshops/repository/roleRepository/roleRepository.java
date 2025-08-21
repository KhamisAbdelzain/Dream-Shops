package com.khamis.dreamshops.repository.roleRepository;

import com.khamis.dreamshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface roleRepository extends JpaRepository<Role,Long> {
    Role  findByName(String role) ;

}
