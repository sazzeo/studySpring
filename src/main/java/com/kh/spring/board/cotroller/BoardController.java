package com.kh.spring.board.cotroller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.member.model.dto.Member;

@Controller
@RequestMapping("board")
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BoardService boardService; //impl이아닌 BoardService로 받음(proxy패터는 인터페이스로 받음)
	
	@GetMapping("board-form")
	public void boardForm() {};
	
//	
//	//RequestParam에 MultipartFile을 설정해두면 컨트롤러가 알아서 넣어줌
//	@PostMapping("upload")
//	public String uploadBoard(
//			Board board
//			,@RequestParam List<MultipartFile> files
//			,@SessionAttribute("authentication") Member member) {
//		for(MultipartFile multipartFile : files) {
//			
//			//file객체는 단순 파일 생성만 해준다. 내용을 넣으려면 FileOutputStream객체를 사용해야한다.
//			File file = new File(Config.UPLOAD_PATH.DESC+"/"+ multipartFile.getOriginalFilename());
//			try {
//				multipartFile.transferTo(file);
//				//멀티파트 파일 내용 넣기
//			} catch (IllegalStateException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			logger.debug(multipartFile.toString());
//			logger.debug(board.toString());
//			
//			
//		}
//		
//		
//		
//		return "redirect:/";
//		
//	};
//	
	
	
	//RequestParam에 MultipartFile을 설정해두면 컨트롤러가 알아서 넣어줌
	@PostMapping("upload")
	public String uploadBoard(
			Board board
			,@RequestParam List<MultipartFile> files
			,@SessionAttribute("authentication") Member member) {
		logger.debug("파일 사이즈: "+files.size());
		logger.debug("파일 0번 : "+files.get(0));
		logger.debug("현재 비어있니? :" +  files.get(0).isEmpty());
		
		board.setUserId(member.getUserId());
		boardService.insertBoard(files, board);
		
		return "redirect:/";
		
	};
	
	
	@GetMapping("board-detail")
	public void boardDetail(Model model, String bdIdx){
		
		
		Map<String, Object> commadMap= boardService.selectBoardByIdx(bdIdx);
		model.addAllAttributes(commadMap); //현재 commandMap에있는 키값으로 속성 일일히 담아서 보내줌
	
		
	};
	
	
	
	

}
