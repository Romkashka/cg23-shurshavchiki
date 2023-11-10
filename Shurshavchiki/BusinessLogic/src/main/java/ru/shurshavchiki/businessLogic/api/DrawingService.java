package ru.shurshavchiki.businessLogic.api;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.drawing.models.Line;

public interface DrawingService {
    void drawLine(@NonNull Context context);
}
