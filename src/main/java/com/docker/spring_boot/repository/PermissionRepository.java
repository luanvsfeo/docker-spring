package com.docker.spring_boot.repository;

import com.docker.spring_boot.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>{
	Permission findByName(String name);
}
