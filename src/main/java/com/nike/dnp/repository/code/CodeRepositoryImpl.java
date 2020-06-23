package com.nike.dnp.repository.code;

import com.nike.dnp.dto.code.CodeSearchDTO;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.entity.code.QCode;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CodeRepositoryImpl
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 23. 오전 11:51:57
 * @Description Code(공통 코드) Repository interface 작성
 */
@Repository
public class CodeRepositoryImpl extends QuerydslRepositorySupport implements CodeRepositoryCustom {

    /**
     * 생성자 주입
     * Instantiates a new Manager repository.
     *
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오전 11:51:57
     * @Description
     */
    public CodeRepositoryImpl() {
        super(Code.class);
    }

    /**
     * 조회(페이징)
     *
     * @param codeSearchDTO the code search dto
     * @param pageRequest   the page request
     * @return the page
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오전 11:51:53
     * @Description
     */
    @Override
    public Page<Code> findPages(final CodeSearchDTO codeSearchDTO, final PageRequest pageRequest) {
        final QCode qCode = QCode.code1;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final String keyword = codeSearchDTO.getKeyword();
        final JPAQuery<Code> jpqCodes = queryFactory
                .select(Projections.fields(Code.class, qCode.code, qCode.codeName))
                .from(qCode)
                .where(compareKeyword(keyword));

        final List<Code> codes = getQuerydsl().applyPagination(pageRequest, jpqCodes).fetch();
        return new PageImpl<>(codes, pageRequest, jpqCodes.fetchCount());
    }

    /**
     * 검색어 비교
     *
     * @param keyword the keyword
     * @return the boolean expression
     * @author [오지훈]
     * @CreatedOn 2020. 6. 23. 오전 11:51:58
     * @Description
     */
    public BooleanExpression compareKeyword(final String keyword) {
        return keyword.isEmpty() ? null : QCode.code1.code.eq(keyword).or(QCode.code1.codeName.contains(keyword));
    }

    /*
    public Page<Code> findPages2(CodeSearchDTO codeSearchDTO, PageRequest pageRequest) {
        final QCode qCode = QCode.code1;
        final JPQLQuery<Code> query = from(qCode)
                //.where(CodePredicateHelper.search(codeSearchDTO))
                .where(eqCode(codeSearchDTO.getKeyword()).or(containsCodeName(codeSearchDTO.getKeyword())))
                .fetchAll();
        final List<Code> codes = getQuerydsl().applyPagination(pageRequest, query).fetch();
        return new PageImpl<>(codes, pageRequest, query.fetchCount());
    }

    public Page<Code> findPages3(CodeSearchDTO codeSearchDTO, PageRequest pageRequest) {
        final QCode qCode = QCode.code1;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final String keyword = codeSearchDTO.getKeyword();

        final QueryResults<Code> codes = queryFactory
                .selectFrom(qCode)
                .where(compareKeyword(keyword))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(qCode.codeSeq.asc())
                .fetchResults();

        return new PageImpl<>(codes.getResults(), pageRequest, codes.getTotal());
    }
    */

}
