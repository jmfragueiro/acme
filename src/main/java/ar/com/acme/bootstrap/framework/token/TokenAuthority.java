package ar.com.acme.bootstrap.framework.token;

import ar.com.acme.adapter.token.IEntityTokenAuthority;
import ar.com.acme.bootstrap.common.Constants;

public class TokenAuthority implements IEntityTokenAuthority {
    private final String role;

    public TokenAuthority(String role) {
        if (role == null || role.length() == 0) {
            throw new IllegalArgumentException(Constants.MSJ_TOK_ERR_NOAUTHCAD);
        }
        this.role = role;
    }

    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof TokenAuthority) {
            TokenAuthority sga = (TokenAuthority)obj;
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
