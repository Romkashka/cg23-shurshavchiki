package ru.shurshavchiki.businessLogic.api;

import lombok.NonNull;

public interface ImageProcessingService {
    Context ditherImage(@NonNull Context source);
}
