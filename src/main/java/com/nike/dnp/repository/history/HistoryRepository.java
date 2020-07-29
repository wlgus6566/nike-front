package com.nike.dnp.repository.history;

import com.nike.dnp.entity.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, HistoryRepositoryCustom {

}
