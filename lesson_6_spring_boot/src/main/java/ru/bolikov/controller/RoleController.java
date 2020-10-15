package ru.bolikov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bolikov.entity.specification.RoleSpecification;
import ru.bolikov.entity.specification.UserSpecification;
import ru.bolikov.entity.users.Role;
import ru.bolikov.entity.users.User;
import ru.bolikov.exception.NotFoundException;
import ru.bolikov.repositories.RoleRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RoleController {
    private final static Integer itemCount = 5;

    private final static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/admin/role")
    public String allRole(Model model,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam(value = "roleName", required = false) String roleName) {
        logger.info("Filtering by role: {} ", roleName);
        int currentPage = page.orElse(1);
        PageRequest pageRequest = PageRequest.of(currentPage - 1, itemCount);

        Specification<Role> spec = RoleSpecification.trueLiteral();

        if (roleName != null && !roleName.isEmpty()) {
            spec = spec.and(RoleSpecification.roleLike(roleName));
        }

        model.addAttribute("rolePage", roleRepository.findAll(spec, pageRequest));
        return "roles";
    }

    @GetMapping("/admin/role/{id}")
    public String editRole(@PathVariable("id") Integer id, Model model) {
        Role role = roleRepository.findById(id).orElseThrow(new NotFoundException(null, "Role"));
        model.addAttribute("role", role);
        return "role";
    }

    @GetMapping("/admin/role/new")
    public String newRole(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "role";
    }

    @PostMapping("/admin/role/update")
    public String updateRole(@Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "role";
        }
        roleRepository.save(role);
        return "redirect:/admin/role";
    }

    @DeleteMapping("/admin/role/{id}/delete")
    public String deleteRole(@PathVariable("id") Integer id) {
        roleRepository.deleteById(id);
        return "redirect:/admin/role";
    }
}
