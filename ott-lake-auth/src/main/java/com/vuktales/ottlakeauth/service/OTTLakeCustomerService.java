package com.vuktales.ottlakeauth.service;


import com.vuktales.ottlakeauth.dto.OTTLakeCustomerLogin;
import com.vuktales.ottlakeauth.dto.OTTLakeCustomerResponse;
import com.vuktales.ottlakeauth.dto.OTTLakeCustomerSignup;

public interface OTTLakeCustomerService {

    OTTLakeCustomerSignup signUp(OTTLakeCustomerSignup signUpDto);

    OTTLakeCustomerResponse login(OTTLakeCustomerLogin loginRequest);
}