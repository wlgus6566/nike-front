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
 * @since 2020. 6. 23. 오전 11:51:57
 * @implNote Menu(메뉴) Repository interface 작성
 */
@Repository
public class MenuRepositoryImpl extends QuerydslRepositorySupport implements MenuRepositoryCustom {

    /**
     * Instantiates a new Manager repository.
     *
     * @author [오지훈]
     * @since 2020. 6. 23. 오전 11:51:57
     * @implNote 생성자 주입
     */
    public MenuRepositoryImpl() {
        super(Menu.class);
    }

    /**
     * Gets menus.
     *
     * @return the menus
     * @author [오지훈]
     * @since 2020. 7. 8. 오후 6:16:36
     * @implNote 메뉴 목록(전체)
     */
    /*@Override
    public List<MenuReturnDTO> getMenus() {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final QMenu qMenu = new QMenu("menu");
        final QMenu qUpperMenu = new QMenu("upperMenu");
        return queryFactory
                .select(Projections.bean(MenuReturnDTO.class
                        , qMenu.menuName
                        , qMenu.menuSeq
                        , qUpperMenu.menuSeq.as("upperMenuSeq")
                        , qUpperMenu.menuName.as("upperMenuName")
                        , qMenu.listAuthYn
                        , qMenu.creationAuthYn
                        , qMenu.deleteAuthYn
                        , qMenu.detailAuthYn
                        , qMenu.downloadAuthYn
                        , qMenu.reportAuthYn
                        ))
                .from(qMenu)
                .innerJoin(qUpperMenu).on(qMenu.upperMenuSeq.eq(qUpperMenu.menuSeq))
                .where(qMenu.useYn.eq(ServiceCode.yesOrNoEnumCode.Y.toString()))
                .fetch();
    }*/

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
                .groupBy(qMenu.menuSeq)
                .fetch();
    }

}
