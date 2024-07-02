package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repository.RoleJpaRepository;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleJpaRepository roleJpaRepository;

    @Autowired
    public RoleServiceImpl(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleJpaRepository.findAll();
    }

}