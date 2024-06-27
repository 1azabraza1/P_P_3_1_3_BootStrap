package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserJpaRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, UserJpaRepository userJpaRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @RequestMapping
    public String showAllUser(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping("/add")
    public String add(Model model) {
        User user = new User();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    public String createOrUpdateUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        User user = userService.getById(id);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
