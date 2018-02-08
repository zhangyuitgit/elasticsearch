package cn.com.skyvis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.poi.hwpf.extractor.WordExtractor;

/**
 * Doc文档处理
 * 
 * @author 张宇 Description: 2018年2月6日上午11:19:53
 */
public class DocUtil {

	/**
	 * Blob类型导出 
	 *         格式doc
	 * @param path
	 *            导出地址
	 * @param blob
	 *            Blob对象
	 */
	public static void blobExportDoc(String path, Blob blob) {
		if (path != null && blob != null) {
			InputStream input = null;
			File file = null;
			FileOutputStream fileOut = null;
			try {
				input = blob.getBinaryStream();
				int length = (int) blob.length();
				byte[] data = new byte[length];
				input.read(data);
				input.close();
				file = new File(path);
				fileOut = new FileOutputStream(file);
				fileOut.write(data);
				fileOut.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 读取doc文档
	 * @param path
	 *            文档地址
	 * @return
	 */
	public static String readDoc(String path) {
		String text = null;
		try {
			FileInputStream stream = new FileInputStream(path);
			WordExtractor word = new WordExtractor(stream);
			text = word.getText();
			// 去掉word文档中的多个换行
			text = text.replaceAll("\\s*|\t|\r|\n", "");
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;

	}

}
