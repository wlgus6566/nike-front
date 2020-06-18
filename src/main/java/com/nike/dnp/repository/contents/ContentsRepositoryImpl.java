package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.QContents;
import com.nike.dnp.entity.example.Manager;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contents Repository Impl
 *
 * @since 2020.06.11
 * @author [이소정]
 * @Description Contents RepositoryImpl 작성
 * @history [이소정] [2020.06.11] [최초 작성]
 *
 */
@Repository
public class ContentsRepositoryImpl extends QuerydslRepositorySupport implements ContentsRepositoryCustom {

    /**
     * 생성자 주입
     * Instantiates a new Manager repository.
     */
    public ContentsRepositoryImpl() {
        super(Manager.class);
    }

    @Override
    public Page<Contents> findAlls(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest) {
        final QContents qContents = QContents.contents;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final String keyword = contentsSearchDTO.getKeyword();

        final List<Contents> contents = queryFactory.selectFrom(qContents)
                .where(eqFolderName(keyword)
                        , eqTopMenuSeq(contentsSearchDTO.getTopMenuSeq())
                        , eqMenuSeq(contentsSearchDTO.getMenuSeq()))
                .fetch();

        return new PageImpl<>(contents, pageRequest, contents.size());
    }


    /**
     * 폴더명 eq
     * @param keyword
     * @return
     */
    private BooleanExpression eqFolderName(final String keyword) {
        return keyword.isEmpty() ? null : QContents.contents.folderName.contains(keyword);
    }

    /**
     * 최고 메뉴 시퀀스 eq
     * @param topMenuSeq
     * @return
     */
    private BooleanExpression eqTopMenuSeq(final Long topMenuSeq) {
        return null == topMenuSeq || 0 == topMenuSeq ? null : QContents.contents.topMenuSeq.eq(topMenuSeq);
    }

    /**
     * 메뉴 시퀀스 eq
     * @param menuSeq
     * @return
     */
    private BooleanExpression eqMenuSeq(final Long menuSeq) {
        return null == menuSeq || 0 == menuSeq ? null : QContents.contents.menuSeq.eq(menuSeq);
    }

}
