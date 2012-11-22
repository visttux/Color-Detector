package org.ia.practiques;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * This class provides a set of utilities for clusters and colors
 * 
 * @author Victor Martinez
 * @see Rgb
 */
public class Utils {

	/**
	 * This method return the distance between two RGB points
	 * 
	 * @param c1
	 *            : RGB point one
	 * @param c2
	 *            : RGB point two
	 * @return Integer with distance
	 */
	public int getDistance(Rgb c1, Rgb c2) {
		int dRed = c1.getRed() - c2.getRed();
		int dBlue = c1.getBlue() - c2.getBlue();
		int dGreen = c1.getGreen() - c2.getGreen();

		return ((dRed * dRed) + (dBlue * dBlue) + (dGreen * dGreen));
	}

	/**
	 * This method convert an RGB point to its respective CMYK 
	 * 
	 * @param rgbColor: RGB point
	 * @return CMYK object
	 */
	public Cmyk rgbToCmyk(Rgb rgbColor) {
		Cmyk cmykColor = null;
		int black = Math.min(
				Math.min(255 - rgbColor.getRed(), 255 - rgbColor.getGreen()),
				255 - rgbColor.getBlue());

		if (black == 255) {
			cmykColor = new Cmyk(255 - rgbColor.getRed(),
					255 - rgbColor.getGreen(), 255 - rgbColor.getBlue(), black);
		} else {
			cmykColor = new Cmyk(
					(int) ((255 - rgbColor.getRed() - black) / (255 - black)),
					(int) ((255 - rgbColor.getGreen() - black) / (255 - black)),
					(int) ((255 - rgbColor.getBlue() - black) / (255 - black)),
					black);
		}

		return (cmykColor);
	}
	
	/**
	 * This method convert an CMYK point to its respective RGB
	 * 
	 * @param cmykColor : CMYK point
	 * @return RGB object
	 */
	public Rgb cmykToRgb(Cmyk cmykColor) {
		Rgb rgbColor = null;

		if (cmykColor.getBlack() == 255) {
			rgbColor = new Rgb(255 - cmykColor.getCyan(),
					255 - cmykColor.getMagenta(), 255 - cmykColor.getYellow());
		} else {
			rgbColor = new Rgb((int) ((255 - cmykColor.getCyan())
					* (255 - cmykColor.getBlack()) / 255),
					(int) ((255 - cmykColor.getMagenta())
							* (255 - cmykColor.getBlack()) / 255),
					(int) ((255 - cmykColor.getYellow())
							* (255 - cmykColor.getBlack()) / 255));
		}

		return (rgbColor);
	}



	/**
	 * This method returns the color at the specific pixel on a bitmap
	 * 
	 * @param bImg: Bitmap
	 * @param widthRand: X component of the pixel 
	 * @param heightRand: Y componen of the pixel
	 * @return RGB point
	 * 
	 * @see Rgb
	 * @see Bitmap
	 */
	
	public Rgb getColor(Bitmap bImg, int widthRand, int heightRand) {

		int pixel = bImg.getPixel(widthRand, heightRand);
		Rgb rgb = new Rgb(Color.red(pixel), Color.green(pixel),
				Color.blue(pixel));

		return rgb;
	}

}
