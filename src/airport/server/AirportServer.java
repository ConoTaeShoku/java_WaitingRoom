package airport.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import airport.vo.Aircraft;

public class AirportServer {

	public static void main(String[] args) {
		AirportServerManager sm = new AirportServerManager();

		try {
			ServerSocket ss = new ServerSocket(8989);
			System.out.println("대기 중...");
			Socket socket = ss.accept();
			System.out.println("연결 완료!");

			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

			while (socket.isConnected()) {
				Object[] re = (Object[]) ois.readObject();
				switch ((String) re[0]) {

				case "add":
					Aircraft ac_a = (Aircraft) re[1];
					oos.writeObject(sm.addAircraft(ac_a));
					break;

				case "searchnum":
					String no_sn = (String) re[1];
					oos.writeObject(sm.searchAircraftByFlightNo(no_sn));
					break;

				case "searchtoday":
					String today = (String) re[1];
					oos.writeUnshared(sm.searchAircraftByStartDate(today));
					break;

				case "searchdatetodate":
					String[] dates = (String[]) re[1];
					oos.writeUnshared(sm.searchAircraftByStartDate(dates[0], dates[1]));
					break;

				case "delete":
					String no_d = (String) re[1];
					oos.writeObject(sm.deleteAircraft(no_d));
					break;

				case "update":
					Aircraft ac_u = (Aircraft) re[1];
					oos.writeObject(null);
					break;

				case "getall":
					oos.writeUnshared(sm.getAllAircraftInfo());
					break;

				case "quit":
					sm.quit();

					try {
						System.out.println("client-server 통신 종료");
						Thread.sleep(500);

						if (oos != null)
							oos.close();
						if (ois != null)
							ois.close();

					} catch (Exception e) {
					}
				}
			}

		} catch (Exception e) {
		}

	}

}
