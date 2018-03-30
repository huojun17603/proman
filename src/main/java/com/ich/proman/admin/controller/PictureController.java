package com.ich.proman.admin.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ich.core.base.TimeUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ich.core.base.JsonUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.file.FileUtil;
import com.ich.core.file.ImageCut;
import com.ich.core.file.MimeType;
import com.ich.core.http.controller.CoreController;
import com.ich.core.http.other.IPUtils;
import com.ich.core.plug.jedis.JedisClient;
import sun.misc.BASE64Decoder;

@Controller
public class PictureController extends CoreController{

	//文件存储地址，SpringBoot是Jar格式无法像War一样保存上传文件
	@Value("${UPLOAD_ROOT}")
	private String UPLOAD_ROOT;
	@Value("${SERVER_HTTP}")
	private String SERVER_HTTP;

	private static String FILE_ROOT = "file";

	private static String PICTURE_ROOT = "picture";

	private final ResourceLoader resourceLoader;

	@Autowired
	public PictureController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/** 文件上传 */
	@RequestMapping(value="img/upload", method=RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> imgUpload(HttpServletRequest request,HttpServletResponse response) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<Map<String,Object>> list = new ArrayList<>();
		String yyyyMMdd = TimeUtil.format("yyyyMMdd");
		String filePath = FileUtil.createDirs(UPLOAD_ROOT  + File.separator + PICTURE_ROOT + File.separator + yyyyMMdd);
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile multipartFile = entity.getValue();
			String fileName = multipartFile.getOriginalFilename();//文件名称
			String ext = FileUtil.getFileExt(fileName).substring(1);
			String randomname = FileUtil.createRandomFileName(fileName);
			File file = new File(filePath + File.separator + randomname.toLowerCase());
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			Map<String,Object> picture = new HashMap<>();
			BufferedImage image = ImageIO.read(file);//配置图片信息
			picture.put("url",SERVER_HTTP + "/" + PICTURE_ROOT +"/" + yyyyMMdd +"/"+ randomname);
			picture.put("randomname",randomname);
			picture.put("ext",ext);
			list.add(picture);
		}
		return getSuccessMap(list);
	}

	/** 文件上传 */
	@RequestMapping(value="file/upload", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> fileUpload(HttpServletRequest request,HttpServletResponse response) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<Map<String,Object>> list = new ArrayList<>();
		String yyyyMMdd = TimeUtil.format("yyyyMMdd");
		String filePath = FileUtil.createDirs(UPLOAD_ROOT  + File.separator + FILE_ROOT + File.separator + yyyyMMdd);
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile multipartFile = entity.getValue();
			String fileName = multipartFile.getOriginalFilename();//文件名称
			String ext = FileUtil.getFileExt(fileName).substring(1);
			String randomname = FileUtil.createRandomFileName(fileName);
			File file = new File(filePath + File.separator + randomname.toLowerCase());
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			Map<String,Object> picture = new HashMap<>();
			picture.put("url",SERVER_HTTP +"/" + FILE_ROOT +"/" + yyyyMMdd +"/"+ randomname);
			picture.put("oldname",fileName);
			picture.put("randomname",randomname);
			picture.put("ext",ext);
			list.add(picture);
		}
		return getSuccessMap(list);
	}
	@RequestMapping(method = RequestMethod.GET, value = "picture/{times}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<?> getPicture(@PathVariable String times,@PathVariable String filename) {
		try {
			return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(UPLOAD_ROOT  + File.separator + PICTURE_ROOT + File.separator + times, filename).toString()));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	//显示图片的方法关键 匹配路径像 localhost:8080/b7c76eb3-5a67-4d41-ae5c-1642af3f8746.png
	//下载-显示统一方案
	@RequestMapping(method = RequestMethod.GET, value = "file/{times}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<?> getFile(String downloadName,@PathVariable String times,@PathVariable String filename) {
		try {
			File file = new File(UPLOAD_ROOT  + File.separator + FILE_ROOT  + File.separator + times + File.separator + filename);
			HttpHeaders headers = new HttpHeaders();
			//下载显示的文件名，解决中文名称乱码问题
			String downloadFielName = new String(downloadName.getBytes("UTF-8"), "iso-8859-1");
			//通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName);
			//application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
					headers, HttpStatus.CREATED);
		}catch (Exception e) {
				return ResponseEntity.notFound().build();
		}
	}


}
