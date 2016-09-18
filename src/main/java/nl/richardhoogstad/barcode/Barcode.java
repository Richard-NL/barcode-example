package nl.richardhoogstad.barcode;

import java.awt.Color;
import com.google.zxing.*;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * Created by Richard  Hoogstad on 18-9-16.
 */
public class Barcode {
    public static void main(String... args) {
        try {
            BufferedImage barcode = createBarCode("http://www.hoogstad.nl", BarcodeFormat.QR_CODE, 500, 500);

            File outputfile = new File("/home/richard/Desktop/hoogstad-cr-code.png");
            ImageIO.write(barcode, "png", outputfile);

        } catch (WriterException | IOException e) {
            System.out.print(e);
        }
    }

    public static BufferedImage createBarCode (String codeData, BarcodeFormat barcodeFormat, int codeHeight, int codeWidth) throws WriterException {

        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put (EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        Writer codeWriter;
        if (barcodeFormat == BarcodeFormat.QR_CODE) {
            codeWriter = new QRCodeWriter();
        } else if (barcodeFormat == BarcodeFormat.CODE_128) {
            codeWriter = new Code128Writer();
        } else {
            throw new RuntimeException ("Format Not supported.");
        }

        BitMatrix byteMatrix = codeWriter.encode (
                codeData,
                barcodeFormat,
                codeWidth,
                codeHeight,
                hintMap
        );

        int width   = byteMatrix.getWidth ();
        int height  = byteMatrix.getHeight ();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < width; i ++) {
            for (int j = 0; j < height; j ++) {
                bufferedImage.setRGB(i, j, byteMatrix.get (i, j) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        return bufferedImage;
    }
}
