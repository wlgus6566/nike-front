package com.nike.dnp.controller.manage;

import com.nike.dnp.dto.manage.ManagerDTO;
import com.nike.dnp.dto.manage.ManagerSearchDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.repository.manage.ManagerRepository;
import com.nike.dnp.service.manage.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * ManagerController
 *
 * @since 2020.05.27
 * @author [오지훈]
 * @Description 관리자 컨트롤러 작성
 * @history [오지훈] [2020.05.27] [최초 작성]
 *
 */

@Slf4j
@RestController
@Api(description = "관리 정보", tags = "1_MANAGE")
@RequestMapping(value = "/manage/manager", name = "관리자")
public class ManagerController {

    /**
     * 관리자 서비스
     */
    private final ManagerService managerService;

    @Autowired
    private ManagerRepository managerRepository;

    /**
     * Instantiates a new Manager controller.
     *
     * @param managerService the manager service
     */
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    /**
     * 관리자 전체목록 조회
     *
     * @return all managers
     */
    @ApiOperation(
        value = "관리자 목록 조회"
        , notes =
        "## Reqeust ## \n"
        + "필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "keyword|검색어|false|String\n"
        + "page|페이지|false|Integer\n"
        + "size|노출갯수|false|Integer\n"
        + "\n## Response ## \n"
        + "상위필드|하위필드|필드설명|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "content|registrationDt|최초등록일|String\n"
        + "|updateDt|최종수정일|String\n"
        + "|managerSeq|관리자시퀀스|Long\n"
        + "|managerId|관리자ID|String\n"
        + "|managerName|관리자명|String\n"
        + "|managerName|관리자명|String\n"
        + "|loginDt|로그인일자|String\n"
        + "page|페이지||Integer\n"
        + "size|노출갯수||Integer\n"
    )
    @ApiImplicitParams({
        @ApiImplicitParam(
                name = "keyword"
                , value = "{\"keyword\": \"MASTER\"}"
                , required = false
                , dataType = "JSON String"
                , paramType = "body"
        )
        ,@ApiImplicitParam(
                name = "page"
                , value = "{\"page\": \"0\"}"
                , required = false
                , dataType = "JSON String"
                , paramType = "body"
        )
        ,@ApiImplicitParam(
                name = "size"
                , value = "{\"size\": \"20\"}"
                , required = false
                , dataType = "JSON String"
                , paramType = "body"
        )
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "관리자 목록 조회")
    public Page<Manager> getAllManagers(final Pageable pageable, ManagerSearchDTO managerSearchDTO) {
        return managerService.findAllPaging(pageable, managerSearchDTO);
    }

    /**
     * 관리자 상세정보 조회
     *
     * @param managerSeq the manager seq
     * @return the manager
     */
    @ApiOperation(value = "앱 정보 조회", notes = "## payload 파라미터 설명 ## \n" + "필드명|한글명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"

            + "device|디바이스|true|String\n" + "## 결과 데이터 설명 ##\n" + "##앱_버전 (appVersion) ##\n" + "필드명|한글명|데이터 타입(길이)\n" + "-|-|-\n"

            + "appVersionSeq|앱_버전_시퀀스|long\n" + "deviceTypeCd|디바이스_유형_코드|String\n" + "deviceTypeName|디바이스_유형_코드_이름|String\n" + "appVersion|앱_버전|String\n"
            + "updateTypeCd|업데이트_유형_코드|String\n" + "updateTypeName|업데이트_유형_코드_이름|String\n" + "updateBeginDt|업데이트_시작_일시|Date\n"
            + "updateMessage|업데이트_메시지|String\n" + "moveUrl|이동_URL|String\n" + "##리소스_버전 (resourceVersion) ##\n" + "필드명|한글명|데이터 타입(길이)\n" + "-|-|-\n"

            + "resourceVersionSeq|리소스_버전_시퀀스|long\n" + "resourceTypeCd|리소스_유형_코드|String\n" + "deviceTypeCd|디바이스_유형_코드|String\n"
            + "deviceTypeName|디바이스_유형_코드_이름|String\n" + "resourceVersion|리소스_버전|String\n" + "versionName|버전_이름|String\n" + "versionDescription|버전_설명|String\n"
            + "updateBeginDt|업데이트_시작_일시|Date\n" + "##리소스_목록 (resourceData) ##\n" + "필드명|한글명|데이터 타입(길이)\n" + "-|-|-\n" + "resourceSeq|리소스_시퀀스|long\n"
            + "targetName|대상_이름|String\n" + "permissionOsTypeCd|허용_OS_유형_코드|String\n" + "permissionOsTypeName|허용_OS_유형_코드_이름|String\n"
            + "resourceTypeCd|리소스_유형_코드|String\n" + "resourceTypeName|리소스_유형_코드_이름|String\n" + "resourceUrl|리소스_URL|String\n" + "resourceName|리소스_이름|String\n"
            + "fileSize|파일_사이즈|long\n" + "rgbColor|RGB_색상|String\n" + "brightness|밝기|FLOAT\n" + "contrast|명암|FLOAT\n" + "stillshotUrl|스틸샷_URL|String\n"
            + "downloadDt|다운로드_일시|Date\n" + "fileHashcode|파일_해시코드|String\n" + "serviceBeginYmd|서비스_시작_년월일|String\n" + "serviceEndYmd|서비스_종료_년월일|String\n"
            + "##리소스_매핑 (resourceMapping) ##\n" + "필드명|한글명|데이터 타입(길이)\n" + "-|-|-\n"

            + "productCode|제품_코드|String\n" + "slateName|슬레이트_이름|String\n")

    @ApiImplicitParams({ @ApiImplicitParam(name = "payload", value = "{\"device\": \"IOS\"}", required = true, dataType = "JSON String", paramType = "body") })
    @GetMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE}, name = "관리자 상세 조회")
    public ResponseEntity<Manager> getManager(@PathVariable("managerSeq") Long managerSeq) {
        try {
            Optional<Manager> manager = managerService.findById(managerSeq);
            return new ResponseEntity<>(manager.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 관리자 삭제
     *
     * @param managerSeq the manager seq
     * @return the response entity
     */
    @DeleteMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE}, name = "관리자 삭제")
    public ResponseEntity<Void> deleteManager(@PathVariable("managerSeq") Long managerSeq) {
        managerService.delete(managerSeq);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 관리자 수정
     *
     * @param managerSeq the manager seq
     * @param managerDTO the manager dto
     * @return the response entity
     */
    @PutMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE}, name = "관리자 수정")
    public ResponseEntity<Manager> updateManager(@PathVariable("managerSeq") Long managerSeq, ManagerDTO managerDTO) {
        managerService.update(managerSeq, managerDTO);
        return new ResponseEntity(managerSeq, HttpStatus.OK);
    }

    /**
     * 관리자 등록
     *
     * @param managerDTO the manager dto
     * @return the response entity
     */
    @PostMapping(name = "관리자 등록")
    public ResponseEntity<Manager> insertManager(ManagerDTO managerDTO) {
        return new ResponseEntity(managerService.save(managerDTO), HttpStatus.OK);
    }

}
