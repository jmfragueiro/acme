package ar.com.acme.framework.core.token;

import ar.com.acme.framework.common.Constantes;

public class TokenAuthority implements ITokenAuthority {
    private final String role;

    public TokenAuthority(String role) {
        if (role == null || role.length() == 0) {
            throw new IllegalArgumentException(Constantes.MSJ_TOK_ERR_NOAUTHCAD);
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
