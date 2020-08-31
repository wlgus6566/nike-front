package com.nike.dnp.repository.auth;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.dto.user.UserAuthSearchDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.auth.QAuth;
import com.nike.dnp.entity.auth.QAuthMenuRole;
import com.nike.dnp.entity.menu.QMenu;
import com.nike.dnp.entity.menu.QMenuRole;
import com.nike.dnp.entity.user.QUserContents;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class Auth repository.
 *
 * @author [오지훈]
 * @since 2020. 6. 23. 오전 11:51:57
 * @implNote Auth(권한) Repository interface 작성
 */
@Repository
public class AuthRepositoryImpl extends QuerydslRepositorySupport implements AuthRepositoryCustom {

    /**
     * Instantiates a new Auth repository.
     *
     * @author [오지훈]
     * @since 2020. 6. 23. 오전 11:51:57
     * @implNote 생성자 주입
     */
    public AuthRepositoryImpl() {
        super(Auth.class);
    }

    /**
     * Find by config list.
     *
     * @param userAuthSearchDTO the user auth search dto
     * @return the list
     * @author [오지훈]
     * @implNote 컨텐츠 권한 목록
     * @since 2020. 7. 20. 오후 4:27:00
     */
    @Override
    public List<AuthReturnDTO> findByConfig(final UserAuthSearchDTO userAuthSearchDTO) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QAuth auth = new QAuth("auth");
        final QAuth upperAuth = new QAuth("upperAuth");
        final QAuthMenuRole authMenuRole = new QAuthMenuRole("authMenuRole");
        final QMenuRole menuRole = new QMenuRole("menuRole");
        final QMenu menu = new QMenu("menu");
        final QUserContents userContents = new QUserContents("userContents");

        return queryFactory
                .select(Projections.bean(
                        AuthReturnDTO.class
                        , auth.authSeq
                        , auth.authName
                        , auth.useYn
                        , upperAuth.authSeq.as("upperAuthSeq")
                        , upperAuth.authName.as("upperAuthName")
                        , userContents.detailAuthYn
                        , userContents.emailReceptionYn
                ))
                .from(auth)
                .leftJoin(upperAuth).on(auth.upperAuthSeq.eq(upperAuth.authSeq))
                .innerJoin(authMenuRole).on(auth.authSeq.eq(authMenuRole.authSeq))
                .innerJoin(menuRole).on(
                        authMenuRole.menuRoleSeq.eq(menuRole.menuRoleSeq)
                        , AuthPredicateHelper.eqSkillCode(userAuthSearchDTO.getSkillCode())
                )
                .innerJoin(menu).on(
                        menuRole.menuSeq.eq(menu.menuSeq)
                        , AuthPredicateHelper.eqMenuCode(userAuthSearchDTO.getMenuCode())
                )
                .leftJoin(userContents).on(auth.authSeq.eq(userContents.authSeq).and(userContents.contentsSeq.eq(userAuthSearchDTO.getContentsSeq())))
                .where(auth.useYn.eq(ServiceCode.YesOrNoEnumCode.Y.toString()))
                .fetch();
    }

    /**
     * Find by auth seq array list.
     *
     * @param authSeqArray the auth seq array
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 20. 오후 2:50:03
     * @implNote 컨텐츠 권한 목록
     */
    @Override
    public List<AuthReturnDTO> findByAuthSeqArray(final Long... authSeqArray) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QAuth auth = new QAuth("auth");
        final QAuth upperAuth = new QAuth("upperAuth");

        return queryFactory
                .select(Projections.bean(
                        AuthReturnDTO.class
                        , auth.authSeq
                        , auth.authName
                        , upperAuth.authSeq.as("upperAuthSeq")
                        , upperAuth.authName.as("upperAuthName")
                    ))
                .from(auth)
                .leftJoin(upperAuth).on(auth.upperAuthSeq.eq(upperAuth.authSeq))
                .where(
                        auth.authSeq.in(authSeqArray)
                        , auth.useYn.eq(ServiceCode.YesOrNoEnumCode.Y.toString())
                )
                .fetch();
    }

    /**
     * Find by auth depth list.
     *
     * @param authSeq   the auth seq
     * @param authDepth the auth depth
     * @param menuCode  the menu code
     * @param skillCode the skill code
     * @return the list
     * @author [오지훈]
     * @since 2020. 7. 21. 오후 5:04:25
     * @implNote
     */
    @Override
    public List<AuthReturnDTO> findByAuthDepth(
            final Long authSeq
            , final Long authDepth
            , final String menuCode
            , final String skillCode) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QAuth auth = new QAuth("auth");
        final QAuth upperAuth = new QAuth("upperAuth");
        final QAuthMenuRole authMenuRole = new QAuthMenuRole("authMenuRole");
        final QMenuRole menuRole = new QMenuRole("menuRole");
        final QMenu menu = new QMenu("menu");
        final QUserContents userContents = new QUserContents("userContents");

        return queryFactory
                .select(Projections.bean(
                        AuthReturnDTO.class
                        , auth.authSeq
                        , auth.authName
                        , upperAuth.authSeq.as("upperAuthSeq")
                        , upperAuth.authName.as("upperAuthName")
                        , userContents.detailAuthYn
                        , userContents.emailReceptionYn
                ))
                .from(auth)
                .leftJoin(upperAuth).on(auth.upperAuthSeq.eq(upperAuth.authSeq))
                .innerJoin(authMenuRole).on(auth.authSeq.eq(authMenuRole.authSeq))
                .innerJoin(menuRole).on(
                        authMenuRole.menuRoleSeq.eq(menuRole.menuRoleSeq)
                        .and(menuRole.menuSkillCode.eq(skillCode))
                )
                .innerJoin(menu).on(
                        menuRole.menuSeq.eq(menu.menuSeq)
                        .and(menu.menuCode.eq(menuCode))
                )
                .leftJoin(userContents).on(auth.authSeq.eq(userContents.authSeq))
                .where(
                        AuthPredicateHelper.compareDepth(authSeq, authDepth)
                        , auth.useYn.eq(ServiceCode.YesOrNoEnumCode.Y.toString())
                )
                .fetch();
    }
}
