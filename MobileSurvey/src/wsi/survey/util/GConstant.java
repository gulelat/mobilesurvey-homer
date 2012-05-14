package wsi.survey.util;

import java.util.Random;

import android.graphics.Color;

public class GConstant {

	/** 适应不同屏幕的字体大小 */

	private static int deviceScreenWidth = 480;
	private static int deviceScreenHeight = 960;
	private static int titleFontSize = 26;

	public static void adjustFontSize(int screenWidth, int screenHeight) {
		deviceScreenWidth = screenWidth;
		deviceScreenHeight = screenHeight;

		if (deviceScreenWidth <= 240) { // 240X320 屏幕
			titleFontSize = 10;

		} else if (deviceScreenWidth <= 320) { // 320X480 屏幕
			titleFontSize = 14;

		} else if (deviceScreenWidth <= 480) { // 480X800 或 480X854 屏幕
			titleFontSize = 24;

		} else if (deviceScreenWidth <= 540) { // 540X960 屏幕
			titleFontSize = 26;

		} else if (deviceScreenWidth <= 800) { // 800X1280 屏幕
			titleFontSize = 30;

		} else { // 大于 800X1280
			titleFontSize = 32;
		}
	}

	
	/** 枚举获取系统颜色值 */
	private static int[] sysColors = new int[] { Color.RED, Color.MAGENTA,
			Color.CYAN, Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE,
			Color.LTGRAY, Color.GRAY, Color.DKGRAY};
	
	/** 获取不同颜色值 */
	public static int[] getColors(int colorsLen) {
		if (colorsLen <= 0) {
			return null;
		}

		if (colorsLen < sysColors.length) {		// 颜色值小于sysColors系统默认值个数
			return sysColors;
		} else {
			return generateColor(colorsLen);	// 颜色值大于系统sysColors系统默认值个数，则自动随机生成
		}
	}

	/** 随机生成颜色数组 */
	private static int[] generateColor(int colorsLen) {
		int[] myColors = new int[colorsLen];

		for (int i = 0; i < colorsLen; i++) {
			myColors[i] = Color.parseColor(sandColor2());

			System.out.println("color " + i + " = " + myColors[i]);
		}
		return myColors;
	}

	/** 随机生成颜色 */
	private static int sandColor() {
		int r, g, b;

		Random random = new Random();
		r = random.nextInt(256);
		g = random.nextInt(256);
		b = random.nextInt(256);
		System.out.println("r = " + r + "; g = " + g + "; b = " + b);

		return Color.argb(0xff, r, g, b);
	}

	/** 随机生成颜色 */
	private static String sandColor2() {
		char[] array = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };

		StringBuffer sb = new StringBuffer("#");
		Random random = new Random();

		for (int i = 0; i < 6; i++) {
			sb.append(array[random.nextInt(16)]);
		}

		System.out.println("sb = " + sb.toString());
		return sb.toString();
	}

}
