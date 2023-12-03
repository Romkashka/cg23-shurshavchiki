package ru.shurshavchiki.businessLogic.api;

import lombok.NonNull;

import java.io.File;
import java.io.IOException;

public interface FileProcessingService {
    void createNewImage(@NonNull Context context) throws IOException;
    void openImage(@NonNull Context context) throws IOException;
    void saveImage(@NonNull Context context) throws IOException;
    void saveImageAs(@NonNull Context context, @NonNull File destination) throws IOException;
}
