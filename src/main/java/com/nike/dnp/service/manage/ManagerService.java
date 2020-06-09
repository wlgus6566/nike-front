package com.nike.dnp.service.manage;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.nike.dnp.dto.manage.auth.AuthUserDTO;
import com.nike.dnp.dto.manage.manager.ManagerPredicate;
import com.nike.dnp.dto.manage.manager.ManagerSaveDTO;
import com.nike.dnp.dto.manage.manager.ManagerSearchDTO;
import com.nike.dnp.dto.manage.manager.ManagerUpdateDTO;
import com.nike.dnp.entity.manage.Manager;
import com.nike.dnp.entity.manage.ManagerAuth;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.ErrorEnumCode;
import com.nike.dnp.repository.manage.ManagerAuthRepository;
import com.nike.dnp.repository.manage.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
public class ManagerService implements UserDetailsService {

    /**
     * @author [오지훈]
     * ManagerRepository
     */
    private final ManagerRepository managerRepository;

    /**
     * @author [오지훈]
     * ManagerAuthRepository
     */
    private final ManagerAuthRepository authRepository;

    /**
     * Instantiates a new Manager service.
     *
     * @param managerRepository     the manager repository
     * @param authRepository the manager auth repository
     */
    public ManagerService(
            final ManagerRepository managerRepository
            , final ManagerAuthRepository authRepository
    ) {
        this.managerRepository = managerRepository;
        this.authRepository = authRepository;
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
    public Page<Manager> findAllPaging(final ManagerSearchDTO managerSearchDTO) {
        final PageRequest pageRequest = PageRequest.of(managerSearchDTO.getPage()
                , managerSearchDTO.getSize()
                , Sort.by("managerSeq").descending());

        return managerRepository.findAll(
                ManagerPredicate.search(managerSearchDTO),
                pageRequest);
    }

    /*public List<Manager> findAllPaging3(ManagerSearchDTO managerSearchDTO) {
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
    }*/

    /**
     * 상세조회
     *
     * @param managerSeq the manager seq
     * @return the optional
     */
    public Manager findById(final Long managerSeq) {
        return managerRepository.findById(managerSeq)
                .orElseThrow(() -> new CodeMessageHandleException(ErrorEnumCode.ManageError.MANE01.toString(), ErrorEnumCode.ManageError.MANE01.getMessage()));
    }

    /**
     * 삭제
     *
     * @param managerSeq the manager seq
     */
    public void delete(final Long managerSeq) {
        managerRepository.deleteById(managerSeq);
    }

    /**
     * 등록
     *
     * @param managerSaveDTO the manager dto
     * @return the long
     */
    @Transactional
    public Manager save(final ManagerSaveDTO managerSaveDTO) {
        final Optional<ManagerAuth> managerAuth = authRepository.findById(managerSaveDTO.getAuthSeq());
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
    public Manager update(final Long managerSeq, final ManagerUpdateDTO managerUpdateDTO) {
        final Optional<Manager> manager = managerRepository.findById(managerSeq);
        if (manager.isPresent()) {
            final Optional<ManagerAuth> managerAuth = authRepository.findById(managerUpdateDTO.getAuthSeq());
            manager.get().update(
                    managerUpdateDTO.getManagerName()
                    , managerUpdateDTO.getPassword()
                    , managerAuth.get()
                    , managerUpdateDTO.getUpdaterSeq()
            );
        }
        return manager.get();
    }



    /*@PersistenceContext // 영속성 객체를 자동으로 삽입해줌
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

    }*/


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Manager manager = managerRepository.findByManagerId(username);
        if(manager == null){
            throw new UserNotFoundException(ErrorEnumCode.LoginError.LOGE01.toString());
        }
        return new AuthUserDTO(manager);
    }
}
