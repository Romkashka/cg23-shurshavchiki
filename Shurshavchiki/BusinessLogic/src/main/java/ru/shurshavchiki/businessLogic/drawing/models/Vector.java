package ru.shurshavchiki.businessLogic.drawing.models;

import java.awt.geom.Point2D;
import java.util.Objects;

public final class Vector {
    private final float Dx;
    private final float Dy;

    public Vector(float Dx, float Dy) {
        this.Dx = Dx;
        this.Dy = Dy;
    }

    public Vector(Point2D start, Point2D end) {
        this.Dx = (float) (end.getX() - start.getX());
        this.Dy = (float) (end.getY() - start.getY());
    }

    public float Dx() {
        return Dx;
    }

    public float Dy() {
        return Dy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Vector) obj;
        return Float.floatToIntBits(this.Dx) == Float.floatToIntBits(that.Dx) &&
                Float.floatToIntBits(this.Dy) == Float.floatToIntBits(that.Dy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Dx, Dy);
    }

    @Override
    public String toString() {
        return "Vector[" +
                "Dx=" + Dx + ", " +
                "Dy=" + Dy + ']';
    }

}
