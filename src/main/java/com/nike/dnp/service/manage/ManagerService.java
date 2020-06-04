package com.nike.dnp.service.manage;

import com.nike.dnp.dto.manage.manager.ManagerPredicate;
import com.nike.dnp.dto.manage.manager.ManagerSaveDTO;
import com.nike.dnp.dto.manage.manager.ManagerSearchDTO;
import com.nike.dnp.dto.manage.manager.ManagerUpdateDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.entity.manage.QManager;
import com.nike.dnp.repository.manage.ManagerAuthRepository;
import com.nike.dnp.repository.manage.ManagerRepository;
import com.nike.dnp.repository.manage.ManagerRepositorySupport;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * ManagerService
 *
 * @since 2020.05.22
 * @author [오지훈]
 * @Description 관리자(유저) 서비스 작성
 * @history [오지훈] [2020.05.22] [최초 작성]
 *
 */

@Service
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerRepositorySupport managerRepositorySupport;
    private final ManagerAuthRepository managerAuthRepository;

    /**
     * Instantiates a new Manager service.
     *
     * @param managerRepository     the manager repository
     * @param managerAuthRepository the manager auth repository
     */
    public ManagerService(
            ManagerRepository managerRepository
            , ManagerRepositorySupport managerRepositorySupport
            , ManagerAuthRepository managerAuthRepository
    ) {
        this.managerRepository = managerRepository;
        this.managerRepositorySupport = managerRepositorySupport;
        this.managerAuthRepository = managerAuthRepository;
    }

    /**
     * 전체조회(entire)
     *
     * @return the list
     */
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }
    
    /**
     * 전체조회(paging)
     *
     * @param managerSearchDTO the manager search dto
     * @return the list
     */
    public Page<Manager> findAllPaging(ManagerSearchDTO managerSearchDTO) {
        PageRequest pageRequest = PageRequest.of(managerSearchDTO.getPage()
                , managerSearchDTO.getSize()
                , Sort.by("registrationDt").descending());

        if (managerSearchDTO.getKeyword().isEmpty()) {
            return managerRepository.findAll(pageRequest);
        }
        return managerRepository.findAllByManagerIdLikeOrManagerNameLike(
                pageRequest, managerSearchDTO.getKeyword(), managerSearchDTO.getKeyword());
    }

    public Page<Manager> findAllPaging2(ManagerSearchDTO managerSearchDTO) {
        PageRequest pageRequest = PageRequest.of(managerSearchDTO.getPage()
                , managerSearchDTO.getSize()
                , Sort.by("managerSeq").descending());

        Page<Manager> result = managerRepository.findAll(
                ManagerPredicate.search(managerSearchDTO),
                pageRequest);

        return result;
    }

    public List<Manager> findAllPaging3(ManagerSearchDTO managerSearchDTO) {
        PageRequest pageRequest = PageRequest.of(managerSearchDTO.getPage()
                , managerSearchDTO.getSize()
                , Sort.by("managerSeq").descending());

        List<Manager> result = managerRepositorySupport.findAlls(managerSearchDTO);

        return result;
    }

    public List<Manager> findAllPaging4(ManagerSearchDTO managerSearchDTO) {
        PageRequest pageRequest = PageRequest.of(managerSearchDTO.getPage()
                , managerSearchDTO.getSize()
                , Sort.by("managerSeq").descending());

        List<Manager> result = managerRepositorySupport.findAlls(managerSearchDTO);

        return result;
    }

    /**
     * 상세조회
     *
     * @param managerSeq the manager seq
     * @return the optional
     */
    public Optional<Manager> findById(Long managerSeq) {
        Optional<Manager> manager = managerRepository.findById(managerSeq);
        return manager;
    }

    /**
     * 삭제
     *
     * @param managerSeq the manager seq
     */
    public void delete(Long managerSeq) {
        managerRepository.deleteById(managerSeq);
    }

    /**
     * 등록
     *
     * @param managerSaveDTO the manager dto
     * @return the long
     */
    @Transactional
    public Manager save(ManagerSaveDTO managerSaveDTO) {
        Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerSaveDTO.getAuthSeq());
        return managerRepository.save(Manager.builder()
                .managerId(managerSaveDTO.getManagerId())
                .managerName(managerSaveDTO.getManagerName())
                .password(managerSaveDTO.getPassword())
                .managerAuth(managerAuth.get())
                .registerSeq(managerSaveDTO.getRegisterSeq())
                .build());
    }

    /**
     * 수정
     *
     * @param managerSeq       the manager seq
     * @param managerUpdateDTO the manager update dto
     */
    @Transactional
    public Manager update(Long managerSeq, ManagerUpdateDTO managerUpdateDTO) {
        Optional<Manager> e = managerRepository.findById(managerSeq);
        if (e.isPresent()) {
            Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerUpdateDTO.getAuthSeq());
            e.get().update(
                    managerUpdateDTO.getManagerName()
                    , managerUpdateDTO.getPassword()
                    , managerAuth.get()
                    , managerUpdateDTO.getUpdaterSeq()
            );
        }
        return e.get();
    }



    @PersistenceContext // 영속성 객체를 자동으로 삽입해줌
    private EntityManager em;

    public void findByTestList() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QManager qManager = QManager.manager;

        long result = queryFactory
                .selectFrom(qManager)
                .where(qManager.managerName.eq("master"))
                .fetchCount();

        List<Manager> result2 = queryFactory
                .selectFrom(qManager)
                .where(qManager.managerName.eq("master"), qManager.managerId.like("master"))
                .fetch();

        Manager result3 = managerRepositorySupport.getManager(17);

    }


}
