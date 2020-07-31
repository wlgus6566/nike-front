package com.nike.dnp.repository.history;

import com.nike.dnp.entity.contents.RecentUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Recent upload repository.
 *
 * @author [오지훈]
 * @since 2020. 7. 31. 오후 4:12:15
 */
@Repository
public interface RecentUploadRepository extends JpaRepository<RecentUpload, Long>, RecentUploadRepositoryCustom {

}
