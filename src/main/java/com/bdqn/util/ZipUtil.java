package com.bdqn.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author
 * @Description: 打包成zip工具类
 * @date 2019年9月12日 上午10:16:41
 * @version V1.0
 */
public class ZipUtil {
	/**
	 * 执行压缩文件
	 * 
	 * @param pathArr
	 *            文件路径数组
	 * @param nameArr
	 *            文件名数组
	 * @param response
	 *            响应对象
	 */
	public static void downloadZip(String path, String[] nameArr, HttpServletResponse response) {
		ZipOutputStream zipos = null;
		DataOutputStream dos = null;
		try {
			// 设置压缩流：直接写入response，实现边压缩边下载
			zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
			zipos.setMethod(ZipOutputStream.DEFLATED);// 设置压缩方法

			// 循环将文件写入压缩流
			for (int i = 0; i < nameArr.length; i++) {
				File file = new File(path + File.separator + nameArr[i]);
				if (file.exists()) { // 判断文件是否存在
					// 添加ZipEntry，并将其写入文件流
					// 目的是为防止下载的文件时有重名导致下载失败
					zipos.putNextEntry(new ZipEntry(nameArr[i]));
					dos = new DataOutputStream(zipos);
					InputStream in = new FileInputStream(file);
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = in.read(b)) != -1) {
						dos.write(b, 0, len);
					}
					in.close();
					zipos.closeEntry();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipos != null)
					zipos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 设置响应为下载格式化，定义下载文件名
	 * 
	 * @param fileName
	 *            文件名
	 * @param request
	 * @param response
	 */
	public static void setZipDownload(String fileName, HttpServletRequest request, HttpServletResponse response) {
		// 获取客户端浏览器版本号，协议
		String header = request.getHeader("USER-AGENT");
		try {
			// 针对IE或者IE内核的浏览器
			if (header.contains("MSIE") || header.contains("Trident")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				// 非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 高速浏览器已下载的形式
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".zip");
	}
}