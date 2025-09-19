package org.kfokam48.inscriptionenlignebackend.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.kfokam48.inscriptionenlignebackend.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuditAspect {
    
    private final SecurityService securityService;
    
    public AuditAspect(@Qualifier("securityServiceImpl") SecurityService securityService) {
        this.securityService = securityService;
    }
    
    @AfterReturning("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
                   "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
                   "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void auditModifications(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = getCurrentRequest();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (request != null && auth != null) {
                String action = determinerAction(request.getMethod());
                String entite = extraireEntite(joinPoint);
                String utilisateur = auth.getName();
                
                securityService.loggerAction(action, entite, null, utilisateur, request);
            }
        } catch (Exception e) {
            // Log silencieux pour ne pas interrompre le flux
        }
    }
    
    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
    
    private String determinerAction(String method) {
        switch (method) {
            case "POST": return "CREATE";
            case "PUT": return "UPDATE";
            case "DELETE": return "DELETE";
            default: return "ACTION";
        }
    }
    
    private String extraireEntite(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        return className.replace("Controller", "");
    }
}