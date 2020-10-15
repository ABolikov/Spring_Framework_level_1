package ru.bolikov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.bolikov.entity.users.Role;
import ru.bolikov.entity.users.User;
import ru.bolikov.exception.NotFoundException;
import ru.bolikov.repositories.RoleRepository;
import ru.bolikov.repositories.UserRepository;
import ru.bolikov.entity.specification.UserSpecification;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final static Integer itemCount = 5;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Добавить фильтрацию по ролям
    @GetMapping("/admin/user")
    public String allUsers(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam(value = "login", required = false) String login,
                           @RequestParam(value = "userRole", required = false) String userRole) {
        logger.info("Filtering by name: {} roles: {}", login, userRole);
        int currentPage = page.orElse(1);
        PageRequest pageRequest = PageRequest.of(currentPage - 1, itemCount);

        Specification<User> spec = UserSpecification.trueLiteral();

        if (login != null && !login.isEmpty()) {
            spec = spec.and(UserSpecification.loginLike(login));
        }

        model.addAttribute("usersPage", userRepository.findAll(spec, pageRequest));
        return "users";
    }

    @GetMapping("/admin/user/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(new NotFoundException(null, "User"));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin/user/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> test = roleRepository.findAll();
        model.addAttribute("allRoles", test);
        return "user";
    }

//    @GetMapping("/admin/user/new")
//    public ModelAndView newUser() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("user");
//        User user = new User();
//        modelAndView.addObject("user", user);
//        List<Role> roles = roleRepository.findAll();
//        modelAndView.addObject("allRoles", roles);
//        return modelAndView;
//    }

    @PostMapping("/admin/user/update")
    public String updateUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/admin/user";
    }

    @DeleteMapping("/admin/user/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/admin/user";
    }
}
