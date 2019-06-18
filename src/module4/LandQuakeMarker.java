package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for land earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Star Dust
 * Date: May 17, 2019
 *
 */
public class LandQuakeMarker extends EarthquakeMarker {
	
	
	public LandQuakeMarker(PointFeature quake) {
		
		// calling EarthquakeMarker constructor
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = true;
	}


	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		// Draw a centered circle for land quakes
		// DO NOT set the fill color here.  That will be set in the EarthquakeMarker
		// class to indicate the depth of the earthquake.
		// Simply draw a centered circle.
		
		
		float magnitude = getMagnitude();
		
		if (magnitude < THRESHOLD_LIGHT )
			pg.ellipse(x, y, SIZE_LIGHT,SIZE_LIGHT);
		else if ( magnitude >= THRESHOLD_LIGHT && magnitude <= THRESHOLD_MODERATE )
			pg.ellipse(x, y, SIZE_MODERATE,SIZE_MODERATE);
		else 
			pg.ellipse(x, y, SIZE_LARGE,SIZE_LARGE);
		
	}
	

	// Get the country the earthquake is in
	public String getCountry() {
		return (String) getProperty("country");
	}



		
}