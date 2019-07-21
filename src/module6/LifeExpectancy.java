package module6;

import processing.core.PApplet;
import processing.core.PConstants;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.providers.Google.*;

import java.util.List;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import java.util.HashMap;


import de.fhpotsdam.unfolding.marker.Marker;

/**
 * Visualizes life expectancy in different countries. 
 * 
 * It loads the country shapes from a GeoJSON file via a data reader, and loads the population density values from
 * another CSV file (provided by the World Bank). The data value is encoded to transparency via a simplistic linear
 * mapping.
 * @author Star Dust
 * Date: May 17, 2019
 */
public class LifeExpectancy extends PApplet {

	UnfoldingMap map;
	HashMap<String, Float> lifeExpMap;
	List<Feature> countries;
	private List<Marker> countryMarkers;
	private int lastX = 0,lastY = 0;
	public void setup() {
		size(900, 700, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 650, 600, new Microsoft.AerialProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		// Load lifeExpectancy data
		lifeExpMap = ParseFeed.loadLifeExpectancyFromCSV(this,"LifeExpectancyWorldBank.csv");


		// Load country polygons and adds them as markers
		countries = GeoJSONReader.loadData(this, "countries.geo.json"); // ShapeFeature und MultiFeature
		
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		map.addMarkers(countryMarkers);
		//System.out.println(countryMarkers.get(0).getId());
		
		// Country markers are shaded according to life expectancy (only once)
		shadeCountries();
	}

	public void draw() {
		// Draw map tiles and country markers
		map.draw();
		addKey();
		showTitle();
	}

	private void addKey(){
		
		fill(255, 250, 240);
		
		int xbase = 25;
		int ybase = 50;
		
		rect(xbase, ybase, 150, 130);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Life expectancy Key", xbase+10, ybase+10);
		
		fill(color(72,61,139));
		ellipse(xbase+15, ybase+35, 20, 20);
		fill(color(255, 0, 0));
		ellipse(xbase+15, ybase+55, 20, 20);
		fill(color(150, 150, 150));
		ellipse(xbase+15, ybase+75, 20, 20);
		
		textAlign(LEFT, CENTER);
		fill(0, 0, 0);
		text("live longer", xbase+50, ybase+35);
		text("live shorter", xbase+50, ybase+55);
		

		text("No data", xbase+50, ybase+75);

		
	}
	
	
	private void showTitle(){
		for (Marker marker : countryMarkers){
			if (marker.isHidden()){
				String countryName = marker.getId();
				String title = "";
				if (lifeExpMap.get(countryName) != null)
					title = "Country code: " +  countryName + "\n Life expectancy: " + Math.round(lifeExpMap.get(countryName));
				else
					title = "Country code: " +  countryName + "\n Life expectancy: No data";
					
				pushStyle();
				
				rectMode(PConstants.CORNER);
				
				stroke(110);
				fill(255,255,255);
				rect(lastX, lastY + 15, textWidth(title) +6, 40, 5);
				
				textAlign(PConstants.LEFT, PConstants.TOP);
				fill(0);
				text(title, lastX + 3 , lastY +18);
				
				popStyle();
				
			}
		} 
			

		
	}
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
	public void mouseClicked()
	{
		for (Marker marker : countryMarkers){
			if (marker.isInside(map, mouseX, mouseY)){
				marker.setHidden(true);				
				lastX = mouseX;
				lastY = mouseY;
			}
			else
				marker.setHidden(false);
		} 
			
	}

	//Helper method to color each country based on life expectancy
	//Red-orange indicates low (near 40)
	//Blue indicates high (near 100)
	private void shadeCountries() {
		for (Marker marker : countryMarkers) {
			// Find data for country of the current marker
			String countryId = marker.getId();
			//System.out.println(lifeExpMap.containsKey(countryId));
			if (lifeExpMap.containsKey(countryId)) {
				//System.out.println("Country: " + countryId + "life exp: " + lifeExpMap.containsKey(countryId));
				float lifeExp = lifeExpMap.get(countryId);
				// Encode value as brightness (values range: 40-90)
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150,150,150));
			}
		}
	}


}
