package com.nike.dnp.repository.log;

import com.nike.dnp.entity.log.DownloadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DownloadLogRepository
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 5:50:48
 * @implNote DownloadLog(다운로드 로그) Repository 작성
 */
@Repository
public interface DownloadLogRepository extends JpaRepository<DownloadLog, Long> {
}
