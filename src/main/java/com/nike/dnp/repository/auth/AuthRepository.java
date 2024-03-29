package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * AuthRepository
 *
 * @author [오지훈]
 * @since 2020. 7. 7. 오후 5:02:48
 * @implNote Auth(권한) Repository Interface 작성
 */
@Repository
public interface AuthRepository extends JpaRepository<Auth, Long>, AuthRepositoryCustom {

    /**
     * Find all by use yn list.
     *
     * @param useYn the use yn
     * @return the list
     * @author [오지훈]
     * @since 2020. 6. 24. 오후 3:41:00
     * @implNote (사용가능)권한 전체 목록
     */
    List<Auth> findAllByUseYn(String useYn);

    /**
     * Find all by use yn and upper auth seq is null list.
     *
     * @param useYn the use yn
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 20. 오후 2:47:13
     * @implNote
     */
    List<Auth> findAllByUseYnAndUpperAuthSeqIsNull(String useYn);

    /**
     * Find by role type optional.
     *
     * @param roleType the role type
     * @return the optional
     * @author [오지훈]
     * @since 2020. 7. 13. 오전 11:11:37
     * @implNote 권한 조회(Role Type으로)
     */
    Optional<Auth> findByRoleType(String roleType);

}
