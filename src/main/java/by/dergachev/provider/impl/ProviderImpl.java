package by.dergachev.provider.impl;

import by.dergachev.provider.Provider;

public class ProviderImpl<T> implements Provider<T> {

    private final T instance;

    public ProviderImpl(T instance) {
        this.instance = instance;
    }

    @Override
    public T getInstance() {
        return this.instance;
    }
}
