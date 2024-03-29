package com.nike.dnp.repository.contents;

import com.nike.dnp.dto.contents.ContentsFileResultDTO;
import com.nike.dnp.dto.contents.ContentsFileSearchDTO;
import com.nike.dnp.entity.contents.ContentsFile;
import com.nike.dnp.entity.contents.QContentsFile;
import com.nike.dnp.entity.log.QDownloadLog;
import com.nike.dnp.util.ObjectMapperUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.nike.dnp.entity.log.QDownloadLog.downloadLog;


/**
 * The Class Contents file repository.
 *
 * @author [이소정]
 * @implNote 콘텐츠 파일 repository
 * @since 2020. 7. 13. 오후 5:57:36
 */
@Repository
public class ContentsFileRepositoryImpl extends QuerydslRepositorySupport implements ContentsFileRepositoryCustom {


    /**
     * 생성자 주입
     * Instantiates a new Contents repository.
     *
     * @author [이소정]
     * @implNote 생서자 주입
     * @since 2020. 6. 19. 오후 6:15:29
     */
    public ContentsFileRepositoryImpl() {
        super(ContentsFile.class);
    }


    /**
     * Find all contents file list.
     *
     * @param contentsFileSearchDTO 콘텐츠 파일 조회 DTO
     * @param pageRequest           the page request
     * @return the list
     * @author [이소정]
     * @implNote 콘텐츠 파일 페이징 처리 후 조회
     * @since 2020. 7. 13. 오후 6:16:50
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

        /*
         * 2021-10-07
         * 작업자: 최미영, 류성재
         * 다운로드 수가 전부 0 으로 나오는 증상으로 수정
         * 이후 퍼포먼스에 이슈가 적은 방법으로 수정해야함.
         */
        List<ContentsFileResultDTO> dataList = getQuerydsl()
                .applyPagination(pageRequest, query)
                .fetch()
                .stream()
                .map(entity -> {
                    Long contentsFileSeq = entity.getContentsFileSeq();
                    long downloadCount = queryFactory
                            .from(downloadLog)
                            .where(
                                    downloadLog.fileSeq.eq(contentsFileSeq),
                                    downloadLog.downloadType.eq("CONTENTS")
                            ).fetchCount();
                    ContentsFileResultDTO data = ObjectMapperUtil.map(entity, ContentsFileResultDTO.class);

                    data.setDownloadCount(downloadCount);

                    return data;
                })
                .collect(Collectors.toList());


        /*
         * 2021-10-07
         * 작업자: 최미영, 류성재
         * 다운로드 수가 전부 0 으로 나오는 증상으로 수정
         * 이후 퍼포먼스에 이슈가 적은 방법으로 수정해야함.
         */
        //final List<ContentsFileResultDTO> contentsFileResultDTOList = ObjectMapperUtil.mapAll(getQuerydsl().applyPagination(pageRequest, query).fetch(), ContentsFileResultDTO.class);
        return new PageImpl<>(dataList, pageRequest, query.fetchCount());
    }
}

