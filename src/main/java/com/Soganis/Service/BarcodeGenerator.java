package com.Soganis.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class BarcodeGenerator {
    public static void main(String[] args) {
        try {
            String text = "SKSTR18"; // The text to be encoded as a barcode
            String headerText = "shishukung"; // The header text
            String footerText = "TROUSER"; // The footer text
            String filePath = "C:\\Users\\mehul\\Desktop\\Invoice\\barcode.png"; // Path where the barcode image will be saved
            int barcodeWidth = 250; // Width of the barcode image
            int barcodeHeight = 100; // Height of the barcode image
            int margin = 20; // Margin for header and footer

            // Generate the barcode as a BitMatrix
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, barcodeWidth, barcodeHeight);

            // Convert BitMatrix to BufferedImage
            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Create a new image with extra space for header and footer
            BufferedImage finalImage = new BufferedImage(barcodeWidth, barcodeHeight + margin * 3, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = finalImage.createGraphics();

            // Set background color
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, barcodeWidth, barcodeHeight + margin * 3);

            // Draw the header
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(headerText, (barcodeWidth - g.getFontMetrics().stringWidth(headerText)) / 2, margin - 5);

            // Draw the barcode
            g.drawImage(barcodeImage, 0, margin, null);

            // Draw the barcode text (footer part 1)
            g.drawString(text, (barcodeWidth - g.getFontMetrics().stringWidth(text)) / 2, barcodeHeight + margin + g.getFontMetrics().getHeight() - 5);

            // Draw the "T-shirt" text (footer part 2)
            g.drawString(footerText, (barcodeWidth - g.getFontMetrics().stringWidth(footerText)) / 2, barcodeHeight + margin + g.getFontMetrics().getHeight() + 15);

            g.dispose();

            // Save the final image to the specified file path
            Path path = Paths.get(filePath);
            ImageIO.write(finalImage, "PNG", path.toFile());

            System.out.println("Barcode with header and footer generated and saved to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

