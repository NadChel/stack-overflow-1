package stack.overflow.util;

import org.springframework.security.core.Authentication;
import stack.overflow.model.entity.Account;

/**
 * An object that provides shorthand methods for common operations
 * with {@code org.springframework.security.core.Authentication} instances
 */
public class AuthenticationProcessor {
    /**
     * Extracts the {@code stack.overflow.model.entity.Account} instance associated
     * with the given {@code Authentication} object
     *
     * @param authentication {@code Authentication} instance
     * @return associated {@code Account} instance
     */
    public static Account extractAccount(Authentication authentication) {
        return (Account) authentication.getPrincipal();
    }

    /**
     * Extracts the username of the associated {@code Account} object
     *
     * @param authentication {@code Authentication} instance
     * @return username of the associated {@code Account} instance
     * @implNote to retrieve an {@code Account} object, the method invokes {@code extractAccount()}
     */
    public static String extractUsername(Authentication authentication) {
        Account account = extractAccount(authentication);
        return account.getUsername();
    }
}
