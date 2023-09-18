package carrentalsystem.auth;

import carrentalsystem.entities.Role;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class RoleAuthorizationAspect {
    @Before("@annotation(isAdmin)")
    public void checkUserRoles(IsAdmin isAdmin) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.toString()))) {
                return;

            }

        }
        throw new AccessDeniedException("This option can be accessed only by Administrator!");

        // TODO: return this response:
        //  return ApiResponse.map(HttpStatus.UNAUTHORIZED, null, "This option can be accessed only by Administrator!")
    }
}

