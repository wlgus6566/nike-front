package com.nike.dnp.repository.auth;

import com.nike.dnp.entity.auth.AuthMenuRole;
import com.nike.dnp.entity.auth.QAuthMenuRole;
import com.nike.dnp.entity.menu.MenuRole;
import com.nike.dnp.entity.menu.QMenuRole;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class AuthMenuRole
 *
 * @author [오지훈]
 * @since 2020. 6. 23. 오전 11:51:57
 * @implNote AuthMenuRole(권한) Repository interface 작성
 */
@Repository
public class AuthMenuRoleRepositoryImpl extends QuerydslRepositorySupport implements AuthMenuRoleRepositoryCustom {

    /**
     * Instantiates a new AuthMenuRole repository.
     *
     * @author [오지훈]
     * @since 2020. 6. 23. 오전 11:51:57
     * @implNote 생성자 주입
     */
    public AuthMenuRoleRepositoryImpl() {
        super(AuthMenuRole.class);
    }

    /**
     * Find by auth menu role join menu role list.
     *
     * @param authSeq the auth seq
     * @param menuSeq the menu seq
     * @return the list
     * @author [오지훈]
     * @implNote 특정 메뉴에 본인 그룹이 부여받은 권한 조회
     * @since 2020. 9. 3. 오후 4:39:08
     */
    @Override
    public List<MenuRole> findByAuthMenuRoleJoinMenuRole(final Long authSeq, final Long menuSeq) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QMenuRole menuRole = new QMenuRole("menuRole");
        final QAuthMenuRole authMenuRole = new QAuthMenuRole("authMenuRole");

        return queryFactory
                .select(menuRole)
                .from(menuRole)
                .innerJoin(authMenuRole)
                    .on(
                            menuRole.menuRoleSeq.eq(authMenuRole.menuRoleSeq)
                            , authMenuRole.authSeq.eq(authSeq)
                    )
                .where(menuRole.menuSeq.eq(menuSeq))
                .fetch();
    }
}
