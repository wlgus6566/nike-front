package com.nike.dnp.repository.menu;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.entity.menu.QMenu;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;

/**
 * CodePredicateHelper
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 24. 오후 5:51:00
 * @Description Code(공통코드) Predicate Helper 작성
 */
@UtilityClass
public class MenuPredicateHelper {

    public static Predicate eqBasicConfig() {
        final BooleanBuilder builder = new BooleanBuilder();
        builder.and(QMenu.menu.useYn.eq(ServiceEnumCode.yesOrNoEnumCode.Y.toString()));
        builder.and(QMenu.menu.upperMenuSeq.isNull());
        return builder;
    }

}
