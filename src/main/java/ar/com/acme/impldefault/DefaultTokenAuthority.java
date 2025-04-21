package ar.com.acme.impldefault;

import ar.com.acme.framework.core.token.ITokenAuthority;

public class DefaultTokenAuthority implements ITokenAuthority {
    private final String role;

    public DefaultTokenAuthority(String role) {
        if (role == null || role.length() == 0) {
            throw new IllegalArgumentException("A granted authority textual representation is required");
        }
        this.role = role;
    }

    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof DefaultTokenAuthority) {
            DefaultTokenAuthority sga = (DefaultTokenAuthority)obj;
            return this.role.equals(sga.getAuthority());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}
