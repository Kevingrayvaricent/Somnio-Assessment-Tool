package pdfservice.util;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class PdfUtil {

	public static void writeLine(PDPageContentStream contents, PDFont font, float fontSize, float x, float y,
			String text, RGBColor rgb) throws IOException {
		contents.beginText();
		contents.setFont(font, fontSize);
//		contents.setNonStrokingColor(109, 165, 253);
		contents.setNonStrokingColor(rgb.r, rgb.g, rgb.b);
		contents.newLineAtOffset(x, y);
		contents.showText(text);
		contents.endText();
	}
	
	public static void writeLine(PDPageContentStream contents, PDFont font, float fontSize, float x, float y,
			String text, Color color) throws IOException {
		contents.beginText();
		contents.setFont(font, fontSize);
		contents.setNonStrokingColor(color);
		contents.newLineAtOffset(x, y);
		contents.showText(text);
		contents.endText();
	}
	
	public static void writeLine2Color(PDPageContentStream contents, PDFont font, float fontSize, float x, float y,
			String text1, String answer, String text2, Color color, RGBColor rgb) throws IOException {
		contents.beginText();
		contents.setFont(font, fontSize);
		
		// text in white
		contents.setNonStrokingColor(color);
		contents.newLineAtOffset(x, y);
		contents.showText(text1);
		
		// answer in blue
//		contents.setNonStrokingColor(109, 165, 253);
		contents.setNonStrokingColor(rgb.r, rgb.g, rgb.b);
		contents.showText(answer);
		
		// text in white
		contents.setNonStrokingColor(color);
		contents.showText(text2);
		contents.endText();
	}
	
	public static void writeNumbers2Color(PDPageContentStream contents, PDFont font, PDFont font2, float fontSize, float x, float y,
			String text1, String answer, String text2, Color color, RGBColor rgb) throws IOException {
		contents.beginText();
		contents.setFont(font, fontSize);
		
		// text in white
		contents.setNonStrokingColor(color);
		contents.newLineAtOffset(x, y);
		contents.showText(text1);
		
		// answer in blue
//		contents.setNonStrokingColor(109, 165, 253);
		contents.setFont(font2, fontSize);
		contents.setNonStrokingColor(rgb.r, rgb.g, rgb.b);
		contents.showText(answer);
		
		// text in white
		contents.setFont(font, fontSize);
		contents.setNonStrokingColor(color);
		contents.showText(text2);
		contents.endText();
	}
}
