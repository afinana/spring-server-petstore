package net.petstore.security.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Aspect
@Component
public class RolesAspect {

    @Before("@annotation(net.petstore.security.annotation.AllowedRoles)")
    public void before(JoinPoint joinPoint) {

        String[] expectedRoles = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(AllowedRoles.class).value();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // No authentication or anonymous authentication -> treat as unauthenticated and allow (permitAll)
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return;
        }
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        if (grantedAuthorities == null || grantedAuthorities.isEmpty()) {
            return;
        }

        List<String> roles = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        if (!roles.containsAll(Arrays.asList(expectedRoles))) {
            throw new AccessDeniedException(String.format("Unauthorized request. Expected to have %s roles, but have %s", Arrays.asList(expectedRoles), roles));
        }
    }
}