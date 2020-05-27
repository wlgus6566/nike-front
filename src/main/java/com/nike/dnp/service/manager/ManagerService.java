package com.nike.dnp.service.manager;

import com.nike.dnp.dto.manager.ManagerDTO;
import com.nike.dnp.entity.manager.Manager;
import com.nike.dnp.entity.manager.ManagerAuth;
import com.nike.dnp.repository.manager.ManagerAuthRepository;
import com.nike.dnp.repository.manager.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerAuthRepository managerAuthRepository;

    public List<Manager> findAll() {
        List<Manager> managers = new ArrayList<>();
        managerRepository.findAll().forEach(e -> managers.add(e));
        return managers;
    }

    public Optional<Manager> findById(Long managerSeq) {
        Optional<Manager> manager = managerRepository.findById(managerSeq);
        return manager;
    }

    public void deleteById(Long managerSeq) {
        managerRepository.deleteById(managerSeq);
    }

    @Transactional
    public Long save(ManagerDTO managerDTO) {
        Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerDTO.getAuthSeq());

        return managerRepository.save(Manager.builder()
                .managerId(managerDTO.getManagerId())
                .managerName(managerDTO.getManagerName())
                .password(managerDTO.getPassword())
                .managerAuth(managerAuth.get())
                .build()).getManagerSeq();
    }

    @Transactional
    public void updateById(Long managerSeq, ManagerDTO managerDTO) {
        Optional<Manager> e = managerRepository.findById(managerSeq);

        if (e.isPresent()) {
            Optional<ManagerAuth> managerAuth = managerAuthRepository.findById(managerDTO.getAuthSeq());

            e.get().managerNameUpdate(
                managerDTO.getManagerName()
                , managerDTO.getPassword()
                , managerAuth.get()
            );
        }
    }

}
