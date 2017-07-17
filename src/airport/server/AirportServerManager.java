package airport.server;

import java.util.*;
import java.io.*;
import java.net.*;

import airport.manager.AirportManager;
import airport.vo.Aircraft;

public class AirportServerManager implements AirportManager{

	File file=new File("data.dat");
	ArrayList<Aircraft> aList;
	
	FileOutputStream fos;
	FileInputStream fis;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public AirportServerManager(){
		loadFile();
	}
	
	public void loadFile(){
		try{
			if (file.exists()){
				fis=new FileInputStream(file);
				ois=new ObjectInputStream(fis);
				aList=(ArrayList)ois.readObject();
			}
			else{
				aList=new ArrayList<>();
			}
		} catch(Exception e){
				
		}
	}
	
	public void saveFile(){
		try{
			fos=new FileOutputStream(file);
			oos=new ObjectOutputStream(fos);
			oos.writeObject(aList);
		} catch(Exception e){
		}
	}
	
	@Override
	public boolean addAircraft(Aircraft ac) {
		// TODO Auto-generated method stub
		for(int i=0;i<aList.size();i++){
			if(aList.get(i).getFlightNo().equals(ac.getFlightNo())){
				return false;
			}
		}
		aList.add(ac);
		saveFile();
		return true;
	}

	@Override
	public Aircraft searchAircraftByFlightNo(String flightNo) {
		// TODO Auto-generated method stub
		Aircraft ac=null;
		for(int i=0;i<aList.size();i++){
			if(aList.get(i).getFlightNo().equals(flightNo)){
				ac=aList.get(i);
			}
		}
		return ac;
	}

	@Override
	public ArrayList<Aircraft> searchAircraftByStartDate(String today) {
		// TODO Auto-generated method stub
		ArrayList<Aircraft> result=new ArrayList<Aircraft>();
		for(int i=0;i<aList.size();i++){
			if(aList.get(i).getStartDate().equals(today)){
				result.add(aList.get(i));
			}
		}
		return result;
	}

	@Override
	public ArrayList<Aircraft> searchAircraftByStartDate(String startDate, String endDate) {
		// TODO Auto-generated method stub
		ArrayList<Aircraft> result=new ArrayList<Aircraft>();
		for(int i=0;i<aList.size();i++){
			String date=aList.get(i).getStartDate();
			if((date.compareTo(startDate)==1)&&(date.compareTo(endDate)==(-1))){
				result.add(aList.get(i));
			} else if((date.compareTo(startDate)==0)||(date.compareTo(endDate)==0)){
				result.add(aList.get(i));
			}
		}
		return result;
	}

	@Override
	public boolean deleteAircraft(String flightNo) {
		// TODO Auto-generated method stub
		boolean result=false;
		for(int i=0;i<aList.size();i++){
			if(aList.get(i).getFlightNo().equals(flightNo)){
				aList.remove(i);
				result=true;
				saveFile();
			}
		}
		return result;
	}

	@Override
	public void updateAircraftInfo(Aircraft ac) {
		// TODO Auto-generated method stub
		for(int i=0;i<aList.size();i++){
			if(aList.get(i).getFlightNo().equals(ac.getFlightNo())){
				aList.set(i,ac);
				saveFile();
			}
		}
	}

	@Override
	public ArrayList<Aircraft> getAllAircraftInfo() {
		// TODO Auto-generated method stub
		return aList;
	}
	
	public void quit(){
		try{
			System.out.println("file input/output Á¾·á");
			if(oos!=null)
				oos.close();
			if(ois!=null)
				ois.close();
		}catch(Exception e){
		}
	}
}
