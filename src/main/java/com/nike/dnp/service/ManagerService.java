package com.nike.dnp.service;

import com.nike.dnp.dto.ManagerDTO;
import com.nike.dnp.entity.Manager;
import com.nike.dnp.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public List<Manager> findAll() {
        List<Manager> managers = new ArrayList<>();
        managerRepository.findAll().forEach(e -> managers.add(e));
        return managers;
    }

    public Optional<ManagerDTO> findById(long managerSeq) {
        Optional<ManagerDTO> manager = managerRepository.findById(managerSeq);
        return manager;
    }

    public void deleteById(long managerSeq) {
        managerRepository.deleteById(managerSeq);
    }

    public ManagerDTO save(ManagerDTO managerDTO) {
        managerRepository.save(managerDTO);
        return managerDTO;
    }

    public void updateById(long managerSeq, ManagerDTO managerDTO) {
        Optional<ManagerDTO> e = managerRepository.findById(managerSeq);

        if (e.isPresent()) {
            e.get().builder().managerId(managerDTO.getManagerId()).password(managerDTO.getPassword()).build();
            managerRepository.save(managerDTO);
        }
    }

}
