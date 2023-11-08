package ru.shurshavchiki.businessLogic.domain.util;

import ru.shurshavchiki.businessLogic.exceptions.ImplementationRepositoryException;

import java.util.List;

public abstract class ImplementationRepositoryBase<T extends WithName> implements ImplementationsRepository {
    protected final List<T> implementations;

    protected ImplementationRepositoryBase(List<T> implementations) {
        this.implementations = implementations;
    }

    @Override
    public List<String> getAllImplementations() {
        return implementations.stream().map(T::getName).toList();
    }

    @Override
    public T getImplementationByName(String name) {
        var result = implementations.stream().filter(impl -> impl.getName().equals(name)).findAny();

        if (result.isEmpty()) {
            throw ImplementationRepositoryException.NoSuchImplementation(name);
        }

        return result.get();
    }
}
