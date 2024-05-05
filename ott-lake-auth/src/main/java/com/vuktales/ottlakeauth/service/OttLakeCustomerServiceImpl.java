package com.vuktales.ottlakeauth.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.vuktales.ottlakeauth.dto.OTTLakeCustomerLogin;
import com.vuktales.ottlakeauth.dto.OTTLakeCustomerResponse;
import com.vuktales.ottlakeauth.dto.OTTLakeCustomerSignup;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.ws.rs.core.Response;

@Service
public class OttLakeCustomerServiceImpl implements OTTLakeCustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OttLakeCustomerServiceImpl.class);

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    private String role = "ott-role";


    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Override
    public OTTLakeCustomerSignup signUp(OTTLakeCustomerSignup signUpDto) {
        LOGGER.info("signUp... {}", signUpDto);
        Keycloak keycloak =
                KeycloakBuilder.builder().serverUrl(authServerUrl).grantType(OAuth2Constants.PASSWORD)
                        .realm("master").clientId("admin-cli").username("abhiram").password("abhiram")
                        .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build()).build();

        keycloak.tokenManager().getAccessToken();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(signUpDto.getEmail());
        user.setFirstName(signUpDto.getFirstname());
        user.setLastName(signUpDto.getLastname());
        user.setEmail(signUpDto.getEmail());

        // Get realm
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersRessource = realmResource.users();

        Response response = usersRessource.create(user);

        signUpDto.setStatusCode(response.getStatus());
        signUpDto.setStatusMessage(response.getStatusInfo().toString());

        if (response.getStatus() == 201) {

            String userId = CreatedResponseUtil.getCreatedId(response);

            LOGGER.info("Created userId {}", userId);

            // create password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(signUpDto.getPassword());

            UserResource userResource = usersRessource.get(userId);

            // Set password credential
            userResource.resetPassword(passwordCred);

            // Get realm role student
            RoleRepresentation realmRoleUser = realmResource.roles().get(role).toRepresentation();

            // Assign realm role student to user
            userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser));

        }
        return signUpDto;

    }

    @Override
    public OTTLakeCustomerResponse login(OTTLakeCustomerLogin loginRequest) {
        Map<String, Object> clientCredentials = new HashMap<>();

        clientCredentials.put("client_id", "ott-client");
        clientCredentials.put("grant_type", "password");

        Configuration configuration =
                new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);

        AccessTokenResponse response =
                authzClient.obtainAccessToken(loginRequest.getEmail(), loginRequest.getPassword());
        OTTLakeCustomerResponse loginResponse = new OTTLakeCustomerResponse();
        loginResponse.setAccesssToken(response.getToken());
        loginResponse.setRefreshToken(response.getRefreshToken());
        loginResponse.setScope(response.getScope());
        loginResponse.setExpiresIn(response.getExpiresIn());
        loginResponse.setError(response.getError());
        return loginResponse;
    }

}