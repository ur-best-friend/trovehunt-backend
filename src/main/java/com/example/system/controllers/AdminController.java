package com.example.system.controllers;

import com.example.system.entity.TreasureFinding;
import com.example.system.exceptions.unauthorized.InvalidAdminTokenException;
import com.example.system.jsonviews.TreasureFindingView;
import com.example.system.services.AdminService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: Add custom adminToken filter OR add admin authority and use @Secured("...") annotation

//Used by /panel.html page
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    //NOTE: Can be both specified in location.properties and as ENV variable
    @Value("${panel.token}")
    private String panelToken;
    private final AdminService adminService;

    @GetMapping("/findings/unverified")
    @JsonView(TreasureFindingView.CompleteView.class)
    public List<TreasureFinding> allUnverifiedFindings(@RequestHeader String adminToken){
        if(!panelToken.equals(adminToken)) throw new InvalidAdminTokenException();

        return adminService.getUnverifiedFindings();
    }

    @PutMapping("/findings/verify/{id}")
    @JsonView(TreasureFindingView.CompleteView.class)
    public TreasureFinding verifyFinding(@RequestHeader String adminToken, @PathVariable int id){
        if(!panelToken.equals(adminToken)) throw new InvalidAdminTokenException();
        return adminService.verifyOrDeclineFinding(id, true);
    }

    @PutMapping("/findings/decline/{id}")
    @JsonView(TreasureFindingView.CompleteView.class)
    public TreasureFinding declineFinding(@RequestHeader String adminToken, @PathVariable int id){
        if(!panelToken.equals(adminToken)) throw new InvalidAdminTokenException();
        return adminService.verifyOrDeclineFinding(id, false);
    }
}
