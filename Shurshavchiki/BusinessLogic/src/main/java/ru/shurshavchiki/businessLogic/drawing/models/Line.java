package ru.shurshavchiki.businessLogic.drawing.models;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.awt.geom.Point2D;

public record Line(Point2D start, Point2D end, float width, RgbConvertable color, float alpha) {
}
