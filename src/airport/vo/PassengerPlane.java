package airport.vo;

public class PassengerPlane extends Aircraft {

	private int maximumNumber;

	public PassengerPlane(String flightNo, String flyingRange, String startDate, int maximumNumber) {
		super(flightNo, flyingRange, startDate);
		this.maximumNumber = maximumNumber;
	}

	public int getMaximumNumber() {
		return maximumNumber;
	}

	public void setMaximumNumber(int maximumNumber) {
		this.maximumNumber = maximumNumber;
	}

	@Override
	public String toString() {
		return super.toString()+", ÃÖ´ë ÁÂ¼®¼ö:" + maximumNumber+"ÁÂ¼®";
	}
	
}
