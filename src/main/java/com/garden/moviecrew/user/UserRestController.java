package com.garden.moviecrew.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 API
    @PostMapping("/join")
    public Map<String, String> join(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("nickName") String nickName,
            @RequestParam("birthday") String birthday,
            @RequestParam("email") String email,
            @RequestParam("gender") String gender) {

        LocalDate birthdayDate = null;
        if (birthday != null && !birthday.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthdayDate = LocalDate.parse(birthday, formatter);
        }

        User user = userService.addUser(loginId, password, name, nickName, birthdayDate, email, gender);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", user != null ? "success" : "fail");

        return resultMap;
    }

    @GetMapping("/duplicateIdCheck")
    public Map<String, Boolean> isDuplicateId(@RequestParam("loginId") String loginId) {
        boolean idDuplicateId = userService.isDuplicateId(loginId);
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isDuplicateId", idDuplicateId);
        return resultMap;
    }

    // 로그인 API
    @PostMapping("/login")
    public Map<String, String> login(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            HttpSession session) {

        User user = userService.login(loginId, password);
        Map<String, String> resultMap = new HashMap<>();

        if (user != null) {
            resultMap.put("result", "success");
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userNickName", user.getNickName());
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

    // 개인정보 수정 API
    @PutMapping("/edit")
    public Map<String, String> edit(
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam(value = "birthday", required = false) String birthday,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "gender", required = false) String gender,
            HttpSession session) {

        int userId = (Integer) session.getAttribute("userId");

        LocalDate birthdayDate = null;
        if (birthday != null && !birthday.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthdayDate = LocalDate.parse(birthday, formatter);
        }

        User user = userService.editUser(userId, password, name, nickName, birthdayDate, email, gender);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", user != null ? "success" : "fail");

        return resultMap;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        return "redirect:login-view";
    }
}
