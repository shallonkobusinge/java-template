package rw.ac.rca.nat2022.shallon.servlets.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.nat2022.shallon.servlets.dtos.CreateOrUpdateUserDTO;
import rw.ac.rca.nat2022.shallon.servlets.dtos.LoginDTO;
import rw.ac.rca.nat2022.shallon.servlets.services.IUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final IUserService userService;

    @Autowired
    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateOrUpdateUserDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        return ResponseEntity.ok(userService.profile());
    }
}
