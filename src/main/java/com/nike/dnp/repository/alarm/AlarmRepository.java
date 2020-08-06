package com.nike.dnp.repository.alarm;

import com.nike.dnp.entity.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Alarm repository.
 *
 * @author [이소정]
 * @implNote 알림 repository Interface
 * @since 2020. 7. 30. 오후 3:12:41
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepositoryCustom {
}
