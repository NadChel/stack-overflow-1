package stack.overflow.util;

import org.springframework.security.core.Authentication;
import stack.overflow.model.entity.Account;

public class AuthenticationProcessor {
    public static Account extractAccount(Authentication authentication) {
        return (Account) authentication.getPrincipal();
    }

    public static String extractUsername(Authentication authentication) {
        Account account = extractAccount(authentication);
        return account.getUsername();
    }
}
