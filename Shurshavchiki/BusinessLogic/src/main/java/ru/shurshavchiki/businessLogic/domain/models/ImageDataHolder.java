package ru.shurshavchiki.businessLogic.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ImageDataHolder {
    @Getter @Setter
    private Header header;
    @Getter @Setter
    private byte[] data;
    @Getter @Setter
    private Float gamma;

    public ImageDataHolder(Header header, byte[] data) {
        this.header = header;
        this.data = data;
        gamma = null;
    }

    public float[] getFloatData() {
        float[] floatData = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            floatData[i] = ((data[i] + 256) % 256) / 255f;
        }

        return floatData;
    }
}
