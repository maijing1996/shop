package com.shop.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 生成二维码
 * 2018年5月17日
 * @author oumu
 */
public class QRUtil {
	// 二维码中间的LOGO图片设置
	private static int IMAGE_WIDTH = 50; // 宽度
	private static int IMAGE_HEIGHT = 50; // 高度

	/**
	 * 添加在指定位置追加图片水印
	 * 
	 * @param waterImage
	 *            --水印文件地址
	 * @param img
	 *            -- 目标
	 * @param x
	 *            --x坐标
	 * @param y
	 *            --y坐标
	 */
	public final static BufferedImage addWaterImage(String waterImage, BufferedImage img, int x, int y) {
		try {
			// 目标文件
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			File file = new File(waterImage);
			BufferedImage image = ImageIO.read(file);
			Graphics graphics = image.createGraphics();
			graphics.drawImage(img, x, y, width, height, null);
			graphics.dispose();
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 添加在指定位置追加文字水印
	 * 
	 * @param text
	 *            --要添加的文字
	 * @param img
	 *            --目标
	 * @param fontName
	 *            --字体
	 * @param fontStyle
	 *            --样式
	 * @param color
	 *            --字体颜色
	 * @param fontSize
	 *            --字体大小
	 * @param x
	 *            --x坐标
	 * @param y
	 *            --y坐标
	 * @return
	 */
	public static BufferedImage addWaterText(String text, BufferedImage img, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y) {
		try {
			// 目标文件
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			Graphics graphicsg = img.createGraphics();
			graphicsg.drawImage(img, 0, 0, width, height, null);
			graphicsg.setColor(color);
			graphicsg.setFont(new Font(fontName, fontStyle, fontSize));
			graphicsg.drawString(text, x, y + fontSize);
			graphicsg.dispose();
			return img;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
     * 将二维码内容创建到Image流
     * @param content
     * @return
     * @throws Exception
     */
    public static BufferedImage createImage(String content, int width2, int height2, String imagePath) throws Exception {
    	Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    	hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    	hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    	hints.put(EncodeHintType.MARGIN, 1);
    	BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width2,
    			height2, hints);
    	int width = bitMatrix.getWidth();
    	int height = bitMatrix.getHeight();
    	//清晰排版
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	for(int x = 0; x < width; x++) {
    		for(int y = 0; y < height; y++) {
    			image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
    		}
    	}
        // 插入logo
        if(imagePath != null) {
        	insertImage(image, imagePath, width, height);
        }
        return image;
    }

    /**
     * 将logo插入到二维码中
     * @param source
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String srcImagePath, int width2, int height2)
    		throws Exception {
		Image src = ImageIO.read(new File(srcImagePath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        //logo 缩放
        if (width > IMAGE_WIDTH) {
            width = IMAGE_WIDTH;
        }
        if (height > IMAGE_HEIGHT) {
            height = IMAGE_HEIGHT;
        }
        Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        src = image;
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (width2 - width) / 2;
        int y = (height2 - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }
    
    /**
     * 将图片流转化成二维码
     * @param img
     * @param type
     * @param path
     * @throws IOException
     */
    public static void writeImage(BufferedImage img, String type, String path) throws IOException {
    	if(mkdir(path)) {
    		ImageIO.write(img, type, new File(path));
    	}
    }
    
    /**
     * 文件夹判断，不存在则创建文件夹
     * @param destPath 文件夹地址
     * @return 存在或创建返回：true
     */
    private static boolean mkdir(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (!file.isDirectory()) {
                return false;
            }
        } else {
            file.mkdir();
        }
        return true;
    }

	public static void main(String[] args) throws Exception {
		
		String content = "http://www.zjjponline.com/wechat/toQRCodeFilter?id=1";
		// 生成二维码
		BufferedImage img = QRUtil.createImage(content, 225, 225, "D:\\logo_image.jpg");
		// 追加二维码模板
		img = QRUtil.addWaterImage("D:\\background_image.jpg", img, 58, 98);

		ImageIO.write(img, "jpg", new File("D:\\BB123.jpg"));
	}

}