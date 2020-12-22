package com.nike.dnp.repository.contents;

import com.nike.dnp.entity.contents.ContentsNotificationMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsNotificationMailRepository extends JpaRepository<ContentsNotificationMail, Long> {

}
