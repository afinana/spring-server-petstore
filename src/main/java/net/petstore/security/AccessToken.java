package net.petstore.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Getter
public class AccessToken {


    public static final String BEARER = "Bearer ";
    private final String value;

    public AccessToken(String value) {
        this.value = value == null ? "" : value;
    }

    /** Returns authorities derived from the standard JWT {@code scope} claim. */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        JsonObject payloadAsJson = getPayloadAsJsonObject();
        if (payloadAsJson == null) {
            return List.of();
        }
        String scope = Optional.ofNullable(payloadAsJson.getAsJsonPrimitive("scope"))
                .map(e -> e.getAsString())
                .orElse("");
        return Arrays.stream(scope.split(" "))
                .filter(s -> !s.isBlank())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /** Returns the subject ({@code sub} claim) as the username. */
    public String getUsername() {
        JsonObject payloadAsJson = getPayloadAsJsonObject();
        if (payloadAsJson == null) {
            return "";
        }
        return Optional.ofNullable(payloadAsJson.getAsJsonPrimitive("sub"))
                .map(e -> e.getAsString())
                .orElse("");
    }

    private JsonObject getPayloadAsJsonObject() {
        try {
            DecodedJWT decodedJWT = decodeToken(value);
            return decodeTokenPayloadToJsonObject(decodedJWT);
        } catch (RuntimeException ex) {
            // Token is missing or invalid — treat as anonymous (no roles, empty username)
            return null;
        }
    }

    private DecodedJWT decodeToken(String value) {
        if (isNull(value) || value.isBlank()) {
            throw new InvalidTokenException("Token has not been provided");
        }
        return JWT.decode(value);
    }

    private JsonObject decodeTokenPayloadToJsonObject(DecodedJWT decodedJWT) {
        try {
            String payloadAsString = decodedJWT.getPayload();
            return new Gson().fromJson(
                    new String(Base64.getDecoder().decode(payloadAsString), StandardCharsets.UTF_8),
                    JsonObject.class);
        } catch (RuntimeException exception) {
            throw new InvalidTokenException("Invalid JWT or JSON format of each of the jwt parts", exception);
        }
    }
}