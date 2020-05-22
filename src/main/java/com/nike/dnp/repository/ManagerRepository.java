package com.nike.dnp.repository;

import com.nike.dnp.dto.ManagerDTO;
import com.nike.dnp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerDTO, Long> {

    List<Member> findBySeq(long seq);

    List<Member> findById(String id);

    List<Member> findByName(String name);

    List<Member> findByNameLike(String keyword);

}
