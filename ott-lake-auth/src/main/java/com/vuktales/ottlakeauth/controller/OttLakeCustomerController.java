package com.vuktales.ottlakeauth.controller;



import java.util.Arrays;

import com.vuktales.ottlakeauth.dto.OTTLakeCustomerLogin;
import com.vuktales.ottlakeauth.dto.OTTLakeCustomerResponse;
import com.vuktales.ottlakeauth.dto.OTTLakeCustomerSignup;
import com.vuktales.ottlakeauth.service.OTTLakeCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/ott-lake-customers")
public class OttLakeCustomerController {
    @Autowired
    private OTTLakeCustomerService userService;

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OTTLakeCustomerSignup> signUp(@RequestBody OTTLakeCustomerSignup signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OTTLakeCustomerResponse> login(@RequestBody OTTLakeCustomerLogin loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }
//
    @GetMapping(path = "/data")
    public ResponseEntity<?> data() {
        return ResponseEntity.ok(Arrays.asList("Hello world!"));
    }
}

