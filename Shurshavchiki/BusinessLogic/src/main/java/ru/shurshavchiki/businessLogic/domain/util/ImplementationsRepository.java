package ru.shurshavchiki.businessLogic.domain.util;

import java.util.List;

public interface ImplementationsRepository<T extends WithName> {
    List<String> getAllImplementations();
    T getImplementationByName(String name);
}
