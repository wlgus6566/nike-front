package com.nike.dnp.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Image Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description ImageUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

public class ImageUtil {

	/**
	 * 이미지 리사이즈
	 * 
	 * @description 가로이미지 세로 변환, jpg 파일로 재생성
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String AutoImageResize(String filePath) {
		File imageFile = new File(filePath);
		int orientation = 1;

		try {
			BufferedImage originalImage = ImageIO.read(imageFile);
			Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
			final ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			JpegDirectory jpegDirectory = (JpegDirectory) metadata.getFirstDirectoryOfType(JpegDirectory.class);
			AffineTransform affineTransform = new AffineTransform();

			orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);

			int newWidth = 0;
			int newHeight = 0;
			int width = jpegDirectory.getImageWidth();
			int height = jpegDirectory.getImageHeight();

			switch (orientation) {
			case 1:
				newWidth = width;
				newHeight = height;
				break;
			case 2: // Flip X
				affineTransform.scale(-1.0, 1.0);
				affineTransform.translate(-width, 0);
				newWidth = width;
				newHeight = height;
				break;
			case 3: // PI rotation
				affineTransform.translate(width, height);
				affineTransform.rotate(Math.PI);
				newWidth = width;
				newHeight = height;
				break;
			case 4: // Flip Y
				affineTransform.scale(1.0, -1.0);
				affineTransform.translate(0, -height);
				newWidth = width;
				newHeight = height;
				break;
			case 5: // - PI/2 and Flip X
				affineTransform.rotate(-Math.PI / 2);
				affineTransform.scale(-1.0, 1.0);
				newWidth = height;
				newHeight = width;
				break;
			case 6: // -PI/2 and -width
				affineTransform.translate(height, 0);
				affineTransform.rotate(Math.PI / 2);
				newWidth = height;
				newHeight = width;
				break;
			case 7: // PI/2 and Flip
				affineTransform.scale(-1.0, 1.0);
				affineTransform.translate(-height, 0);
				affineTransform.translate(0, width);
				affineTransform.rotate(3 * Math.PI / 2);
				newWidth = height;
				newHeight = width;
				break;
			case 8: // PI / 2
				affineTransform.translate(0, width);
				affineTransform.rotate(3 * Math.PI / 2);
				newWidth = height;
				newHeight = width;
				break;
			default:
				break;
			}

			AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);

			// System.out.println("---------------------------------------------------");
			// System.out.println(orientation);
			// System.out.println(width);
			// System.out.println(height);
			// System.out.println(newWidth);
			// System.out.println(newHeight);
			// System.out.println("---------------------------------------------------");

			newWidth = (newWidth == 0) ? width : newWidth;
			newHeight = (newHeight == 0) ? height : newHeight;

			BufferedImage destinationImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
			destinationImage = affineTransformOp.filter(originalImage, destinationImage);

			ImageIO.write(destinationImage, "jpg", new File(filePath));

			ImageIcon ic = resizeImage(filePath, newWidth, newHeight);
			Image i = ic.getImage();

			BufferedImage bi = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bi.createGraphics();
			g2.drawImage(i, 0, 0, null);
			g2.dispose();

			ImageIO.write(bi, "jpg", new File(filePath));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return filePath;
	}

	/**
	 * 썸네일 이미지 생성
	 * 
	 * @param filePath
	 * @param newPath
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static String createThumImage(String filePath, String newPath, int width, int height) throws Exception {
		ImageIcon ic = resizeImage(filePath, width, height);
		Image i = ic.getImage();

		BufferedImage bi = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(i, 0, 0, null);
		g2.dispose();

		ImageIO.write(bi, "jpg", new File(newPath));

		return newPath;
	}

	/**
	 * 이미지 리사이즈
	 * 
	 * @param fileName
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static ImageIcon resizeImage(String fileName, int maxWidth, int maxHeight) {
		String data = fileName;
		BufferedImage src, dest;
		ImageIcon icon;

		try {
			src = ImageIO.read(new File(data));

			int width = src.getWidth();
			int height = src.getHeight();

			if (width > maxWidth) {
				float widthRatio = maxWidth / (float) width;
				width = (int) (width * widthRatio);
				height = (int) (height * widthRatio);
			}
			if (height > maxHeight) {
				float heightRatio = maxHeight / (float) height;
				width = (int) (width * heightRatio);
				height = (int) (height * heightRatio);
			}

			dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = dest.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance((double) width / src.getWidth(), (double) height / src.getHeight());
			g.drawRenderedImage(src, at);

			icon = new ImageIcon(dest);
			return icon;
		} catch (Exception e) {
			System.out.println("This image can not be resized. Please check the path and type of file.");
			return null;
		}
	}
}
