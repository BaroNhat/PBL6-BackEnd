package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.AuthenticationRequest;
import PBL6.example.UNIME.dto.request.IntrospectRequest;
import PBL6.example.UNIME.dto.request.LogoutRequest;
import PBL6.example.UNIME.dto.request.RefreshRequest;
import PBL6.example.UNIME.dto.response.AuthenticationResponse;
import PBL6.example.UNIME.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;


}
