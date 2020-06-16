package com.nike.dnp.repository.code;

import com.nike.dnp.dto.code.CodeSearchDTO;
import com.nike.dnp.entity.code.Code;
import com.nike.dnp.entity.code.QCode;
import com.querydsl.jpa.JPQLQuery;
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
 * @Description Code(공통 코드) Repository interface 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Repository
public class CodeRepositoryImpl extends QuerydslRepositorySupport implements CodeRepositoryCustom {

    /**
     * 생성자 주입
     * Instantiates a new Manager repository.
     */
    public CodeRepositoryImpl() {
        super(Code.class);
    }

    /**
     * Find alls page.
     *
     * @param codeSearchDTO the code search dto
     * @param pageRequest   the page request
     * @return the page
     */
    @Override
    public Page<Code> findAlls(CodeSearchDTO codeSearchDTO, PageRequest pageRequest) {
        final QCode qCode = QCode.code1;
        final JPQLQuery<Code> query = from(qCode)
                .where(CodePredicateHelper.search(codeSearchDTO))
                .fetchAll();
        final List<Code> codes = getQuerydsl().applyPagination(pageRequest, query).fetch();
        return new PageImpl<>(codes, pageRequest, query.fetchCount());
    }

}
