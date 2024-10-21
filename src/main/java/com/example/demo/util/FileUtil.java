package com.example.demo.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {
	
	/**
	 * 파일 유형 파악
	 * @param file
	 * @return
	 */
	public static boolean fileMimeType(InputStream file) {
		// 업로드 가능한 파일 유형 목록
		List<String> typeList = Arrays.asList("image/jpg", "image/png", "image/jpeg", "image/gif", "application/x-tika-ooxml");
		try {
			// tika를 이용해서 실물 파일의 유형을 체크한다.
			String mimeType = new Tika().detect(file);
			
			log.info("mimeType : {}", mimeType);
			
			// typeList를 loop 돌려서 mimeType과 일치하는게 있으면 return true 아니면 false
			return typeList.stream().anyMatch(item -> item.equalsIgnoreCase(mimeType));
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 파라미터로 받은 파일을 드라이브에 임의의 문자열로 리네임해서 저장
	 * @param uploadPath
	 * @param file
	 * @return
	 */
	public static String fileSave(String uploadPath, MultipartFile file) {
		// 1. MIME 체크해서 업로드 가능한 형식의 파일인지 확인
		try {
			boolean isMime = fileMimeType(file.getInputStream());
			if (isMime) {
				
				// uploadPath로 받은 경로를 만든다.
				File uploadFileDir = new File(uploadPath);
				if (!uploadFileDir.exists()) {
					uploadFileDir.mkdirs();
				}
				
				// 파일 저장시 중복되지 않게 하기 위해서 리네임 후 저장한다.
				String genId = UUID.randomUUID().toString();
				
				log.info(genId);
				
				String orgFileName = file.getOriginalFilename();
				String fileExt = getExtension(orgFileName);
				String saveFileName = genId + "." + fileExt;
				log.info(saveFileName);
				// 경로 만들기
				// 드라이브/저장기본폴더/년/월/일/파일명
				String savePath = calcPath(uploadPath);
				// 해당 경로에 파일 복사
				File target = new File(uploadPath + savePath, saveFileName);
				FileCopyUtils.copy(file.getBytes(), target);
				// 경로 + 파일명을 리턴 
				return makeFilePath(uploadPath, savePath, saveFileName);
			}
			return "";
		} catch (Exception e) {
			throw new BadRequestException("업로드가 불가능한 파일 형식입니다.");
		}
	}
	
	/**
	 * 파일 복사 완료 후 리턴할 전체 문자열
	 * @param uploadPath
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String makeFilePath(String uploadPath, String path, String fileName) {
		String filePath = uploadPath + path + File.separator + fileName;
		return filePath.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	/**
	 * 연/월/일 패스를 만들어서 폴더 생성 및 패스 리턴
	 * @param uploadPath
	 * @return
	 */
	public static String calcPath(String uploadPath) {
		Calendar calendar = Calendar.getInstance();
		log.info(calendar.toString());
		// 경로/연도
		// File.separator 를 사용하면 OS에 따라 / 또는 \가 자동으로 찍힌다.
		String yearPath = File.separator + String.valueOf(calendar.get(Calendar.YEAR));
		log.info(yearPath);
		// 연 월 출력 2024\9
		// 1 11 12 13..... 19 2 20
		// 01 02 ..... 10 ... 19 20
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1);
		log.info(monthPath);
		// 연 월 일 출력
		String datePath = monthPath  + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));
		log.info(datePath);
		// 폴더 생성
		makeDirectories(uploadPath, yearPath, monthPath, datePath);
		return datePath;
	}
	
	/**
	 * 실제 경로에 폴더 생성
	 * @param uploadPath
	 * @param paths
	 */
	public static void makeDirectories(String uploadPath, String... paths) {
		// 경로에 폴더가 있으면 return
		if (new File(paths[paths.length - 1]).exists()) {
			return;
		}
		// paths를 loop 돌려서 폴더 생성
		// 업로드경로/2024
		// 업로드경로/2024/10
		// 업로드경로/2024/10/15
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);
			if (!dirPath.exists()) dirPath.mkdir();
		}
	}
	
	/**
	 * 파일에서 확장자만 리턴
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		int dotPosition = fileName.lastIndexOf('.');
		if (-1 != dotPosition && (fileName.length() - 1 > dotPosition)) {
			return fileName.substring(dotPosition + 1);
		} else {
			return "";
		}
	}
	
	
}
