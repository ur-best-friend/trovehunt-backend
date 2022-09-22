package com.example.system.controllers;

import com.example.system.dto.treasure.UserTreasuresDto;
import com.example.system.entity.User;
import com.example.system.jsonviews.TreasureView;
import com.example.system.services.TreasureService;
import com.example.system.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final TreasureService treasureService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if(session != null) session.invalidate();
        return "done";
    }

    @GetMapping("/me")
    public User getCurUser(){
        return userService.getCurrentUser();
    }

    @PutMapping("/me/set-department")
    public User setDepartment(@RequestBody String department){
        return userService.setDepartmentAndSave(department);
    }

    @PostMapping("/me/set-profile-image")
    public User setProfileImage(@RequestParam("file") MultipartFile multipartFile) throws IOException{
        return userService.setProfileImageAndSave(userService.getCurrentUser(), multipartFile);
    }

    @GetMapping("/{uid}/treasures")
    @JsonView(TreasureView.CompleteView.class)
    public UserTreasuresDto getUserTreasures(@PathVariable Integer uid){
        return treasureService.getTreasuresForUser(uid);
    }
}
