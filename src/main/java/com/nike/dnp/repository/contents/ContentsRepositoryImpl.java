package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.QContents;
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
 * @author [이소정]
 * @CreatedOn 2020. 6. 19. 오후 5:54:32
 * @Description Contents RepositoryImpl 작성
 */
@Repository
public class ContentsRepositoryImpl extends QuerydslRepositorySupport implements ContentsRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 6:15:29
     * @Description
     */
    public ContentsRepositoryImpl() {
        super(Contents.class);
    }

    /**
     * Find alls page.
     *
     * @param contentsSearchDTO the contents search dto
     * @param pageRequest       the page request
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:54:39
     * @Description
     */
    @Override
    public Page<Contents> findAlls(final ContentsSearchDTO contentsSearchDTO, final PageRequest pageRequest) {
        final QContents qContents = QContents.contents;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final String keyword = contentsSearchDTO.getKeyword();

        final List<Contents> contents = queryFactory.selectFrom(qContents)
                .where(eqFolderName(keyword)
                        , eqTopMenuCode(contentsSearchDTO.getTopMenuCode())
                        , eqMenuCode(contentsSearchDTO.getMenuCode()))
                .fetch();

        return new PageImpl<>(contents, pageRequest, contents.size());
    }


    /**
     * 폴더명 eq
     *
     * @param keyword the keyword
     * @return boolean expression
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:55:19
     * @Description
     */
    private BooleanExpression eqFolderName(final String keyword) {
        return keyword.isEmpty() ? null : QContents.contents.folderName.contains(keyword);
    }

    /**
     * 최고 메뉴 공통코드 eq
     *
     * @param topMenuCode the top menu code
     * @return boolean expression
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:55:19
     * @Description
     */
    private BooleanExpression eqTopMenuCode(final String topMenuCode) {
        return topMenuCode.isEmpty() ? null : QContents.contents.topMenuCode.eq(topMenuCode);
    }

    /**
     * 메뉴 공통코드 eq
     *
     * @param menuCode the menu code
     * @return boolean expression
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 5:55:19
     * @Description
     */
    private BooleanExpression eqMenuCode(final String menuCode) {
        return menuCode.isEmpty() ? null : QContents.contents.menuCode.eq(menuCode);
    }

}
