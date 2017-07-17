package airport.vo;

public class Transport extends Aircraft {
	
	private int maximumLoad;
	
	public Transport(String flightNo, String flyingRange, String startDate, int maximumLoad) {
		super(flightNo, flyingRange, startDate);
		this.maximumLoad = maximumLoad;
	}

	public int getMaximumLoad() {
		return maximumLoad;
	}

	public void setMaximumLoad(int maximumLoad) {
		this.maximumLoad = maximumLoad;
	}

	@Override
	public String toString() {
		return super.toString()+", 최대 적재량:" + maximumLoad+"톤";
	}
		
}
