package airport.vo;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Aircraft implements Serializable, Comparable<String>{

	private String flightNo;
	private String flyingRange;
	private String startDate;
	
	public Aircraft(String flightNo, String flyingRange, String startDate) {
		super();
		this.flightNo = flightNo;
		this.flyingRange = flyingRange;
		this.startDate = startDate;
	}

	public String getFlightNo() {return flightNo;}
	public void setFlightNo(String flightNo) {this.flightNo = flightNo;}
	public String getFlyingRange() {return flyingRange;}
	public void setFlyingRange(String flyingRange) {this.flyingRange = flyingRange;}
	public String getStartDate() {return startDate;}
	public void setStartDate(String startDate) {this.startDate = startDate;}
	
	@Override
	public String toString() {
		return "비행기 번호:" + flightNo + ", 항속거리:" + flyingRange + "km, 출발날짜:" + startDate;
	}

	@Override
	public int compareTo(String o) {
		// TODO Auto-generated method stub
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy. MM. dd");
		Date mine=null;
		Date yours=null;
		
		try {
			mine=sdf.parse(this.getStartDate());
			yours=sdf.parse(o);			
		} catch (ParseException e) {
		}
		if (mine.before(yours)){return -1;}
		else if(mine.after(yours)){return 1;}
		else{return 0;}
	}

}