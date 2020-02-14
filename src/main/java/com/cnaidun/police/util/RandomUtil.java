
package com.cnaidun.police.util;

import java.util.Random;

/**
 * 获取随机数util
 * @author dongyin
 */
public class RandomUtil {
	/**
	 * 获取2个数之间的随机整数
	 * @param min
	 * @param max
	 * @return
	 */
	public static String getRandomNumBteween(int min, int max) {
		Random random = new Random();
		return String.valueOf(random.nextInt((max - min) + 1) + min);
	}

	/**
	 * 六位随机数
	 * @return
	 */
	public static String getRandomSixNum() {
		int rannum = (int)(Math.random()*(999999-100000+1))+100000;
		return ""+rannum;
	}
}

