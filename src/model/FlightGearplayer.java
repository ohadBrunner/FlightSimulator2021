package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import settings.FlightProperties;
import settings.PropertyObject;
import settings.XMLHandler;

//this is our flight gear player. it's responsible for telling the simulator what to show on the screen
//this is our facade.

public class FlightGearplayer extends Observable implements Model{


	protected TimeSeries timeSeries;
//	protected TimeSeriesAnomalyDetector anomalyDetector; // should be generated by AnomalydDetectorLoader (loader)
	public AnomalyDetectorLoader loader;
	public int timeStep;
	

	// this way the viewModel will know where is every element
	// the values may be changed every flight
	protected Map<String, PropertyObject> properties;
	FlightProperties p;

	Socket fg;
	PrintWriter out2fg;


	public FlightGearplayer(String propertiesXMLFileName) {
		// connecting with the ip & port
		properties = new HashMap<String, PropertyObject>();
		try {

			p = XMLHandler.readSettingObject(propertiesXMLFileName);

			
			//we need to check exactly what the min and max value supposed to be
			properties.put("aileron", new PropertyObject("aileron", p.getAileron(), -1, 1));
			properties.put("elevator", new PropertyObject("elevator", p.getElevator(), -1, 1));
			properties.put("rudder", new PropertyObject("rudder", p.getRudder(), -1, 1));
			properties.put("throttle", new PropertyObject("throttle", p.getThrottle(), 0, 1));
			properties.put("Z", new PropertyObject("Z", p.getZ(), -1, 1));
			properties.put("Y", new PropertyObject("Y", p.getY(), -1, 1));
			properties.put("AK", new PropertyObject("AK", p.getAK(), -1, 1));
			properties.put("roll", new PropertyObject("roll", p.getRoll(), -1, 1));
			properties.put("pitch", new PropertyObject("pitch", p.getPitch(), -1, 1));
			properties.put("yaw", new PropertyObject("yaw", p.getYaw(), -1, 1));

			try {
				fg = new Socket(p.getIp(), Integer.parseInt(p.getPort()));
				out2fg = new PrintWriter(fg.getOutputStream());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}

		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	
	
	public void setTimeStep(int timeStep) {
		this.timeStep = timeStep;
	}
	
	
	//this function should get an element name (like aileron) and a time step and then return the value from it's map
	public double getElement(String element, int timeStep) {
		
		return timeSeries.features.get(element).get(timeStep);
	}
	
	// we want to get a TimeSeries object and then send it to Flight Gear and show the flight
	// we will get the csv from view, the view gets the csv from the user and send it to the viewModel
	public void createTimeSeriesFromCSV(String CSVFile) {

		 timeSeries = new TimeSeries(CSVFile);
		 
	}


	// The model functions
	
	public TimeSeries getTimeSeries() {
		return timeSeries;
	}
	

	@Override
	public void setTimeSeries(TimeSeries timeSeries) {

		this.timeSeries = timeSeries;

	}


	// this function should be triggered every time the user slides the progress bar to a new time step
	@Override
	public void play(int start, int rate) { // play(int speed)??
		// TODO Auto-generated method stub

	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}


	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}


//	@Override
//	public Runnable getPainter() {
//
//		anomalyDetector = loader.load();
//		return ()->anomalyDetector.paint(); // return the paint function
//	}
	
	@Override
	public Runnable getPainter() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AnomalyDetectorLoader getLoader() {
		return loader;
	}



}
