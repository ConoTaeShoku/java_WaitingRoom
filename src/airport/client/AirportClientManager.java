package airport.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import airport.manager.AirportManager;
import airport.server.AirportServer;
import airport.vo.Aircraft;

public class AirportClientManager implements AirportManager {

	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public AirportClientManager() {
		AirportServer as = new AirportServer();
		try {
			socket = new Socket("localhost", 8989);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			System.out.println("연결이 실패하였습니다. 다시 server main, client main 순서로 run해 주세요!");
		}
	}

	@Override
	public boolean addAircraft(Aircraft ac) {
		boolean result = (boolean) communicate("add", ac);
		return result;
	}

	@Override
	public Aircraft searchAircraftByFlightNo(String flightNo) {
		Aircraft result = (Aircraft) communicate("searchnum", flightNo);
		return result;
	}

	@Override
	public ArrayList<Aircraft> searchAircraftByStartDate(String today) {
		ArrayList<Aircraft> result = (ArrayList) communicate("searchtoday", today);
		return result;
	}

	@Override
	public ArrayList<Aircraft> searchAircraftByStartDate(String startDate, String endDate) {
		String[] dates = { startDate, endDate };
		ArrayList<Aircraft> result = (ArrayList) communicate("searchdatetodate", dates);
		return result;
	}

	@Override
	public boolean deleteAircraft(String flightNo) {
		boolean result = (boolean) communicate("delete", flightNo);
		return result;
	}

	@Override
	public void updateAircraftInfo(Aircraft ac) {
		communicate("update", ac);
	}

	@Override
	public ArrayList<Aircraft> getAllAircraftInfo() {
		ArrayList<Aircraft> result = (ArrayList) communicate("getall", null);
		return result;
	}

	public void quit() {
		communicate("quit", null);
		try {
			Thread.sleep(1000);
			if (oos != null)
				oos.close();
			if (ois != null)
				ois.close();
			System.out.println("client-server 통신 종료");
		} catch (Exception e) {
		}
	}

	public Object communicate(String protocol, Object o) {
		Object result = null;
		Object[] send = { protocol, o };
		try {
			oos.writeObject(send);
			result = ois.readObject();
		} catch (Exception e) {
		}
		return result;
	}

}
