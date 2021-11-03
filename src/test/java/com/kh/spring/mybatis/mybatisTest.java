package com.kh.spring.mybatis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kh.spring.member.model.dto.Member;

//가상으로 만들어지는 web.xml을 사용해 테스트 환경을 구축
@WebAppConfiguration 
//JUnit을 실행할 방법. 테스트 때 사용할 가상의  apllicationContext를 생성하고 관리
@RunWith(SpringJUnit4ClassRunner.class)
//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정, 실제 spring bean의 위치를 지정함
//spring폴더 아래 //모든 폴더 아래 -context.xml로 끝나는 모든 파일 => root랑 servlet xml
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"}) 
public class mybatisTest {
	//Junit annotation
	//@Before : 테스트 전에 실행될 메서드
	//@Test : 테스트 메서드
	//@After : 테스트 이후에 실행될 메서드
	
	//SQLSessionTemplete의 주요 메서드
	//selectOne : 단일행 select문 실행
	//selectList : 다중행 select문ㅅ ㅣㄹ행
	//insert :  메서드의 결과값은 쿼리에 의해 영향을 받은 row수
	//update :  메서드의 결과값은 쿼리에 의해 영향을 받은 row수
	//delete :  메서드의 결과값은 쿼리에 의해 영향을 받은 row수
	// ** procedure 호출은 dml(select insert update delete) 쿼리 메서드 중에서 선택
	
	@Autowired
	private SqlSessionTemplate session;
	private final String NAMESPACE = "com.kh.spring.mybatis.MybatisMapper.";
	private String userId = "DEV";
	
	@Test
	public void selectOneTest() {
		String password = session.selectOne(NAMESPACE+"selectPasswordByUserId" , userId);
		System.out.println(password);
		
	}
	
	@Test 
	public void selectOneAsDto() {
		
		Member member =  session.selectOne(NAMESPACE+"selectMemberByUserId" , userId);
		System.out.println("멤버는" + member);
	}
	
	@Test
	public void selectListAsMap() {
		
		//자동으로 조인된 거 List안 Map에 매칭해서 담아줌
		java.util.List<Map<String, Object>> res = session.selectList(NAMESPACE+"selectRentAndMemberByUserId" , userId);
		System.out.println(res);
		
	}
	
	
	//내 ResultMap으로 변환한 맵타입
	@Test
	public void selectListAsMap2() {
		
		java.util.List<Map<String, Object>> res = session.selectList(NAMESPACE+"selectRentAndMemberByUserId2" , userId);
		System.out.println(res);
		
		
	}
	
	@Test
	public void insertWithDto() {
		
		//매개변수가 여러개일때는 dto에 담아서 넘긴다.
		Member member = new Member();
		member.setUserId("mybatis");
		member.setPassword("1234");
		member.setEmail("mybatis@naver.com");
		member.setTell("01088889999");
		
		session.insert(NAMESPACE+"insertWithDto" , member);

		
	}
	

	
	
	@Test
	public void insertWithMap() {
		
		Member member = new Member();
		member.setUserId("mybatis");
	
		Map<String, Object> map = new HashMap<>();
		map.put("member", member);
		map.put("title", "마이바티스의 모든 것 1권");
		map.put("rentBookCnt",2);
		
		session.insert(NAMESPACE+"insertWithMap" , map);
		
		
	}
	
	@Test
	public void deleteRentMaster() {
		
		userId = "mybatis";
		session.delete(NAMESPACE+"deleteRentMaster" , userId);
		
	}
	
	
	@Test
	public void updateReturn() {
		
		session.update(NAMESPACE+"updateReturn" , "mybatis");
		
	}
	
	//프로시저 호출--------------------------------------------
	@Test
	public void procedure() {
		session.update(NAMESPACE+"procedure" , "100043");
		
	}
	
	@Test
	public void proceduerUseTypeHandler() {
		List<String> list = new ArrayList<>();
		list.add("100001");
		list.add("100002");
		
		session.insert(NAMESPACE+"proceduerUseTypeHandler", 
				Map.of("userId" , "DEV" , "title" , "유미의 세포들 외 1 권" , 
						"rentBookCnt" , 2 , "bkIdxs" , list) );
		
	}
	
	
	//문제--------------------------------------------
	
	 //1. 도서명 : 쿠키와 세션, 
	   //    작가   : 김영아
	   //    도서번호 : 시퀀스 사용
	   //   인 도서를 BOOK 테이블에 저장하기
	   //   메서드 이름 : test01
	   
	   
	   //2. 연장횟수가 2회 이상인 모든 대출도서 정보를
	   //    연장횟수 0회로 초기화 해주세요.
	   //  메서드 이름 : test02
	   
	   //3. 2021년 9월 이전에 가입된 회원정보를 삭제
	   //  메서드 이름 : test03
	   
