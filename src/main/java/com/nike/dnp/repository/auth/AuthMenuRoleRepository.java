package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.AuthMenuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AuthRepository
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 7. 오후 5:02:48
 * @Description Auth(권한) Repository Interface 작성
 */
@Repository
public interface AuthMenuRoleRepository extends JpaRepository<AuthMenuRole, Long> {

    /**
     * Find by auth seq list.
     *
     * @param authSeq the auth seq
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오전 10:05:40
     * @Description 권한 메뉴 역할 목록 조회
     */
    List<AuthMenuRole> findByAuthSeq(final Long authSeq);

    /**
     * Delete all by auth seq.
     *
     * @param authSeq the auth seq
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오후 3:33:21
     * @Description 권한 메뉴 역할 목록 삭제
     */
    @Transactional
    @Modifying
    @Query("delete from AuthMenuRole a where a.authSeq = :authSeq")
    void deleteAllByAuthSeq(@Param("authSeq") final Long authSeq);

    /**
     * Delete all by menu role seq array.
     *
     * @param authSeq          the auth seq
     * @param menuRoleSeqArray the menu role seq array
     * @author [오지훈]
     * @CreatedOn 2020. 7. 14. 오전 10:03:50
     * @Description 권한 메뉴 역할 목록 삭제
     */
    @Transactional
    @Modifying
    @Query("delete from AuthMenuRole a where a.authSeq = :authSeq and a.menuRoleSeq in :menuRoleSeqArray")
    void deleteAllByMenuRoleSeqArray(
            @Param("authSeq") final Long authSeq
            , @Param("menuRoleSeqArray") final Long... menuRoleSeqArray
    );

}
