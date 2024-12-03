package com.journey_back.controller;


import com.journey_back.infra.security.TokenInfoJWT;
import com.journey_back.infra.security.TokenService;
import com.journey_back.model.UserModel;
import com.journey_back.infra.security.AutenticationInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@CrossOrigin("*")
public class AutenticationController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AutenticationInfo info) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(info.email() ,info.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generateToken((UserModel) authentication.getPrincipal());


        return ResponseEntity.ok(new TokenInfoJWT(tokenJWT));
    }
}