	   //4. 대출 횟수가 가장 많은 3권의 도서를 조회
	   //  메서드 이름 : test04
	//#{isbn}, #{title} , #{author}
	
	
	@Test 
	public void test01() {
		Map<String, Object> book = new HashMap<>();
		book.put("isbn", "123123");
		book.put("title", "쿠키와 세션");
		book.put("author", "김영아");
		
		//Map.of()  => 11버전에서 추가된 메소드
		session.insert(NAMESPACE+"test01" , Map.of("isbn","12341234","title", "쿠키와 세션" , "author","김영아"));
	}
	
	@Test
	public void test02() {
		session.update(NAMESPACE+"test02");
	}
	
	@Test
	public void test03() {
		String date = "2107";
		session.delete(NAMESPACE+"test03" , date );
	}
	
	@Test
	public void test04() {
		
		String top = "3";
		List<Map<String, Object>> map = session.selectList(NAMESPACE+"test04" , top);
		System.out.println(map);
	}
	
	
	//--동적쿼리----------------------------------------------------------------
	
	//1. if문
	@Test
	public void dynamicIf(){
		//사용자가 도서 검색 필터에 info를 선택하고 검색하면, 사용자가 입력한 키워드가 info에 포함된 도서 검색
		//사용자가 도서 검색필터에 author를 선택하고 검색하면, 사용자가 입력한 키워드가 author에 포함된 도서 검색 
		//사용자가 검색한 필터: info
		//사용자가 검색한 이름: 김애란
		
		session.selectList(NAMESPACE+"dynamicIf" , Map.of("filter" , "author" , "keyword","김애란"));
		
	}
	
	//2. choose문 (if else문)
	@Test
	public void dynamicChoose() {
		
		
		session.selectList(NAMESPACE+"dynamicChoose" , Map.of("keyword","비행"));
		
	}
	
	
	//3. forEach문 (or와 함께)
	@Test
	public void dynamicForEachAndWhereTag() {
		//사용자가 검색 조건을 여러개 선택할 경우 해당 조건들을 or연산해서 검색되는 도서를 반환
		//ex : 제목과 글쓴이로 검색
		//키워드에 김애란을 입력할 경우, 작가, 내용 중에서 하나라도 김애란이 조회되면 해당도서 반환
		
		String[] filters = {"author" ,"info"};
		session.selectList(NAMESPACE + "dynamicForEachAndWhereTag", Map.of("filters" , filters , "keyword" , "김애란"));
		
		
	}
	
	//3-2. forEach문 (and와 함께)
	@Test
	public void dynamicForEachAndWhereTag2() {
		//사용자가 검색 조건을 여러개 선택할 경우 해당 조건들을 or연산해서 검색되는 도서를 반환
		//ex : 제목과 글쓴이로 검색
		//키워드에 김애란을 입력할 경우, 작가, 내용 중에서 하나라도 김애란이 조회되면 해당도서 반환
		
		String[] filters = {"author" ,"info"};
		session.selectList(NAMESPACE + "dynamicForEachAndWhereTag2", Map.of("filters" , filters , "keyword" , "김애란"));
		
		
	}
	
	
	//3-3. forEach문 (in절과 함께)
	@Test
	public void dynamicForEachWithList() {
		//사용자가 선택한 도서명 중에서 DB에 존재하는 도서를 모두 반환
		session.selectList(NAMESPACE + "dynamicForEachWithList" , java.util.List.of("비행운","남한산성","오징어게임"));
		
		
	}
	
	
	//4. 완벽한 동적쿼리
	@Test
	public void insertTemplate() {
		
		//member에 데이터 넣기
		Map<String, Object> data = new HashMap<>();
		data.put("user_id","dynamic");
		data.put("password","1234");
		data.put("tell" , "010-0000-9899");
		data.put("email", "mybatis@naver.com");

		//사용자로부터 데이터를 입력할 테이블명, 컬럼명, 컬럼값을 전달받아 해당 테이블에 사용자가 원하는 데이터를 입력하는 쿼리
		session.insert(NAMESPACE+"insertTemplate" , Map.of("tableName" , "member", "data" , data));
			
		
	}
	
	@Test
	public void insertTemplate2() {
		
		//book에 데이터 넣기
		Map<String, Object> data = new HashMap<>();
		data.put("title","종의 기원");
		data.put("author" , "김유정");
		
		Map<String, Object> sequence = new HashMap<>();
		sequence.put("colName","bk_idx" ); //시퀀스값은 ${}처리하기 위해 뺌
		sequence.put("value","sc_bk_idx.nextval" ); //시퀀스값은 ${}처리하기 위해 뺌
		
		//사용자로부터 데이터를 입력할 테이블명, 컬럼명, 컬럼값을 전달받아 해당 테이블에 사용자가 원하는 데이터를 입력하는 쿼리
		session.insert(NAMESPACE+"insertTemplate" , Map.of("tableName" , "book", "data" , data , "sequence" , sequence));
		
		
	}
	
	@Test 
	public void dynamicSet() {
		Member member = new Member();
		member.setUserId("DEV");
		member.setPassword("11223344");
		
		session.update(NAMESPACE+"dynamicSet" , member);
		
	}
	


}
