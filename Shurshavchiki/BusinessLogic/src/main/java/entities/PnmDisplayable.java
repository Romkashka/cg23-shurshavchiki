package entities;

import lombok.NonNull;
import models.RgbConvertable;
import models.RgbPixel;

public interface PnmDisplayable extends Displayable {
    public String getVersion();
    public int getMaxval();
}
