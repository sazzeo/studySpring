package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.repository.BoardRepository;
import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandleableException;
import com.kh.spring.common.util.file.FileDTO;
import com.kh.spring.common.util.file.FileUtil;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;

	// 보드와 파일을 한번에 저장한다
	//@Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class )
	//트랜젝션 관리해주는 어노테이션 : 중간 에러나면 롤백도 알아서해줌
	public void insertBoard(List<MultipartFile> multiparts, Board board) {

		boardRepository.insertBoard(board);
		FileUtil fileUtil = new FileUtil();
		for (MultipartFile multipartFile : multiparts) {
			
			if(!multipartFile.isEmpty()) {
			FileDTO file = fileUtil.fileUpload(multipartFile);
			boardRepository.insertFileInfo(file);
			}
		}
		
	}

	
	
	@Override
	public Map<String, Object> selectBoardByIdx(String bdIdx) {

		Board board = boardRepository.selectBoardByIdx(bdIdx);
		List<FileDTO> files = boardRepository.selectFilesByBdIdx(bdIdx);
		
		
		return Map.of("board" , board , "files" , files);
	}
	
	

}
