package rw.ac.rca.nat2022.shallon.servlets.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.nat2022.shallon.servlets.dtos.CreateOrUpdateUserDTO;
import rw.ac.rca.nat2022.shallon.servlets.services.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {


    private final IUserService userService;


    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @GetMapping
    ResponseEntity<?> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }


    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }


    @PostMapping
    ResponseEntity<?> save(@RequestBody CreateOrUpdateUserDTO user){
        return ResponseEntity.ok(userService.save(user));
    }
}
