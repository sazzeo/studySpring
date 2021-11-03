package com.kh.spring.common.code;


//enum(enumerated type) : 열거형 클래스
//서로 연관된 상수들의 집합
//서로 연관된 상수들을 하나의 묶음으로 다루기 위한 enum만의 문법적 형식과 메서드를 제공


//사용 MemberGrade.valueOf(ME00))   = > ME00정보 가져옴. 
public enum MemberGrade {
	

	//회원등급코드 ME00은 코드 info가 '일반'이고 연장 가능횟수가 1회
	//enum은 내부적으로 class임.
	//ME00("일반",1) -> public static final MemberGrade ME00 = new MemberGrade("일반",1);
	
	
	
	ME00("일반", "user",1),  // = > new MemberGrade("일반",1) 이랑 똑같음
	ME01("성실", "user",2),
	ME02("우수", "user",3),
	ME03("VIP", "user",4),
	
	AD00("super" , "admin" , 999),
	AD01("member" , "admin" , 999),  //회원 관리자 등급코드
	AD02("boadr" , "admin" , 999);   //게시판 관리자 등급코드

	
	public final String DESC;   //일반, 성실, 우수 VIP
	public final String ROLE;
	public int extendableCnt;   //연장 가능 횟수를 담음

	//함수 호출 없이 상수로 부르기
	//public final String DESC  = >필드변수로 부를 수 있음.
	
	private MemberGrade(String DESC, String ROLE ,int extendableCnt) {
		this.DESC = DESC;
		this.ROLE = ROLE;
		this.extendableCnt = extendableCnt;
	};
	



	
}
