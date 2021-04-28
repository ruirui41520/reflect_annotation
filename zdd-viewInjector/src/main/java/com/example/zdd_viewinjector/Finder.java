package com.example.zdd_viewinjector;

public interface Finder<T> {
    /**
     *
     * @param host
     * @param source
     * @param provider
     */
    void inject(T host, Object source, Provider provider);
}
