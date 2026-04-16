package com.notifapi.app;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QrHelper {
    public static String generateQrBase64(String upiId, String upiName, double amount, String orderId) {
        try {
            String upiUrl = "upi://pay?pa=" + upiId
                + "&pn=" + upiName.replace(" ", "%20")
                + "&am=" + amount
                + "&tn=Order%20" + orderId
                + "&cu=INR";
            // Simple QR using ZXing
            com.google.zxing.QRCodeWriter writer = new com.google.zxing.QRCodeWriter();
            com.google.zxing.common.BitMatrix matrix = writer.encode(upiUrl,
                com.google.zxing.BarcodeFormat.QR_CODE, 300, 300);
            Bitmap bmp = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
            for (int x = 0; x < 300; x++)
                for (int y = 0; y < 300; y++)
                    bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            return "";
        }
    }
}
