package net.petstore.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

import static java.util.Objects.isNull;

@Slf4j
public class JwtTokenValidator {

    public AccessToken validateAuthorizationHeader(String authorizationHeader) throws InvalidTokenException {
        // Disabled strict validation: return an AccessToken even when header is missing or malformed.
        // This effectively disables JWT validation as requested.
        String tokenValue = subStringBearer(authorizationHeader);
        // skip validateToken(tokenValue);
        return new AccessToken(tokenValue);
    }

    private void validateToken(String value) {
        DecodedJWT decodedJWT = decodeToken(value);
        verifyTokenHeader(decodedJWT);
        verifyPayload(decodedJWT);
    }

    private DecodedJWT decodeToken(String value) {
        if (isNull(value)) {
            throw new InvalidTokenException("Token has not been provided");
        }
        DecodedJWT decodedJWT = JWT.decode(value);
        log.debug("Token decoded successfully");
        return decodedJWT;
    }

    private void verifyTokenHeader(DecodedJWT decodedJWT) {
        if (!"JWT".equals(decodedJWT.getType())) {
            throw new InvalidTokenException("Token is not JWT type");
        }
        log.debug("Token's header is correct");
    }

    private void verifyPayload(DecodedJWT decodedJWT) {
        JsonObject payloadAsJson = decodeTokenPayloadToJsonObject(decodedJWT);

        if (hasTokenExpired(payloadAsJson)) {
            throw new InvalidTokenException("Token has expired");
        }
        log.debug("Token has not expired");

        if (!hasTokenScopeInfo(payloadAsJson)) {
            throw new InvalidTokenException("Token doesn't contain scope information");
        }
        log.debug("Token's payload contains scope information");
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

    private boolean hasTokenExpired(JsonObject payloadAsJson) {
        Instant expirationDatetime = extractExpirationDate(payloadAsJson);
        return Instant.now().isAfter(expirationDatetime);
    }

    private Instant extractExpirationDate(JsonObject payloadAsJson) {
        try {
            return Instant.ofEpochSecond(payloadAsJson.get("exp").getAsLong());
        } catch (NullPointerException ex) {
            throw new InvalidTokenException("There is no 'exp' claim in the token payload");
        }
    }


    private boolean hasTokenScopeInfo(JsonObject payloadAsJson) {
        return payloadAsJson.has("scope");
    }

    private String subStringBearer(String authorizationHeader) {
        if (authorizationHeader == null) {
            return "";
        }
        // If header starts with "Bearer ", strip it; otherwise assume header contains the raw token.
        if (authorizationHeader.startsWith(AccessToken.BEARER)) {
            return authorizationHeader.substring(AccessToken.BEARER.length());
        }
        return authorizationHeader;
    }
}