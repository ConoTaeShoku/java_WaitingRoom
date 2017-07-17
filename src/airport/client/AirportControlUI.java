package airport.client;

import airport.vo.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AirportControlUI {
	
	private AirportClientManager cm=new AirportClientManager();
	private Scanner sc= new Scanner(System.in);
	
	DateFormat df=DateFormat.getDateInstance();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy. MM. dd");

	public AirportControlUI(){
		
		while(true){
			printMainMenu();
			switch(sc.nextInt()){
			
			case 1://항공기 등록
				boolean flag=true;
				while(flag){
					insertAircraft();
					int t_i=sc.nextInt();
					switch(t_i){
					case 1: case 2:
						boolean r_a=cm.addAircraft(makeAircraft(t_i));
						if(r_a){System.out.println("등록 완료");}
						else{System.out.println("등록 실패");}
						break;
					case 3://상위메뉴
						flag=false;
						break;
					}
				}
				break;
				
			case 2://항공기 검색
				System.out.print("검색할 비행기 번호: "); String no_sn=sc.next();
				Aircraft a_sn=cm.searchAircraftByFlightNo(no_sn);
				if (a_sn==null){System.out.println("검색 결과 없음");}
				else {System.out.println(a_sn);}
				break;
				
			case 3://비행일정 조회
				searchByStartDate();
				ArrayList<Aircraft> dList=new ArrayList<>();
				switch(sc.nextInt()){
				case 1://오늘 날짜
					String today=df.format(new Date());
					System.out.println("\nToday is: "+today);
					dList=cm.searchAircraftByStartDate(today);
					break;
				case 2://입력기간 날짜
					System.out.print("시작 날짜(yyyy. mm. dd): "); sc.nextLine();String date_start=sc.nextLine();
					System.out.print("마지막 날짜(yyyy. mm. dd): "); String date_end=sc.nextLine();
					dList=cm.searchAircraftByStartDate(date_start,date_end);
					System.out.println();
					break;
				}
				if(dList.size()==0){
					System.out.println("검색 결과 없음");
				}else {
					for(Aircraft ac:dList)
						System.out.println(ac);
					System.out.println();
				}
				break;
				
			case 4://항공기 삭제
				System.out.print("삭제할 비행기 번호: "); String no_d=sc.next();
				boolean r_d=cm.deleteAircraft(no_d);
				if (r_d){System.out.println("삭제 성공");}
				else {System.out.println("삭제 실패");}
				break;

			case 5://항공기정보 수정
				System.out.print("수정할 비행기 조회(비행번호): "); String no_m=sc.next();
				Aircraft a_m=cm.searchAircraftByFlightNo(no_m);
				if (a_m==null){System.out.println("검색 결과 없음");}
				else {
					System.out.println(a_m);
					if (a_m instanceof PassengerPlane){
						System.out.print("최대 좌석수: ");
						((PassengerPlane) a_m).setMaximumNumber(sc.nextInt());
					} else if (a_m instanceof Transport){
						System.out.print("최대 적재량(ton): ");
						((Transport) a_m).setMaximumLoad(sc.nextInt());
					}
					System.out.println("수정 완료");
				}
				break;
				
			case 6://전체출력
				ArrayList<Aircraft> allList=new ArrayList<>();
				allList=cm.getAllAircraftInfo();
				if(allList.size()>0){
					System.out.println();
					for(Aircraft ac:allList)
						System.out.println(ac);
					System.out.println();
				} else {
					System.out.println("\n등록된 항공기 없음\n");
				}
				break;
				
			case 7://종료
				System.out.println("종료 중...");
				cm.quit();
				try{
					Thread.sleep(1500);
					System.out.println("program 종료");
					System.exit(0);
				}catch(Exception e){
				}
				break;
				
			default:
				System.out.println("잘못된 메뉴 선택");
				break;
			}
		}
		
	}
	
	//* 비행일정 조회 메뉴 처리
	private void searchByStartDate() {
		System.out.println("-------------------");
		System.out.println("       비행일정 조회        ");
		System.out.println("-------------------");
		System.out.println("1. 오늘 날짜 출항 조회");
		System.out.println("2. 입력기간 날짜 출항 조회");
		System.out.print(">메뉴선택: ");
	}

	//* 항공기 등록 메뉴 처리
	private void insertAircraft() {
		System.out.println("--------------");
		System.out.println("    항공기 등록      ");
		System.out.println("--------------");
		System.out.println("1. 여객기 등록");
		System.out.println("2. 수송기 등록");
		System.out.println("3. 상위메뉴");
		System.out.print(">메뉴선택: ");
	}

	/**
	 * 항공기 등록 시 항공기 종류에 따른 객체 생성에 필요한 정보를 입력받아 Aircraft객체를 생성하여 반환
	 * 항공기 종류별 객체 생성을 위해 필요한 공통 정보를 입력받은 후 매개변수로 받은 타입(1:여객기, 2:수송기)에 따른 부가적인 정보를 추가로 받아 객체를 생성한다.
	 * @param 등록하고자 하는 항공기 종유의 구분 값 (1:여객기, 2:수송기)
	 * @return 키보드로부터 입력받은 정보를 통해 생성된 항공기 객체
	 * */
	public Aircraft makeAircraft(int type) {
		Aircraft ac = null;
		switch(type){
		case 1://PassengerPlane
			System.out.print("비행기 번호: "); String no1=sc.next();
			System.out.print("항속거리(km): "); String range1=sc.next(); sc.nextLine();
			System.out.print("출항날짜(yyyy. mm. dd): "); String startdate1=sc.nextLine();
			System.out.print("최대 좌석수: "); int seat=sc.nextInt();
			ac=(Aircraft)(new PassengerPlane(no1,range1,startdate1,seat));
			break;
		case 2://Transport
			System.out.print("비행기 번호: "); String no2=sc.next();
			System.out.print("항속거리(km): "); String range2=sc.next(); sc.nextLine();
			System.out.print("출항날짜(yyyy. mm. dd): "); String startdate2=sc.nextLine();
			System.out.print("최대 적재량(ton): "); int load=sc.nextInt();
			ac= (Aircraft)(new Transport(no2,range2,startdate2,load));
			break;
		}
		return ac;
	}
	
	//* 메인메뉴 출력
	private void printMainMenu(){
		System.out.println("-----------------------------");
		System.out.println("    Soft Engineer Airport    ");
		System.out.println("-----------------------------");
		System.out.println("1. 항공기 등록");
		System.out.println("2. 항공기 검색");
		System.out.println("3. 비행일정 조회");
		System.out.println("4. 항공기 삭제");
		System.out.println("5. 항공기정보 수정");
		System.out.println("6. 전체출력");
		System.out.println("7. 종료");
		System.out.print(">메뉴선택: ");
	}
}