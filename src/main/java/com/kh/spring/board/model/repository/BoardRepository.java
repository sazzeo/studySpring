package com.kh.spring.board.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.board.model.dto.Board;
import com.kh.spring.common.util.file.FileDTO;

@Mapper
public interface BoardRepository {
	
	@Insert("insert into file_info(fl_idx , type_idx , origin_file_name , rename_file_name, save_path)"
			+ " values(sc_file_idx.nextval , sc_board_idx.currval , #{originFileName} , #{renameFileName} , #{savePath})")
	void insertFileInfo(FileDTO file);

	@Insert("insert into board(bd_idx , user_id , title , content)"
			+ " values(sc_board_idx.nextval , #{userId}, #{title} , #{content})")
	void insertBoard(Board board);
}
