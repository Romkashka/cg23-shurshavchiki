package ru.shurshavchiki.businessLogic.drawing.models;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

public record Drawing(@Getter FigureOverlap figureOverlap, @Getter RgbConvertable color) {
}
