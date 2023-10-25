package ru.shurshavchiki.businessLogic.drawing.models;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FigureOverlap {
    @Getter
    private final List<PixelOverlap> pixelOverlaps;

    public FigureOverlap() {
        pixelOverlaps = new ArrayList<>();
    }


    //TODO: if pixels has same coordinates one with higher percentage should stay (maybe it's better to do it in
    // service while creating resulting image)
    public void addAll(List<PixelOverlap> pixelOverlaps) {
        this.pixelOverlaps.addAll(pixelOverlaps);
    }

    public void addAll(PixelOverlap... pixelOverlaps) {
        Collections.addAll(this.pixelOverlaps, pixelOverlaps);
    }
}
