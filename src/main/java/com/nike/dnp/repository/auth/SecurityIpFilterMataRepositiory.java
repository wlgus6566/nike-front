package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.SecurityIpFilterMata;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Security ip filter mata repositiory.
 *
 * @author [오지훈]
 * @since 2020. 6. 24. 오후 6:11:59
 * @implNote
 */
public interface SecurityIpFilterMataRepositiory extends JpaRepository<SecurityIpFilterMata,Long> {

}
