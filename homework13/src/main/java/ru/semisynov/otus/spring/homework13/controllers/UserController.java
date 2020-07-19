package ru.semisynov.otus.spring.homework13.controllers;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.semisynov.otus.spring.homework13.model.User;
import ru.semisynov.otus.spring.homework13.model.enums.UserRole;
import ru.semisynov.otus.spring.homework13.services.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "UserController")
@Controller("userController")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public String listView(Model model) {
        List<User> users = userService.findAllUsers()
                .stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/edit")
    public String editView(@RequestParam("id") long id, @RequestParam(name = "isCreate", required = false) boolean isCreate, Model model) {
        User user;
        if (!isCreate) {
            user = userService.findUserById(id);
        } else {
            user = new User();
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", UserRole.values());
        return "user/edit";
    }

    @GetMapping("/user/view")
    public String viewPage(@RequestParam("id") long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", UserRole.values());
        return "user/view";
    }

    @PostMapping("/user")
    public String postUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/user";
    }

    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/user";
    }
}