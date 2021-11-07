package com.kh.spring.common.util.file;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.common.code.Config;
import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandleableException;

public class FileUtil {
	
	
	//파일 DTO만들어주고(DB저장용) , 내가 설정한 경로에 파일 업로드 하는 메소드
	public FileDTO fileUpload(MultipartFile file) {
		
		FileDTO fileDTO = null;
		
		try {
			String uploadPath = createUploadPath(createSubPath());
			fileDTO = createFileDTO(file);
			File dest = new File(uploadPath + fileDTO.getRenameFileName());
			file.transferTo(dest); //파일 업로드하는 메소드
		} catch (IllegalStateException | IOException e) {
			throw new HandleableException(ErrorCode.FAILED_FILE_UPLOAD_ERROR,e);
		}
		
		return fileDTO;
	}
	
	private String createSubPath() {
		//2. 파일 업로드 날짜 기준으로 저장될 파일 경로 생성
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DAY_OF_MONTH);
		
		return year + "/" + month + "/" + date + "/";
	}
	
	private String createUploadPath(String subPath) {
		String uploadPath = Config.UPLOAD_PATH.DESC + subPath;
		
		File dir = new File(uploadPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		return uploadPath;
	}
	
	private FileDTO createFileDTO(MultipartFile filePart) {
		String originFileName = filePart.getOriginalFilename(); //4. File_INFO 테이블에 저장할 FileDTO 생성
		String renameFileName = UUID.randomUUID().toString(); //1. 서버에 저장될 유니크한 파일이름 생성
		
		if(originFileName.contains(".")) {
			renameFileName = renameFileName + originFileName.substring(originFileName.lastIndexOf("."));
		}

		String savePath = createSubPath();
		
		FileDTO fileDTO = new FileDTO();			
		fileDTO.setOriginFileName(originFileName);
		fileDTO.setRenameFileName(renameFileName);
		fileDTO.setSavePath(savePath);
		
		return fileDTO;
	}
	
	
	
	
	
	
	
	
	

}
