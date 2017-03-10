package com.fihou.babicare.Data;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

/**
 * compress & decompress using zlib
 * @author dongtv
 */
public class ZlibCompressionUtil {
    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = null;
        ByteArrayOutputStream outputStream = null;
        byte[] output = null;
        try {
            deflater = new Deflater();
            deflater.setInput(data);
            outputStream = new ByteArrayOutputStream(data.length);
            deflater.finish();
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer); // returns the generated code... index  
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            output = outputStream.toByteArray();
        } finally {
            if (deflater != null) {
                deflater.end();
            }
        }
        return output;
    }
    public static byte[] gzipDecompress(byte[] data) throws IOException, DataFormatException {
        try {
            final int sChunk = 1024;
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            GZIPInputStream zipin = new GZIPInputStream(in);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[sChunk];
            int len;
            while ((len = zipin.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.close();
            zipin.close();
            return out.toByteArray();
        } finally {
        }
    }
    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = null;
        byte[] output = null;
        try {
            inflater = new Inflater();
            inflater.setInput(data);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            output = outputStream.toByteArray();
        } finally {
            if (inflater != null) {
                inflater.end();
            }
        }
        return output;
    }
}
