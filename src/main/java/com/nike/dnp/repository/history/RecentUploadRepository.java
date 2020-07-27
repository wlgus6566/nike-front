package com.nike.dnp.repository.history;

import com.nike.dnp.entity.contents.RecentUpload;
import com.nike.dnp.entity.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentUploadRepository extends JpaRepository<RecentUpload, Long>, RecentUploadRepositoryCustom {

}
