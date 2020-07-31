package com.nike.dnp.repository.alarm;

import com.nike.dnp.entity.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Alarm repository.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오후 4:12:06
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepositoryCustom {
}
