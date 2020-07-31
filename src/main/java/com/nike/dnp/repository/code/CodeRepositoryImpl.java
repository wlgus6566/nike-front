package com.nike.dnp.repository.code;

import com.nike.dnp.dto.code.CodeSearchDTO;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.entity.code.QCode;
import com.querydsl.core.types.Projections;
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
 * @since 2020. 6. 23. 오전 11:51:57
 * @implNote Code(공통 코드) Repository interface 작성
 */
@Repository
public class CodeRepositoryImpl extends QuerydslRepositorySupport implements CodeRepositoryCustom {

    /**
     * Instantiates a new Manager repository.
     *
     * @author [오지훈]
     * @since 2020. 6. 23. 오전 11:51:57
     * @implNote 생성자 주입
     */
    public CodeRepositoryImpl() {
        super(Code.class);
    }

    /**
     * Find pages page.
     *
     * @param codeSearchDTO the code search dto
     * @param pageRequest   the page request
     * @return the page
     * @author [오지훈]
     * @since 2020. 6. 23. 오전 11:51:53
     * @implNote 조회(페이징)
     */
    @Override
    public Page<Code> findPages(final CodeSearchDTO codeSearchDTO, final PageRequest pageRequest) {
        final QCode qCode = QCode.code1;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<Code> jpaCodes = queryFactory
                .select(Projections.fields(Code.class, qCode.code, qCode.codeName))
                .from(qCode)
                .where(CodePredicateHelper.compareKeyword(codeSearchDTO));

        final List<Code> codes = getQuerydsl().applyPagination(pageRequest, jpaCodes).fetch();
        return new PageImpl<>(codes, pageRequest, jpaCodes.fetchCount());
    }

}
