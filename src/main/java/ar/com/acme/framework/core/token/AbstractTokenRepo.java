package ar.gov.posadas.mbe.framework.core.token;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractTokenRepo<K, T extends IToken<K, ITokenPayload>> implements ITokenRepo<K, T> {
    private final Map<K, T> tokens = getRepo();

    protected abstract Map<K, T> getRepo();

    protected abstract Long getMinTokenValidity();

    @Override
    public void addToken(K clave, T token) {
        token.reinitTerm(getMinTokenValidity());
        tokens.put(clave, token);
    }

    @Override
    public int size() {
        return tokens.size();
    }

    @Override
    public boolean tokenExist(K clave) {
        return tokens.containsKey(clave);
    }

    @Override
    public Optional<T> getToken(K clave) {
        return Optional.of(tokens.get(clave));
    }

    @Override
    public void removeToken(K clave) {
        tokens.remove(clave);
    }

    @Override
    public List<T> getTokenList() {
        return tokens.values().stream().toList();
    }
}
