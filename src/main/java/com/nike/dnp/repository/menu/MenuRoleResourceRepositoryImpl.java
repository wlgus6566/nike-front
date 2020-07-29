package com.nike.dnp.repository.menu;

import com.nike.dnp.dto.menu.MenuRoleResourceReturnDTO;
import com.nike.dnp.entity.auth.QAuth;
import com.nike.dnp.entity.auth.QAuthMenuRole;
import com.nike.dnp.entity.menu.MenuRoleResource;
import com.nike.dnp.entity.menu.QMenuRole;
import com.nike.dnp.entity.menu.QMenuRoleResource;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MenuRepositoryImpl
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오전 11:51:57
 * @Description Menu(메뉴 역할 리소스) Repository interface 작성
 */
@Repository
public class MenuRoleResourceRepositoryImpl extends QuerydslRepositorySupport implements MenuRoleResourceRepositoryCustom {

    /**
     * Instantiates a new Menu role resource repository.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오전 11:51:57
     * @Description 생성자 주입
     */
    public MenuRoleResourceRepositoryImpl() {
        super(MenuRoleResource.class);
    }

    /**
     * Menu role resources list.
     *
     * @param authSeq the auth seq
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 13. 오전 11:19:07
     * @Description 권한별 리소스 목록
     */
    @Override
    public List<MenuRoleResourceReturnDTO> getResources(final Long authSeq) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QAuth qAuth = new QAuth("auth");
        final QAuthMenuRole qAuthMenuRole = new QAuthMenuRole("authMenuRole");
        final QMenuRole qMenuRole = new QMenuRole("menuRole");
        final QMenuRoleResource qMenuRoleResource = new QMenuRoleResource("menuRoleResource");

        return queryFactory
                .select(
                        Projections.bean(MenuRoleResourceReturnDTO.class
                            , qMenuRoleResource.menuRoleSeq
                            , qMenuRoleResource.resourceUrl
                            , qMenuRoleResource.resourceMethod
                        )
                )
                .from(qMenuRoleResource)
                .innerJoin(qMenuRole).on(qMenuRole.menuRoleSeq.eq(qMenuRoleResource.menuRoleSeq))
                .innerJoin(qAuthMenuRole).on(qAuthMenuRole.menuRoleSeq.eq(qMenuRole.menuRoleSeq))
                .innerJoin(qAuth).on(qAuth.authSeq.eq(qAuthMenuRole.authSeq))
                .where(qAuth.authSeq.eq(authSeq))
                //.orderBy(qMenuRoleResource.menuRoleResourceSeq.desc())
                .orderBy(qMenuRoleResource.resourceUrl.length().desc())
                .fetch()
                ;
    }
}
