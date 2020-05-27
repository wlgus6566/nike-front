package com.nike.dnp.repository.manager;

import com.nike.dnp.entity.manager.ManagerAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerAuthRepository extends JpaRepository<ManagerAuth, Long> {
}
