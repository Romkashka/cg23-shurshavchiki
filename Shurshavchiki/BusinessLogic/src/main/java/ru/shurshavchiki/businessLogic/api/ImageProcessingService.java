package ru.shurshavchiki.businessLogic.api;

import lombok.NonNull;

public interface ImageProcessingService {
    Context ditherImage(@NonNull Context source);
    void generateHistograms(@NonNull Context context);
    void autocorrectImage(@NonNull Context context);
    void scaleImage(@NonNull Context context);
}
