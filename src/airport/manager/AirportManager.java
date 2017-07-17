package airport.manager;

import java.util.ArrayList;

import airport.vo.Aircraft;

public interface AirportManager {
	/**
	 * 새로운 항공기 정보를 등록한다.
	 * @param ac 등록하고자 하는 항공기(여객기, 수송기) 정보를 갖는 Aircraft객체. 비행번호는 유일한 값을 갖는다.
	 * @return 등록에 성공하면 true를, 이미 등록된 비행번호의 항공기 정보를 등록하고자 할 경우에는 등록에 실패하여 false를 반환한다.
	 * */
	public boolean addAircraft(Aircraft ac);
	
	/**
	 * 주어진 비행번호로 등록된 항공기 정보를 검색한다. 
	 * @param flightNo 검색하고자 하는 항공기의 비행번호. 비행번호는 유일한 값을 갖는다.
	 * @return 검색된 비행 정보를 갖는 Aircraft객체. 검색 결과가 없을 경우에는 null을 반환한다.
	 * */
	public Aircraft searchAircraftByFlightNo(String flightNo);
	
	/**
	 * 오늘 날짜를 출항 일정으로 갖고 있는 항공기 목록 검색
	 * @param today 오늘 날짜에 대한 정보를 갖는 문자열. 그 형식은 반드시 "YYYY. MM. DD"형식을 취해야 한다.
	 * @return 오늘 날짜를 출항 일정으로 갖는 다수의 항공정보
	 * */
	public ArrayList<Aircraft> searchAircraftByStartDate(String today);
	
	/**
	 * 주어진 시작 날짜와 끝날짜 사이를 출항 일정으로 갖고 있는 항공기 목록 검색
	 * @param fromDate 조회하고자 하는 출항 일정의 시작 날짜. 그 형식은 반드시 "YYYY. MM. DD"형식을 취해야 한다.
	 * @param toDate 조회하고자 하는 출항 일정의 마지막 날짜. 그 형식은 반드시 "YYYY. MM. DD"형식을 취해야 한다.
	 * */
	public ArrayList<Aircraft> searchAircraftByStartDate(String fromDate, String toDate);
	
	/**
	 * 주어진 비행번호로 등록된 항공기 정보를 삭제한다.
	 * @param flightNo 삭제하고자 하는 항공기의 비행번호. 비행번호는 유일한 값을 갖는다.
	 * @return 삭제에 성공하면 true를, 실패하면 false를 반환한다.
	 * */
	public boolean deleteAircraft(String flightNo);
	
	/**
	 * 새로운 정보를 갖는 Aircraft객체로 기존 객체 값을 수정한다. 항공기 종류에 따라 수정 가능한 항목(필드)는 다음과 같다.
	 * 여객기: 최대 좌석 수
	 * 수송기: 최대 적재량
	 * @param ac 새로운 정보를 갖는 Aircraft객체
	 * */
	public void updateAircraftInfo(Aircraft ac);
	
	/**
	 * 등록된 모등 항공정보를 가지고 있는 ArrayList객체를 반환한다.
	 * @return 등록된 모등 항공정보를 가지고 있는 ArrayList객체
	 * */
	public ArrayList<Aircraft> getAllAircraftInfo();
	
}