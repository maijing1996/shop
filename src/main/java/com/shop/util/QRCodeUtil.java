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
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码编码
 * 2018年4月4日
 * @author oumu
 */
public class QRCodeUtil {
	
	public static String CHARSET = "UTF-8";//设置二维码编码格式
    public static String FORMAT_NAME = "JPG";//保存的二维码格式
    public static boolean COMPRESS = false;//是否压缩图片
    public static int QRCODE_SIZE = 800;//二维码尺寸
    
    public static String LOGO_FILE = null;//LOGO文件，设置了就会加入logo
    public static int LOGO_WIDTH = 200;//LOGO宽度
    public static int LOGO_HEIGHT = 200;//LOGO高度
    
    /**
     * 将二维码内容创建到Image流
     * @param content
     * @return
     * @throws Exception
     */
    public static BufferedImage createImage(String content) throws Exception {
    	Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    	hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    	hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
    	hints.put(EncodeHintType.MARGIN, 1);
    	BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE,
        		QRCODE_SIZE, hints);
    	int width = bitMatrix.getWidth();
    	int height = bitMatrix.getHeight();
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	for(int x = 0; x < width; x++) {
    		for(int y = 0; y < height; y++) {
    			image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
    		}
    	}
        // 插入logo
        if(LOGO_FILE != null && fileExists(LOGO_FILE)){
        	insertImage(image);
        }
        return image;
    }

    /**
     * 将logo插入到二维码中
     * @param source
     * @throws Exception
     */
    private static void insertImage(BufferedImage source) throws Exception {
        Image src = ImageIO.read(new File(LOGO_FILE));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (COMPRESS) { // 压缩LOGO
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     *  判断文件是否存在
     * @param file
     * @return
     */
    public static boolean fileExists(String filePath) {
    	File file = new File(filePath);
        if (file.exists()) {
        	return true;
        } else {
            return false;
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
     * @throws IOException 
	 */
    public final static BufferedImage addImage(String image, BufferedImage img, int x, int y) throws IOException {
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		File filebiao = new File(image);
		BufferedImage waterImage = ImageIO.read(filebiao);
		Graphics graphics = waterImage.createGraphics();
		graphics.drawImage(img, x, y, width, height, null);
		graphics.dispose();
		return waterImage;
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
			Graphics g = img.createGraphics();
			g.drawImage(img, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.drawString(text, x, y + fontSize);
			g.dispose();
			return img;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * 生成文件目标路径
	 * @param path
	 * @param fileName
	 * @return
	 */
	private static String getPath(String path, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(path);
        sb.append("/");
        sb.append(fileName);
        sb.append(".");
        sb.append(FORMAT_NAME.toLowerCase());
        return sb.toString();
	}

    /**
     * 生成二维码
     * @param content 二维码内容
     * @param path 二维码路径
     * @param fileName 二维码名字
     * @return
     * @throws Exception
     */
    public static String encode(String content, String path, String fileName) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content);
        if(mkdir(path)) {
        	String filePath = getPath(path, fileName);
        	File file = new File(filePath);
        	ImageIO.write(image, FORMAT_NAME, file);
        	return filePath;
        }
        return null;
    }
    
    /**
     * 生成二维码
     * @param content
     * @param output
     * @throws Exception
     */
    public static void encode(String content, OutputStream output) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content);
        ImageIO.write(image, FORMAT_NAME, output);
    }
}