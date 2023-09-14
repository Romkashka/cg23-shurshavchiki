package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.RgbConvertable;

import java.util.ArrayList;

@AllArgsConstructor
public abstract class AbstractPnmFile implements Displayable {
    @Getter
    private String version;
    @Getter
    private String name;
    @Getter
    private final int height;
    @Getter
    private final int width;
    @Getter
    private final int maxval;
    @Getter
    private ArrayList<ArrayList<RgbConvertable>> pixels;
}
