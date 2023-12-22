package ru.shurshavchiki.businessLogic.domain.io.png.chunkDataExtractorImplementations;

import lombok.Setter;
import ru.shurshavchiki.businessLogic.domain.io.png.PngChunkDataConverter;
import ru.shurshavchiki.businessLogic.domain.io.png.PngChunk;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class PngChunkDataConverterBase implements PngChunkDataConverter {
    private static final int[] CRC_TABLE = new int[256];

    private static List<byte[]> savedIDATChunks = new ArrayList<>();

    private static boolean table_computed = false;

    private static void make_table(){
        for (int i = 0; i < 256; i++) {
            int c = i;
            for (int j = 0; j < 8; j++) {
                if ((c & 1) == 1) {
                    c = 0xedb88320 ^ (c >>> 1);
                } else {
                    c = c >>> 1;
                }
            }
            CRC_TABLE[i] = c;
        }
        table_computed = true;
    }

    @Override
    public void extractData(PngChunk chunk, ImageDataHolder dataHolder) {
        handleChunk(chunk, dataHolder);
    }

    @Override
    public List<PngChunk> storeData(List<PngChunk> storedChunks, ImageDataHolder dataHolder) {
        List<PngChunk> currentStageChinks = convertRawData(dataHolder);

        return currentStageChinks;
    }

    protected void handleChunk(PngChunk chunk, ImageDataHolder dataHolder){
        String chunkType = chunk.ChunkType();
        byte[] data = null;
        ByteBuffer wrapped = null;
        switch (chunkType) {
            case "IHDR":
                data = chunk.Data();
                System.out.println(Arrays.toString(data));
                wrapped = ByteBuffer.wrap( Arrays.copyOfRange(data, 0, 4) );
                int width = wrapped.getInt();
                System.out.println(width);
                wrapped = ByteBuffer.wrap( Arrays.copyOfRange(data, 4, 8) );
                int height = wrapped.getInt();
                System.out.println(height);
                //wrapped = ByteBuffer.wrap( Arrays.copyOfRange(data, 8, 9) );
                int bitDepth = data[8] & 0xff;
                System.out.println(bitDepth);
                //wrapped = ByteBuffer.wrap( Arrays.copyOfRange(data, 9, 10) );
                int colorType = data[9] & 0xff;
                System.out.println(colorType);
                //wrapped = ByteBuffer.wrap( Arrays.copyOfRange(data, 10, 11) );
                int compressionMethod = data[10] & 0xff;
                System.out.println("Compression method: " + compressionMethod);
                //wrapped = ByteBuffer.wrap( Arrays.copyOfRange(data, 11, 12) );
                int filterMethod = data[11] & 0xff;
                System.out.println(filterMethod);
                //wrapped = ByteBuffer.wrap( Arrays.copyOfRange(data, 12, 13) );
                int interlaceMethod = data[12] & 0xff;
                System.out.println(interlaceMethod);
                Header header = new Header("PNG", width, height, (int) Math.pow(2, bitDepth));
                //---------------------------
                byte[] pixels = new byte[width * height * 3];
                for (int y = 0; y < height; y++){
                    for (int x = 0 ; x < width ; x++){
                        pixels[y * width + x * 3] = 0x00;
                        pixels[y * width + x * 3 + 1] = 0x00;
                        pixels[y * width + x * 3 + 2] = 0x00;
                    }
                }
                dataHolder.setData(pixels);
                //---------------------------
                dataHolder.setHeader(header);
                System.out.println(checkCRC(chunk));
                break;
            case "sRGB":
                data = chunk.Data();
                int renderingIntent = data[0] & 0xff;
                System.out.println(checkCRC(chunk));
                break;
            case "gAMA":
                data = chunk.Data();
                wrapped = ByteBuffer.wrap( Arrays.copyOfRange(data, 0, 4) );
                Integer gammaInt = wrapped.getInt();
                float gamma = (float) gammaInt / 100000;
                dataHolder.setGamma(gamma);
                System.out.println(gamma);
                break;
            case "pHYs":
                break;
            case "IDAT":
                savedIDATChunks.add(chunk.Data());
            default:
                break;
        }
    }

    public void processIDATChunks(ImageDataHolder dataHolder) throws DataFormatException{
        int totalByteLen = 0;
        for (int i = 0; i < savedIDATChunks.size(); i++){
            totalByteLen += savedIDATChunks.get(i).length;
        }
        ByteBuffer tempBuffer = ByteBuffer.allocate(totalByteLen);
        for (int i = 0; i < savedIDATChunks.size(); i++){
            tempBuffer.put(savedIDATChunks.get(i));
        }
        byte[] bytes = tempBuffer.array();
        Inflater decompresser = new Inflater();
        
        decompresser.reset();
        decompresser.setInput(bytes);
        byte[] result = new byte[2000000];
        decompresser.inflate(result);
        byte[] dataImage = new byte[dataHolder.getHeader().getWidth() * dataHolder.getHeader().getHeight() * 3];
        List<List<RgbConvertable>> pixels = new ArrayList<>();
        for (int y = 0; y < dataHolder.getHeader().getHeight(); y++){
            for (int x = 0; x < dataHolder.getHeader().getWidth(); x++){
                dataImage[y * dataHolder.getHeader().getWidth() * 3 + x * 3] = (byte) (result[y * 3 * (dataHolder.getHeader().getWidth()) + y + 1 + x * 3] & 0xff);
                dataImage[y * dataHolder.getHeader().getWidth() * 3 + x * 3 + 1] = (byte) (result[y * 3 * (dataHolder.getHeader().getWidth()) + y + 2 + x * 3] & 0xff);
                dataImage[y * dataHolder.getHeader().getWidth() * 3 + x * 3 + 2] = (byte) (result[y * 3 * (dataHolder.getHeader().getWidth()) + y + 3 + x * 3] & 0xff);
                // System.out.println(y * dataHolder.getHeader().getWidth() * 3 + x * 3);
                // System.out.println(y * 3 * (dataHolder.getHeader().getWidth() + 1) + 1 + x * 3);
                // System.out.println();
                // System.out.println(result[y * (dataHolder.getHeader().getWidth() + 1) + 1 + x * 3]);
                // System.out.println(result[y * (dataHolder.getHeader().getWidth() + 1) + 2 + x * 3]);
                // System.out.println(result[y * (dataHolder.getHeader().getWidth() + 1) + 3 + x * 3]);
                // RgbPixel pixel = new RgbPixel(result[y * (dataHolder.getHeader().getWidth() + 1) + 1 + x*3]/255,
                // result[y * (dataHolder.getHeader().getWidth() + 1) + 2 + x*3], result[y * (dataHolder.getHeader().getWidth() + 1) + 3 + x*3]);
                // pixels.get(y).add(pixel);
            }
        }
        dataHolder.setData(dataImage);
        System.out.println(Arrays.toString(Arrays.copyOfRange(result, 0, 5 * (dataHolder.getHeader().getWidth() * 3) + 6)));
        System.out.println(dataHolder.getHeader().getMaxValue());
        //System.out.println(Arrays.toString(Arrays.copyOfRange(dataImage, 0, 5 * (dataHolder.getHeader().getWidth() * 3))));
    }

    protected List<PngChunk> convertRawData(ImageDataHolder dataHolder){
        List<PngChunk> chunks = new ArrayList<PngChunk>();
        Header header = dataHolder.getHeader();
        byte[] widthBytes = ByteBuffer.allocate(4).putInt(header.getWidth()).array();
        byte[] heightBytes = ByteBuffer.allocate(4).putInt(header.getHeight()).array();
        byte[] bitDepthBytes = ByteBuffer.allocate(1).put((byte)Math.ceil((Math.log(header.getMaxValue())/Math.log(2)+1e-10))).array();
        byte[] colorType = ByteBuffer.allocate(1).put((byte) 2).array();
        byte[] compressionMethod = ByteBuffer.allocate(1).put((byte) 0).array();
        byte[] filterMethod = ByteBuffer.allocate(1).put((byte) 0).array();
        byte[] interlaceMethod = ByteBuffer.allocate(1).put((byte) 0).array();
        byte[] ihdrbytes = ByteBuffer.allocate(13).put(widthBytes)
                .put(heightBytes).put(bitDepthBytes).put(colorType).put(compressionMethod)
                .put(filterMethod).put(interlaceMethod).array();
        PngChunk ihdrChunk = new PngChunk("IHDR", 13, ihdrbytes, calculateCRC("IHDR", ihdrbytes));
        chunks.add(ihdrChunk);

        //
        

        return chunks;
    }

    private static int calculateCRC(PngChunk chunk) {
        if (!table_computed){
            make_table();
        }
        Charset charset = Charset.forName("ASCII");
        byte[] typeBytes = chunk.ChunkType().getBytes(charset);
        byte[] data = new byte[typeBytes.length + chunk.Data().length];
        System.arraycopy(typeBytes, 0, data, 0, typeBytes.length);
        System.arraycopy(chunk.Data(), 0, data, typeBytes.length, chunk.Data().length);
        int crc = 0xffffffff;
        for (byte b : data) {
            crc = (crc >>> 8) ^ CRC_TABLE[(crc ^ b) & 0xff];
        }
        return crc ^ 0xffffffff;
    }
    
        private static int calculateCRC(String ChunkName, byte[] info) {
        if (!table_computed){
            make_table();
        }
        Charset charset = Charset.forName("ASCII");
        byte[] typeBytes = ChunkName.getBytes(charset);
        byte[] data = new byte[typeBytes.length + info.length];
        System.arraycopy(typeBytes, 0, data, 0, typeBytes.length);
        System.arraycopy(info, 0, data, typeBytes.length, info.length);
        int crc = 0xffffffff;
        for (byte b : data) {
            crc = (crc >>> 8) ^ CRC_TABLE[(crc ^ b) & 0xff];
        }
        return crc ^ 0xffffffff;
    }


    private boolean checkCRC(PngChunk chunk){
        System.out.println(chunk.Crc());
        System.out.println(calculateCRC(chunk));
        return chunk.Crc() == calculateCRC(chunk);
    }
}
