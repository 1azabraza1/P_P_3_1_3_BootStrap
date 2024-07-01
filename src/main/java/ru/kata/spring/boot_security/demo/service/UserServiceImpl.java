package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserJpaRepository;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserJpaRepository userJpaRepository;
    private RoleRepository roleJpaRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserJpaRepository userJpaRepository, RoleRepository roleJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }

    @Override
    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public User getById(Long id) {
        return userJpaRepository.getById(id);
    }

    @Override
    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userJpaRepository.findByName(auth.getName());
    }

    @Override
    public List<Role> getAllRoles() {
        return roleJpaRepository.findAll();
    }

}
