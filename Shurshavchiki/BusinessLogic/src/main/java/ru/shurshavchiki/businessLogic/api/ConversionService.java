package ru.shurshavchiki.businessLogic.api;

import lombok.NonNull;

public interface ConversionService {
    void applyColorFilters(@NonNull Context context);
    void assignGamma(@NonNull Context context);
    void convertGamma(@NonNull Context context);
}
