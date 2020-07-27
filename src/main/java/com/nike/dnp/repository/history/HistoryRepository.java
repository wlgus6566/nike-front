package com.nike.dnp.repository.history;

import com.nike.dnp.dto.history.HistoryResultDTO;
import com.nike.dnp.entity.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, HistoryRepositoryCustom {


//    Optional<History> findAllPage(Long contentsSeq, String topMenuCode, String menuCode, String useYn);

    /**
     * Find paging page.
     *
     * @param registerSeq the register seq
     * @param page        the page
     * @param size        the size
     * @return the page
     * @author [이소정]
     * @CreatedOn 2020. 7. 23. 오후 12:16:21
     * @Description
     */
//    @Query(value = "SELECT * FROM (" +
//            "    SELECT CON.CONTENTS_SEQ AS FOLER_SEQ, CON.FOLDER_NAME AS FOLDER_NAME, CON.TOP_MENU_CODE AS TYPE_CD, CON.TOP_MENU_CODE, CON.MENU_CODE, CON.IMAGE_FILE_NAME" +
//            "         , CON.IMAGE_FILE_SIZE , CON.IMAGE_FILE_PHYSICAL_NAME, CON.FOLDER_CONTENTS" +
//            "         , CON.CAMPAIGN_PERIOD_SECTION_CODE, CON.CAMPAIGN_BEGIN_DT, CON.CAMPAIGN_END_DT" +
//            "         , CON.READ_COUNT, CON.REGISTRATION_DT" +
//            "    FROM TB_CONTENTS CON" +
//            "    WHERE CON.REGISTER_SEQ = 1" +
//            "    UNION" +
//            "    SELECT RE.REPORT_SEQ AS FOLER_SEQ, RE.REPORT_NAME AS FOLDER_NAME,  'REPORT' AS TYPE_CD, 'REPORT' AS TOP_MENU_CODE, NULL AS MENU_CODE, RE.IMAGE_FILE_NAME" +
//            "        , RE.IMAGE_FILE_SIZE, RE.IMAGE_FILE_PHYSICAL_NAME, NULL AS FOLDER_CONTENTS" +
//            "        , NULL AS CAMPAIGN_PERIOD_SECTION_CODE, NULL AS CAMPAIGN_BEGIN_DT, NULL AS CAMPAIGN_END_DT" +
//            "        , RE.READ_COUNT, RE.REGISTRATION_DT" +
//            "    FROM TB_REPORT RE" +
//            "    WHERE RE.REGISTER_SEQ = 1 ORDER BY REGISTRATION_DT DESC" +
//            ") UPLOAD LIMIT 0, 20", nativeQuery = true)

//    TODO[lsj] 네이티브쿼리 -> DTO 확인필요
    @Query(value = "SELECT * FROM (" +
            "    SELECT CON.CONTENTS_SEQ AS CONTENTS_SEQ, CON.FOLDER_NAME AS FOLDER_NAME, CON.REGISTRATION_DT as REGISTRATION_DT" +
            "    FROM TB_CONTENTS CON" +
            "    WHERE CON.REGISTER_SEQ = 1" +
            "    UNION" +
            "    SELECT RE.REPORT_SEQ AS CONTENTS_SEQ, RE.REPORT_NAME AS FOLDER_NAME, RE.REGISTRATION_DT as REGISTRATION_DT" +
            "    FROM TB_REPORT RE" +
            "    WHERE RE.REGISTER_SEQ = 1" +
            ") UPLOAD ORDER BY REGISTRATION_DT DESC LIMIT 0, 20", nativeQuery = true)
    List<HistoryResultDTO> findAllUploadFilePaging(final Long registerSeq, final int page, final int size);

}
