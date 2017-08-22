package br.com.joaops.site.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author João Paulo
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
    
    @Autowired
    @Qualifier("SystemUserDetailsService")
    private UserDetailsService userDetails;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    
    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String username = a.getName();
        String password = a.getCredentials().toString();
        
        UserDetails user = this.userDetails.loadUserByUsername(username);
        
        if (user == null) {
            String message = this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "AbstractUserDetailsAuthenticationProvider.badCredentials");
            throw  new BadCredentialsException(message);
        }
        
        if (!user.getUsername().equals(username)) {
            String message = this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "AbstractUserDetailsAuthenticationProvider.badCredentials");
            throw new BadCredentialsException(message);
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            String message = this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "AbstractUserDetailsAuthenticationProvider.badCredentials");
            throw new BadCredentialsException(message);
        }
        
        if (user.isEnabled() == false) {
            String message = this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "AbstractUserDetailsAuthenticationProvider.disabled");
            throw new DisabledException(message);
        }
        
        if (user.isAccountNonLocked() == false) {
            String message = this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "AbstractUserDetailsAuthenticationProvider.locked");
            throw new LockedException(message);
        }
        
        if (user.isAccountNonExpired() == false) {
            String message = this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "AbstractUserDetailsAuthenticationProvider.expired");
            throw new AccountExpiredException(message);
        }
        
        if (user.isCredentialsNonExpired() == false) {
            String message = this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "AbstractUserDetailsAuthenticationProvider.credentialsExpired");
            throw new CredentialsExpiredException(message);
        }
        
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(),user.getAuthorities());
    }
    
    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}