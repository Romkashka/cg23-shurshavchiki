package ru.shurshavchiki.businessLogic.api;

import lombok.NonNull;

public interface ImageProcessingService {
    void ditherImage(@NonNull Context source, @NonNull Context destination);
}
