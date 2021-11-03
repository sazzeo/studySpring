package com.kh.spring.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.spring.member.model.dto.Member;

@Mapper
public interface MybatisRepository {

	//(mybatisTest2와 mybatisMapper2랑 연관되는 클래스임)
//interface로 구현한 얘는 같은 이름의 namespace를 가지고 있는 mapper와 밀접하게 연관된다
//mapper에 있는 메소드들은 전부 여기에 선언되어 있어야하고
//쿼리는 매퍼에 작성하든, 여기에 어노테이션으로 작성하든 둘중 하나만 되어야 한다.
	
	@Select("select password from member where user_id = #{userId}")
	String selectPasswordByUserId(String userId);
	
	
	@Select("select * from member where user_id = #{userId}")
	Member selectMemberByUserId(String userId);


	@Select("select * from member inner join rent_master using(user_id) where user_id = #{userId}")
	List<Map<String, Object>> selectRentAndMemberByUserId(String userId);


	List<Map<String, Object>> selectRentAndMemberByUserId2(String userId);


	void insertWithDto(Member member);


	void insertWithMap(Map<String, Object> map);


	void deleteRentMaster(String userId);


	void updateReturn(String string);

	void procedure(String string);


	void proceduerUseTypeHandler(Map<String, Object> of);


	void test01(Map<String, String> of);

	
	@Update("update rent_book set extension_cnt=0 where extension_cnt>=2")
	void test02();

	@Delete("delete from member where to_char(reg_date,'YYMM') < #{date}")
	void test03(String date);


	List<Map<String, Object>> test04(String top);


	void dynamicIf(Map<String, String> of);


	void dynamicChoose(Map<String, String> of);


	void dynamicForEachAndWhereTag(Map<String, Serializable> of);


	void dynamicForEachAndWhereTag2(Map<String, Serializable> of);


	void dynamicForEachWithList(List<String> of);


	void insertTemplate(Map<String, Object> of);


	void dynamicSet(Member member);
	
	
	
	


}
