package com.nike.dnp.repository.manager;

import com.nike.dnp.entity.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByManagerSeq(Long managerSeq);

    Long countByManagerSeqOrManagerSeq(Long id1, Long id2);

    Long countByManagerSeqAndManagerName(Long id, String managerName);

    Long countByManagerIdAndManagerName(String managerId, String managerName);

}
