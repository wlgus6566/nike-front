package com.nike.dnp.repository.contents;

import com.nike.dnp.common.ObjectMapperUtils;
import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import com.nike.dnp.dto.contents.ContentsResultDTO;
import com.nike.dnp.dto.contents.ContentsSearchDTO;
import com.nike.dnp.entity.contents.Contents;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.entity.contents.QContents;
import com.nike.dnp.entity.contents.QContentsFile;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Class Contents file repository.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 13. 오후 5:57:36
 * @Description
 */
@Repository
public class ContentsFileRepositoryImpl extends QuerydslRepositorySupport implements ContentsFileRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @CreatedOn 2020. 6. 19. 오후 6:15:29
     * @Description
     */
    public ContentsFileRepositoryImpl() {
        super(ContentsFile.class);
    }


    /**
     * Find all contents file list.
     *
     * @param contentsFileSearchDTO the contents file search dto
     * @return the list
     * @author [이소정]
     * @CreatedOn 2020. 7. 13. 오후 6:16:50
     * @Description
     */
    @Override
    public Page<ContentsFileResultDTO> findAllContentsFilePaging(final ContentsFileSearchDTO contentsFileSearchDTO, final PageRequest pageRequest) {
        final QContentsFile qContentsFile = QContentsFile.contentsFile;
        final JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
        final JPAQuery<ContentsFile> query = queryFactory.selectFrom(qContentsFile)
                .where(
                        ContentsFilePredicateHelper.eqSectionCode(contentsFileSearchDTO)
                        , ContentsFilePredicateHelper.compareFileExtension(contentsFileSearchDTO)
                        , qContentsFile.contentsSeq.eq(contentsFileSearchDTO.getContentsSeq())
                        , qContentsFile.useYn.eq("Y")
                );
        final List<ContentsFileResultDTO> contentsFileResultDTOList = ObjectMapperUtils.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), ContentsFileResultDTO.class);
        return new PageImpl<>(contentsFileResultDTOList, pageRequest, query.fetchCount());
    }
}

