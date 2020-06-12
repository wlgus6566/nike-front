package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.dto.example.manager.ManagerSearchDTO;
import com.nike.dnp.entity.contents.QContents;
import com.nike.dnp.entity.example.QManager;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.experimental.UtilityClass;

/**
 * Contents Predicate
 *
 * @since 2020.06.11
 * @author [이소정]
 * @Description Contents Predicate 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 *
 */
@UtilityClass
public class ContentsPredicateHelper {

//    /**
//     * Search predicate.
//     *
//     * @param contentsSearchDTO the contents search dto
//     * @return the predicate
//     */
//    public static Predicate search(final ContentsSearchDTO contentsSearchDTO) {
//        final BooleanBuilder builder = new BooleanBuilder();
//        final String keyword = contentsSearchDTO.getKeyword();
//
//        builder.and(eqFolderName(keyword), eqTopMenuSeq(contentsSearchDTO.getTopMenuSeq()))
//
//        return builder;
//    }
//
//    /**
//     * 폴더명 eq
//     * @param keyword
//     * @return
//     */
//    public static BooleanExpression eqFolderName(String keyword) {
//        return !keyword.isEmpty() ? QContents.contents.folderName.eq(keyword): null;
//    }
//
//    /**
//     * 최고 메뉴 시퀀스 eq
//     * @param topMenuSeq
//     * @return
//     */
//    public static BooleanExpression eqTopMenuSeq(Long topMenuSeq) {
//        return null != topMenuSeq && 0 != topMenuSeq ? QContents.contents.topMenuSeq.eq(topMenuSeq): null;
//    }
//
//    /**
//     * 메뉴 시퀀스 eq
//     * @param menuSeq
//     * @return
//     */
//    public static BooleanExpression eqMenuSeq(Long menuSeq) {
//        return null != menuSeq && 0 != menuSeq ? QContents.contents.menuSeq.eq(menuSeq): null;
//    }

}
