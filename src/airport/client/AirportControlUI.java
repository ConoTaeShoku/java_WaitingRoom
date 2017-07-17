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
			
			case 1://�װ��� ���
				boolean flag=true;
				while(flag){
					insertAircraft();
					int t_i=sc.nextInt();
					switch(t_i){
					case 1: case 2:
						boolean r_a=cm.addAircraft(makeAircraft(t_i));
						if(r_a){System.out.println("��� �Ϸ�");}
						else{System.out.println("��� ����");}
						break;
					case 3://�����޴�
						flag=false;
						break;
					}
				}
				break;
				
			case 2://�װ��� �˻�
				System.out.print("�˻��� ����� ��ȣ: "); String no_sn=sc.next();
				Aircraft a_sn=cm.searchAircraftByFlightNo(no_sn);
				if (a_sn==null){System.out.println("�˻� ��� ����");}
				else {System.out.println(a_sn);}
				break;
				
			case 3://�������� ��ȸ
				searchByStartDate();
				ArrayList<Aircraft> dList=new ArrayList<>();
				switch(sc.nextInt()){
				case 1://���� ��¥
					String today=df.format(new Date());
					System.out.println("\nToday is: "+today);
					dList=cm.searchAircraftByStartDate(today);
					break;
				case 2://�Է±Ⱓ ��¥
					System.out.print("���� ��¥(yyyy. mm. dd): "); sc.nextLine();String date_start=sc.nextLine();
					System.out.print("������ ��¥(yyyy. mm. dd): "); String date_end=sc.nextLine();
					dList=cm.searchAircraftByStartDate(date_start,date_end);
					System.out.println();
					break;
				}
				if(dList.size()==0){
					System.out.println("�˻� ��� ����");
				}else {
					for(Aircraft ac:dList)
						System.out.println(ac);
					System.out.println();
				}
				break;
				
			case 4://�װ��� ����
				System.out.print("������ ����� ��ȣ: "); String no_d=sc.next();
				boolean r_d=cm.deleteAircraft(no_d);
				if (r_d){System.out.println("���� ����");}
				else {System.out.println("���� ����");}
				break;

			case 5://�װ������� ����
				System.out.print("������ ����� ��ȸ(�����ȣ): "); String no_m=sc.next();
				Aircraft a_m=cm.searchAircraftByFlightNo(no_m);
				if (a_m==null){System.out.println("�˻� ��� ����");}
				else {
					System.out.println(a_m);
					if (a_m instanceof PassengerPlane){
						System.out.print("�ִ� �¼���: ");
						((PassengerPlane) a_m).setMaximumNumber(sc.nextInt());
					} else if (a_m instanceof Transport){
						System.out.print("�ִ� ���緮(ton): ");
						((Transport) a_m).setMaximumLoad(sc.nextInt());
					}
					System.out.println("���� �Ϸ�");
				}
				break;
				
			case 6://��ü���
				ArrayList<Aircraft> allList=new ArrayList<>();
				allList=cm.getAllAircraftInfo();
				if(allList.size()>0){
					System.out.println();
					for(Aircraft ac:allList)
						System.out.println(ac);
					System.out.println();
				} else {
					System.out.println("\n��ϵ� �װ��� ����\n");
				}
				break;
				
			case 7://����
				System.out.println("���� ��...");
				cm.quit();
				try{
					Thread.sleep(1500);
					System.out.println("program ����");
					System.exit(0);
				}catch(Exception e){
				}
				break;
				
			default:
				System.out.println("�߸��� �޴� ����");
				break;
			}
		}
		
	}
	
	//* �������� ��ȸ �޴� ó��
	private void searchByStartDate() {
		System.out.println("-------------------");
		System.out.println("       �������� ��ȸ        ");
		System.out.println("-------------------");
		System.out.println("1. ���� ��¥ ���� ��ȸ");
		System.out.println("2. �Է±Ⱓ ��¥ ���� ��ȸ");
		System.out.print(">�޴�����: ");
	}

	//* �װ��� ��� �޴� ó��
	private void insertAircraft() {
		System.out.println("--------------");
		System.out.println("    �װ��� ���      ");
		System.out.println("--------------");
		System.out.println("1. ������ ���");
		System.out.println("2. ���۱� ���");
		System.out.println("3. �����޴�");
		System.out.print(">�޴�����: ");
	}

	/**
	 * �װ��� ��� �� �װ��� ������ ���� ��ü ������ �ʿ��� ������ �Է¹޾� Aircraft��ü�� �����Ͽ� ��ȯ
	 * �װ��� ������ ��ü ������ ���� �ʿ��� ���� ������ �Է¹��� �� �Ű������� ���� Ÿ��(1:������, 2:���۱�)�� ���� �ΰ����� ������ �߰��� �޾� ��ü�� �����Ѵ�.
	 * @param ����ϰ��� �ϴ� �װ��� ������ ���� �� (1:������, 2:���۱�)
	 * @return Ű����κ��� �Է¹��� ������ ���� ������ �װ��� ��ü
	 * */
	public Aircraft makeAircraft(int type) {
		Aircraft ac = null;
		switch(type){
		case 1://PassengerPlane
			System.out.print("����� ��ȣ: "); String no1=sc.next();
			System.out.print("�׼ӰŸ�(km): "); String range1=sc.next(); sc.nextLine();
			System.out.print("���׳�¥(yyyy. mm. dd): "); String startdate1=sc.nextLine();
			System.out.print("�ִ� �¼���: "); int seat=sc.nextInt();
			ac=(Aircraft)(new PassengerPlane(no1,range1,startdate1,seat));
			break;
		case 2://Transport
			System.out.print("����� ��ȣ: "); String no2=sc.next();
			System.out.print("�׼ӰŸ�(km): "); String range2=sc.next(); sc.nextLine();
			System.out.print("���׳�¥(yyyy. mm. dd): "); String startdate2=sc.nextLine();
			System.out.print("�ִ� ���緮(ton): "); int load=sc.nextInt();
			ac= (Aircraft)(new Transport(no2,range2,startdate2,load));
			break;
		}
		return ac;
	}
	
	//* ���θ޴� ���
	private void printMainMenu(){
		System.out.println("-----------------------------");
		System.out.println("    Soft Engineer Airport    ");
		System.out.println("-----------------------------");
		System.out.println("1. �װ��� ���");
		System.out.println("2. �װ��� �˻�");
		System.out.println("3. �������� ��ȸ");
		System.out.println("4. �װ��� ����");
		System.out.println("5. �װ������� ����");
		System.out.println("6. ��ü���");
		System.out.println("7. ����");
		System.out.print(">�޴�����: ");
	}
}