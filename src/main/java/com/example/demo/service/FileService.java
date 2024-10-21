package com.example.demo.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.FileDetailMapper;
import com.example.demo.mapper.FileMasterMapper;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.util.FileUtil;
import com.example.demo.vo.FileDetailVO;
import com.example.demo.vo.FileMasterVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
	
	private final FileMasterMapper fileMasterMapper;
	private final FileDetailMapper fileDetailMapper;
	private final Path rootLocation;
	
	public FileService (FileMasterMapper fileMasterMapper, String uploadPath, FileDetailMapper fileDetailMapper) {
		this.fileMasterMapper = fileMasterMapper;
		this.rootLocation = Paths.get(uploadPath);
		this.fileDetailMapper = fileDetailMapper;
	}
	
	private Path loadPath(String fileName) {
		return rootLocation.resolve(fileName);
	}
	
	public Resource loadAsResource(String fileName) {
		try {
			// 파일이름 앞에 /로 시작되면 삭제
			if (fileName.toCharArray()[0] == '/') {
				fileName = fileName.substring(1);
			}
			log.info(fileName);
			Path file = rootLocation.resolve(rootLocation.toString() + fileName);
			log.info(file.toUri().toString());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				log.info(resource.toString());
				return resource;
			} else {
				throw new ResourceNotFoundException(fileName + "이 없습니다.");
			}
		} catch (Exception e) {
			throw new ResourceNotFoundException(fileName + "이 없습니다.");
		}
	}
	
	/**
	 * 첨부파일 마스터 ID 생성 후 리턴
	 * @param fileMasterVO
	 * @return
	 */
	public Long insertFileMaster(FileMasterVO fileMasterVO) {
		fileMasterMapper.insertFileMaster(fileMasterVO);
		return fileMasterVO.getFileMstId();
	}
	
	public ApiResponse saveFile(MultipartFile file, Long fileMstId, String fileDest) {
		// 용량, MIME, 파일명
		log.info(String.valueOf(file.getSize()));
		log.info(file.getContentType());
		log.info(file.getOriginalFilename());
		
		// 파일 존재 유무 체크
		if (file.isEmpty()) throw new BadRequestException("파일이 없습니다.");
		
		// fileMstId가 없을 경우 예외처리
		if (fileMstId.equals(0)) {
			throw new BadRequestException("파일 마스터 번호가 없습니다.");
		}
		
		String saveFileName = FileUtil.fileSave(rootLocation.toString(), file);	
		// 리턴 받은 파일 경로에서 /yyyy/mm/dd/ 만 별도로 분리한다.
		String[] saveFileArray = saveFileName.split("/");
		StringBuilder fileDirString = new StringBuilder();
		for (int i = 0; i < saveFileArray.length; i++) {
			if (i < saveFileArray.length - 1) {
				fileDirString.append(saveFileArray[i]).append(File.separator);
			}
		}
		// FILE_LOCATION 값 출력
		log.info(fileDirString.toString());
		// FILE_PATH
		// saveFileName 의 첫번째 레터가 / 이면 삭제
		if (saveFileName.toCharArray()[0] == '/') {
			saveFileName = saveFileName.substring(1);
		}
		String fullPathName = rootLocation.toString() + File.separator + saveFileName;
		log.info(fullPathName);
		
		FileDetailVO fileDetailVO = FileDetailVO.builder()
				.fileMiMe(file.getContentType())
				.fileDest(fileDest)
				.fileLocation(fileDirString.toString())
				.filePath(fullPathName)
				.fileSize(file.getSize())
				.fileExt(FileUtil.getExtension(saveFileName))
				.fileNumber(0L)
				.fileName(saveFileArray[saveFileArray.length - 1])
				.orgFileName(file.getOriginalFilename())
				.regID("regID")
				.updID("updID")
				.fileMstId(fileMstId)
				.build();
		
		log.info(fileDetailVO.toString());
		fileDetailMapper.insertDetailFile(fileDetailVO);
		
		log.info(String.valueOf(fileDetailVO.getFileDetailId()));
		
		return new ApiResponse(true, String.valueOf(fileDetailVO.getFileDetailId()));
		
	}
	
	/**
	 * 첨부파일 출력
	 * @param fileDetailVO
	 * @return
	 */
	public FileDetailVO selectFileByFileDetailId(FileDetailVO fileDetailVO) {
		return fileDetailMapper.selectFileByFileDetailId(fileDetailVO)
				.orElseThrow(() -> new ResourceNotFoundException("첨부파일", "파일명", fileDetailVO.getFileDetailId()));
		// super(String.format("%s에 해당하는 리소스를 찾을 수 없습니다. %s : '%s'", resourceName, fieldName, fieldValue));
		
	}
	
	/**
	 * 파일 삭제
	 * @param detailFileId
	 */
	public void fileDelete(Long detailFileId) {
		Optional<FileDetailVO> result =  fileDetailMapper.detailFileExist(detailFileId);
		// result가 있으면
		if (result.isPresent()) {
			if (result.get().getFilePath() != null) {
				// FILE_PATH 값이 있으면 실물 파일과 데이터 row를 삭제한다.
				File fileItem = new File(result.get().getFilePath());
				if (fileItem.exists()) {
					fileItem.delete();
					fileDetailMapper.deleteFileByFileDetailId(detailFileId);
				}
			}
		} else {
			throw new ResourceNotFoundException("해당 데이터를 찾을 수 없습니다.");
		}
	}
	
	/**
	 * FILE_DETAIL 테이블에서 FILE_MST_ID 기준으로 조회
	 * @param fileMstId
	 * @return
	 */
	public List<FileDetailVO> selectFileByMstId(Long fileMstId) {
		return fileDetailMapper.selectFileByMstId(fileMstId);
	}

}
