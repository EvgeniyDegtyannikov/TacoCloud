package tacos.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tacos.domain.User;

@Service
public class AuthorizeService {

    public boolean isAvailableUserPage(String id) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return current.getId().equals(Long.valueOf(id)) ||
                AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication()
                        .getAuthorities()).contains("ROLE_admin");
    }
}
