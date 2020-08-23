package com.yuqiaotech.common.hibernate;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.yuqiaotech.security.domain.SecurityUserDetails;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long>
{
    @Override
    public Optional<Long> getCurrentAuditor()
    {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null)
        {
            return Optional.of(-1L);
        }
        if (ctx.getAuthentication() == null)
        {
            return Optional.of(-1L);
        }
        if (ctx.getAuthentication().getPrincipal() == null)
        {
            return Optional.of(-1L);
        }
        Object principal = ctx.getAuthentication().getPrincipal();
        if (principal instanceof SecurityUserDetails)
        {
            return Optional.of(((SecurityUserDetails)principal).getId());
        }
        return Optional.of(-1L);
    }
}
