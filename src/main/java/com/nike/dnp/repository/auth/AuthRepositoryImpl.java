package com.nike.dnp.repository.auth;

import com.nike.dnp.dto.auth.AuthReturnDTO;
import com.nike.dnp.entity.auth.Auth;
import com.nike.dnp.entity.auth.QAuth;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Class Auth repository.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오전 11:51:57
 * @Description Auth(권한) Repository interface 작성
 */
@Repository
public class AuthRepositoryImpl extends QuerydslRepositorySupport implements AuthRepositoryCustom {

    /**
     * Instantiates a new Auth repository.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오전 11:51:57
     * @Description 생성자 주입
     */
    public AuthRepositoryImpl() {
        super(Auth.class);
    }

    /**
     * Find by auth seq array list.
     *
     * @param authSeqArray the auth seq array
     * @return the list
     * @author [오지훈]
     * @CreatedOn 2020. 7. 20. 오후 2:50:03
     * @Description
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
                .where(auth.authSeq.in(authSeqArray))
                .fetch();
    }

    /*@Override
    public  List<MenuReturnDTO> getMenus(final Long authSeq) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QAuthMenuRole qAuthMenuRole = new QAuthMenuRole("authMenuRole");
        final QMenuRole qMenuRole = new QMenuRole("menuRole");
        final QMenu qMenu = new QMenu("menu");
        final QMenu qUpperMenu = new QMenu("upperMenu");
        return queryFactory
                .select(
                        Projections.bean(
                                MenuReturnDTO.class
                                , qMenu.menuSeq
                                , qMenu.menuName
                                , qMenu.menuPathUrl
                                , qUpperMenu.menuSeq.as("upperMenuSeq")
                                , qUpperMenu.menuName.as("upperMenuName")
                        )
                )
                .from(qMenu)
                .innerJoin(qUpperMenu).on(qMenu.upperMenuSeq.eq(qUpperMenu.menuSeq))
                .innerJoin(qMenuRole).on(
                        qMenu.menuSeq.eq(qMenuRole.menuSeq)
                        , qMenuRole.menuSkillCode.eq(ServiceEnumCode.MenuSkillEnumCode.LIST.toString())
                ).fetchJoin()
                .innerJoin(qAuthMenuRole).on(
                        qMenuRole.menuRoleSeq.eq(qAuthMenuRole.menuRoleSeq)
                        , qAuthMenuRole.authSeq.eq(authSeq)
                ).fetchJoin()
                .where(qMenu.useYn.eq(ServiceEnumCode.YesOrNoEnumCode.Y.toString()))
                .groupBy(qMenu.menuSeq)
                .fetch();
    }*/

}
