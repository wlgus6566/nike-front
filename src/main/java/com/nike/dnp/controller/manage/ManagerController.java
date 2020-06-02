package com.nike.dnp.controller.manage;

import com.nike.dnp.dto.manage.manager.ManagerSaveDTO;
import com.nike.dnp.dto.manage.manager.ManagerSearchDTO;
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

import java.util.HashMap;
import java.util.Optional;

/**
 * ManagerController
 *
 * @since 2020.05.27
 * @author [오지훈]
 * @Description 사용자 컨트롤러 작성
 * @history [오지훈] [2020.05.27] [최초 작성]
 *
 */

@Slf4j
@RestController
@Api(description = "관리 정보", tags = "1_MANAGE")
@RequestMapping(value = "/manage/manager", name = "사용자")
public class ManagerController {

    /**
     * 사용자 서비스
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
     * 사용자 전체목록 조회
     *
     * @param pageable         the pageable
     * @param managerSearchDTO the manager search dto
     * @return all managers
     */
    @ApiOperation(
        value = "사용자 목록 조회"
        , notes =
        "## Reqeust ## \n"
        + "필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "keyword|검색어|false|String\n"
        + "page|페이지|false|Integer\n"
        + "size|노출갯수|false|Integer\n"
        + "\n\n\n"
        + "## Response ## \n"
        + "1depth|2depth|3depth|필드설명|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "content|||본문내용|\n"
            + "|registrationDt||최초등록일|DateTime\n"
            + "|updateDt||최종수정일|DateTime\n"
            + "|registerSeq||최초작성자|Long\n"
            + "|updaterSeq||최종수정자|Long\n"
            + "|managerSeq||사용자시퀀스|Long\n"
            + "|managerId||사용자ID|String\n"
            + "|managerName||사용자명|String\n"
            + "|useIp||접속IP|String\n"
            + "|loginDt||로그인일자|DateTime\n"
            + "|managerAuth||권한정보|\n"
                + "||registrationDt|최초등록일|DateTime\n"
                + "||updateDt|최종수정일|DateTime\n"
                + "||registerSeq|최초작성자|Long\n"
                + "||updaterSeq|최종수정자|Long\n"
                + "||authSeq|권한시퀀스|Long\n"
                + "||authName|권한명|String\n"
                + "||roleType|권한타입|String\n"
                + "||useYn|사용여부|String\n"
        + "totalPages|||총페이지수|Integer\n"
        + "totalElements|||총데이터수|Integer\n"
        + "first|||첫페이지여부|Boolean\n"
        + "last|||마지막페이지여부|Boolean\n"
        + "empty|||빈값여부|Boolean\n"
        + "number|||현재페이지|Integer\n"
        + "size|||노출갯수|Integer\n"
        + "\n\n\n"
        , response = Page.class
    )
    @ApiImplicitParams({
        @ApiImplicitParam(
                name = "keyword"
                , value = "master"
                , required = false
                , dataType = "String"
                , paramType = "query"
        )
        ,@ApiImplicitParam(
                name = "page"
                , value = "0"
                , required = false
                , dataType = "int"
                , paramType = "query"
        )
        ,@ApiImplicitParam(
                name = "size"
                , value = "20"
                , required = false
                , dataType = "int"
                , paramType = "query"
        )
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE},name = "사용자 목록 조회")
    public Page<Manager> getAllManagers(final Pageable pageable, ManagerSearchDTO managerSearchDTO) {
        return managerService.findAllPaging(pageable, managerSearchDTO);
    }

    /**
     * 사용자 상세정보 조회
     *
     * @param managerSeq       the manager seq
     * @param managerSearchDTO the manager search dto
     * @return the manager
     */
    @ApiOperation(
        value = "사용자 상세 조회"
        /*, notes =
        "## Reqeust ## \n"
        + "필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "managerSeq|사용자시퀀스|true|Long\n"
        + "\n\n\n"
        + "## Response ## \n"
        + "1depth|2depth|3depth|필드설명|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "result|||본문내용|\n"
        + "|registrationDt||최초등록일|DateTime\n"
        + "|updateDt||최종수정일|DateTime\n"
        + "|registerSeq||최초작성자|Long\n"
        + "|updaterSeq||최종수정자|Long\n"
        + "|managerSeq||사용자시퀀스|Long\n"
        + "|managerId||사용자ID|String\n"
        + "|managerName||사용자명|String\n"
        + "|useIp||접속IP|String\n"
        + "|loginDt||로그인일자|DateTime\n"
        + "|managerAuth||권한정보|\n"
            + "||registrationDt|최초등록일|DateTime\n"
            + "||updateDt|최종수정일|DateTime\n"
            + "||registerSeq|최초작성자|Long\n"
            + "||updaterSeq|최종수정자|Long\n"
            + "||authSeq|권한시퀀스|Long\n"
            + "||authName|권한명|String\n"
            + "||roleType|권한타입|String\n"
            + "||useYn|사용여부|String\n"
        + "search|||검색정보|\n"
        + "|keyword||검색어|String\n"
        + "|page||현재페이지|Integer\n"
        + "code|||응답코드|String\n"
        + "\n\n\n"*/
    )
    @GetMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE}, name = "사용자 상세 조회")
    public ResponseEntity<Manager> getManager(@PathVariable(name = "managerSeq", required = true) Long managerSeq) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Manager> manager = managerService.findById(managerSeq);

        if (manager.isPresent()) {
            result.put("code", HttpStatus.OK);
            result.put("result", manager.get());
            return new ResponseEntity(result, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 사용자 삭제
     *
     * @param managerSeq the manager seq
     * @return the response entity
     */
    @ApiOperation(
        value = "사용자 삭제"
        , notes =
        "## Reqeust ## \n"
        + "필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "managerSeq|사용자시퀀스|true|Long\n"
        + "\n\n\n"
    )
    @DeleteMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE}, name = "사용자 삭제")
    public ResponseEntity<Void> deleteManager(@PathVariable(name = "managerSeq", required = true) Long managerSeq) {
        managerService.delete(managerSeq);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 사용자 수정
     *
     * @param managerSeq the manager seq
     * @param managerSaveDTO the manager dto
     * @return the response entity
     */
    @ApiOperation(
        value = "사용자 수정"
        , notes =
        "## Reqeust ## \n"
        + "필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "managerSeq|사용자시퀀스|true|Long\n"
        + "\n\n\n"
    )
    @PutMapping(value = "/{managerSeq}", produces = {MediaType.APPLICATION_JSON_VALUE}, name = "사용자 수정")
    public ResponseEntity<Manager> updateManager(@PathVariable(name = "managerSeq", required = true) Long managerSeq, ManagerSaveDTO managerSaveDTO) {
        managerService.update(managerSeq, managerSaveDTO);
        return new ResponseEntity(managerSeq, HttpStatus.OK);
    }

    /**
     * 사용자 등록
     *
     * @param managerSaveDTO the manager dto
     * @return the response entity
     */
    @ApiOperation(
        value = "사용자 등록"
        , notes =
        "## Reqeust ## \n"
        + "필드명|설명|필수여부|데이터 타입(길이)\n" + "-|-|-|-\n"
        + "managerSeq|사용자시퀀스|true|Long\n"
        + "\n\n\n"
    )
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, name = "사용자 등록")
    public ResponseEntity<Manager> insertManager(ManagerSaveDTO managerSaveDTO) {
        return new ResponseEntity(managerService.save(managerSaveDTO), HttpStatus.OK);
    }

}
