package com.nike.dnp.repository.history;

import com.nike.dnp.entity.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface History repository.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오후 4:12:11
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, HistoryRepositoryCustom {

}
