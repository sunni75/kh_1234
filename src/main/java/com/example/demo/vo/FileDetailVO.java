package com.example.demo.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDetailVO {

	private Long fileDetailId;
	private Long fileMstId;
	private String fileDest;
	private Long fileNumber;
	private String fileLocation;
	private String filePath;
	private String fileName;
	private String orgFileName;
	private String fileExt;
	private String fileMiMe;
	private Long fileSize;
	private LocalDateTime regDate;
	private String regID;
	private LocalDateTime updDate;
	private String updID;
	
	@Builder
	public FileDetailVO(Long fileDetailId, Long fileMstId, String fileDest, Long fileNumber, String fileLocation,
			String filePath, String fileName, String orgFileName, String fileExt, String fileMiMe, Long fileSize,
			LocalDateTime regDate, String regID, LocalDateTime updDate, String updID) {
		this.fileDetailId = fileDetailId;
		this.fileMstId = fileMstId;
		this.fileDest = fileDest;
		this.fileNumber = fileNumber;
		this.fileLocation = fileLocation;
		this.filePath = filePath;
		this.fileName = fileName;
		this.orgFileName = orgFileName;
		this.fileExt = fileExt;
		this.fileMiMe = fileMiMe;
		this.fileSize = fileSize;
		this.regDate = regDate;
		this.regID = regID;
		this.updDate = updDate;
		this.updID = updID;
	}
	
	
	
}
