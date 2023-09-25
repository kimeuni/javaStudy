package t8_sungjuk;

import java.util.ArrayList;
import java.util.Scanner;

// 비즈니스 로직
public class SungjukService {
	Scanner scanner = new Scanner(System.in);
	SungjukDAO dao = new SungjukDAO();
	SungjukVO vo = null;
	
	String ans = "N";
	String name = "";
	
	// 성적 입력
	public void setInput() {
		vo = new SungjukVO();
		
		while(true) {
			System.out.println("\n** 성적 입력처리 **");
			System.out.print("성명 : "); vo.setName(scanner.next());
			System.out.print("국어 : ");vo.setKor(scanner.nextInt());
			System.out.print("영어 : ");vo.setEng(scanner.nextInt());
			System.out.print("수학 : ");vo.setMat(scanner.nextInt());
			
			// 동명이인 처리(같은 성명자료는 입력하지 않는다.)
			
			int res = dao.setInput(vo);	//dao 객체에 가서 입력된 값 데이터베이스에 저장.	//dao.setInput(vo) 메소드 vo를 넣은 이유는 입력받은 값을(vo.setNaem 등) SungjukVO()객체에 읽어서 저장하기 위해 (vo.getName) 
			
			if(res == 0) System.out.println("성적 등록 실패~");
			else System.out.println("성적이 등록되었습니다.");
			
			System.out.print("계속하시겠습니까?(y/n) => ");
			ans = scanner.next();
			if(ans.toUpperCase().equals("N")) break;
		}
//		scanner.close();//메소드는 동작이 있는 곳에서 사용 가능, 필드에서 스캐너를 닫을 수 없다.	
		//하지만, 스캐너를 닫으면 안된다. 입력이 끝나면 main으로 돌아가는데 스캐너가 닫혀있으면 오류가 나온다.
	}

	// 전체 리스트
	public void setList() {
		ArrayList<SungjukVO> vos = dao.getList();
		
		System.out.println("\n\t*** 성 적 리 스 트 ***");
		System.out.println("===========================================================================");
		System.out.println("번호\t성명\t국어\t영어\t수학\t총점\t평균\t학점");
		System.out.println("---------------------------------------------------------------------------");
		for(int i=0; i<vos.size(); i++) {
			vo = vos.get(i);
			calculator(vo);	//바로위에 있는 vo와 같음	//총점, 평균, 학점을 계산하기 위해 계산처리하는 메소드를 만들었다.
			System.out.print(vo.getIdx() + "\t");
			System.out.print(vo.getName() + "\t");
			System.out.print(vo.getKor() + "\t");
			System.out.print(vo.getEng() + "\t");
			System.out.print(vo.getMat() + "\t");
			System.out.print(vo.getTot() + "\t");
			System.out.print(String.format("%.1f", vo.getAvg())+ "\t");
			System.out.print(vo.getGrade() + "\n");
		}
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("\t총 인원 수 : "+vos.size()+" 명");
		System.out.println("===========================================================================");
		
		
	}
	
	// 계산처리...
	private void calculator(SungjukVO vo) {
		vo.setTot(vo.getKor()+vo.getEng()+vo.getMat());
		
		vo.setAvg(vo.getTot()/3.0);
		
		if(vo.getAvg()>=90) vo.setGrade('A');
		else if (vo.getAvg()>=80) vo.setGrade('B');
		else if (vo.getAvg()>=70) vo.setGrade('C');
		else if (vo.getAvg()>=60) vo.setGrade('D');
		else vo.setGrade('F');
	}

	// 개별 조회
	public void setSearch() {
		while(true) {
			System.out.print("\n조회할 성명을 입력하세요? ");
			name = scanner.next();
			
			vo = dao.getSearch(name);
			
			if(vo != null) {
				calculator(vo);
				System.out.println("\n번호 : " + vo.getIdx());
				System.out.println("성명 : " + vo.getName());
				System.out.println("국어 : " + vo.getKor());
				System.out.println("영어 : " + vo.getEng());
				System.out.println("수학 : " + vo.getMat());
				System.out.println("총점 : " + vo.getTot());
				System.out.println("평균 : " + vo.getAvg());
				System.out.println("학점 : " + vo.getGrade());
			}
			else System.out.println("검색하신 "+name+"님은 없습니다.");
			
			System.out.print("계속 검색하시겠습니까?(y/n) => ");
			ans = scanner.next();
			if(ans.toUpperCase().equals("N")) break;
		}
	}
	
}
