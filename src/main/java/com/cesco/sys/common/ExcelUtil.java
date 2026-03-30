package com.cesco.sys.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ExcelUtil {
	
    public static BufferedImage resizeImage(URL url) {

        // 이미지 리사이즈
        // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
        // Image.SCALE_FAST    : 이미지 부드러움보다 속도 우선
        // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
        // Image.SCALE_SMOOTH  : 속도보다 이미지 부드러움을 우선
        // Image.SCALE_AREA_AVERAGING  : 평균 알고리즘 사용
        
	    try {
	        Image image = ImageIO.read(url);
            Image resizeImage = image.getScaledInstance(200, 112, Image.SCALE_SMOOTH);
            // 새 이미지  
            BufferedImage newImage = new BufferedImage(200, 112, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(resizeImage, 0, 0, null);
            g.dispose();
            
            return newImage;
        } catch (IOException e) {
//            throw new NullPointerException("ExcelUtil.resizeImage:This url is not image.");
        	return null;
        }
	    
	}
	
	public static int charByWidth(String str, int size) {
		return size == 0 ? str.length() * 500 : str.length() * size;
	}
}
