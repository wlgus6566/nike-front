package com.nike.dnp.repository.menu;

import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.menu.MenuReturnDTO;
import com.nike.dnp.entity.auth.QAuthMenuRole;
import com.nike.dnp.entity.menu.Menu;
import com.nike.dnp.entity.menu.QMenu;
import com.nike.dnp.entity.menu.QMenuRole;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MenuRepositoryImpl
 *
 * @author [오지훈]
 * @implNote Menu(메뉴) Repository interface 작성
 * @since 2020. 6. 23. 오전 11:51:57
 */
@Repository
public class MenuRepositoryImpl extends QuerydslRepositorySupport implements MenuRepositoryCustom {

    /**
     * Instantiates a new Manager repository.
     *
     * @author [오지훈]
     * @implNote 생성자 주입
     * @since 2020. 6. 23. 오전 11:51:57
     */
    public MenuRepositoryImpl() {
        super(Menu.class);
    }

    /**
     * Gets menus.
     *
     * @param authSeq the auth seq
     * @return the menus
     * @author [오지훈]
     * @since 2020. 7. 8. 오후 6:19:23
     * @implNote 메뉴 목록(권한)
     */
    @Override
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
                                , qMenu.pcYn
                                , qMenu.mobileYn
                                , qUpperMenu.menuSeq.as("upperMenuSeq")
                                , qUpperMenu.menuName.as("upperMenuName")
                        )
                )
                .from(qMenu)
                .innerJoin(qUpperMenu).on(qMenu.upperMenuSeq.eq(qUpperMenu.menuSeq))
                .innerJoin(qMenuRole).on(
                        qMenu.menuSeq.eq(qMenuRole.menuSeq)
                        , qMenuRole.menuSkillCode.eq(ServiceCode.MenuSkillEnumCode.LIST.toString())
                ).fetchJoin()
                .innerJoin(qAuthMenuRole).on(
                        qMenuRole.menuRoleSeq.eq(qAuthMenuRole.menuRoleSeq)
                        , qAuthMenuRole.authSeq.eq(authSeq)
                ).fetchJoin()
                .where(qMenu.useYn.eq(ServiceCode.YesOrNoEnumCode.Y.toString()))
                .orderBy(
                        qUpperMenu.menuDepth.asc()
                        , qUpperMenu.menuOrder.asc()
                        , qMenu.menuOrder.asc())
                .groupBy(qMenu.menuSeq)
                .fetch();
    }

    /**
     * Gets menus.
     *
     * @param authSeq the auth seq
     * @return the menus
     * @author [오지훈]
     * @since 2020. 7. 8. 오후 6:18:06
     * @implNote 상위 메뉴 목록(GNB)
     */
    @Override
    public  List<MenuReturnDTO> getUpperMenus(final Long authSeq) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QMenu qMenu = new QMenu("menu");
        return queryFactory
                .select(
                        Projections.bean(
                                MenuReturnDTO.class
                                , qMenu.menuSeq
                                , qMenu.menuName
                                , qMenu.menuCode
                                , qMenu.menuPathUrl
                                , qMenu.pcYn
                                , qMenu.mobileYn
                                , qMenu.managementYn
                        )
                )
                .from(qMenu)
                .where(
                        qMenu.useYn.eq(ServiceCode.YesOrNoEnumCode.Y.toString())
                        , qMenu.menuDepth.eq(1L)
                )
                .orderBy(qMenu.menuOrder.asc())
                .fetch();
    }

    /**
     * Gets lower menus.
     *
     * @param authSeq   the auth seq
     * @param menuSeq   the menu seq
     * @param menuDepth the menu depth
     * @return the lower menus
     * @author [오지훈]
     * @implNote 권한 관리 중인 하위 메뉴 목록(GNB)
     * @since 2020. 8. 10. 오후 5:33:21
     */
    @Override
    public List<MenuReturnDTO> getLowerMenus(Long authSeq, Long menuSeq, Long menuDepth) {
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
                                , qMenu.menuCode
                                , qMenu.menuPathUrl
                                , qMenu.pcYn
                                , qMenu.mobileYn
                                , qMenu.managementYn
                                , qUpperMenu.menuSeq.as("upperMenuSeq")
                                , qUpperMenu.menuName.as("upperMenuName")
                        )
                )
                .from(qMenu)
                .innerJoin(qUpperMenu).on(
                        qMenu.upperMenuSeq.eq(qUpperMenu.menuSeq)
                        , qUpperMenu.menuSeq.eq(menuSeq)
                )
                .innerJoin(qMenuRole).on(
                        qMenu.menuSeq.eq(qMenuRole.menuSeq)
                        , qMenuRole.menuSkillCode.eq(ServiceCode.MenuSkillEnumCode.LIST.toString())
                ).fetchJoin()
                .innerJoin(qAuthMenuRole).on(
                        qMenuRole.menuRoleSeq.eq(qAuthMenuRole.menuRoleSeq)
                        , qAuthMenuRole.authSeq.eq(authSeq)
                ).fetchJoin()
                .where(
                        qMenu.useYn.eq(ServiceCode.YesOrNoEnumCode.Y.toString())
                        , qMenu.menuDepth.eq(menuDepth)
                )
                .orderBy(qMenu.menuOrder.asc())
                .groupBy(qMenu.menuSeq)
                .fetch();
    }

    /**
     * Gets sub menus.
     *
     * @param menuSeq   the menu seq
     * @param menuDepth the menu depth
     * @return the sub menus
     * @author [오지훈]
     * @implNote 권한 관리 하지않는 하위 메뉴 목록(GNB)
     * @since 2020. 8. 10. 오후 5:33:22
     */
    @Override
    public List<MenuReturnDTO> getSubMenus(Long menuSeq, Long menuDepth) {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QMenu qMenu = new QMenu("menu");
        final QMenu qUpperMenu = new QMenu("upperMenu");
        return queryFactory
                .select(
                        Projections.bean(
                                MenuReturnDTO.class
                                , qMenu.menuSeq
                                , qMenu.menuName
                                , qMenu.menuCode
                                , qMenu.menuPathUrl
                                , qMenu.pcYn
                                , qMenu.mobileYn
                                , qMenu.managementYn
                                , qUpperMenu.menuSeq.as("upperMenuSeq")
                                , qUpperMenu.menuName.as("upperMenuName")
                        )
                )
                .from(qMenu)
                .innerJoin(qUpperMenu).on(
                        qMenu.upperMenuSeq.eq(qUpperMenu.menuSeq)
                        , qUpperMenu.menuSeq.eq(menuSeq)
                )
                .where(
                        qMenu.useYn.eq(ServiceCode.YesOrNoEnumCode.Y.toString())
                        , qMenu.menuDepth.eq(menuDepth)
                )
                .orderBy(qMenu.menuOrder.asc())
                .fetch();
    }
}
