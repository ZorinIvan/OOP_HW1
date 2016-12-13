package homework1;

import java.util.Iterator;
import java.util.LinkedList;
/**
 * A RouteFormatter class knows how to create a textual description of
 * directions from one location to another. The class is abstract to
 * support different textual descriptions.
 */
public abstract class RouteFormatter {

  	/**
     * Give directions for following this Route, starting at its start point
     * and facing in the specified heading.
     * @requires route != null && 
     * 			0 <= heading < 360
     * @param route the route for which to print directions.
   	 * @param heading the initial heading.
     * @return A newline-terminated directions <tt>String</tt> giving
     * 	       human-readable directions from start to end along this route.
     **/
  	public String computeDirections(Route route, double heading) {
  		// Implementation hint:
		// This method should call computeLine() for each geographic
		// feature in this route and concatenate the results into a single
		// String.
  		
  		String dir = "";
  		double startHeading = heading;
  		Iterator<GeoFeature> it = route.getGeoFeatures();
  		GeoFeature gf;
  		
  		while(it.hasNext()){
  			
  			gf = it.next();
  			dir+=computeLine(gf, startHeading);
  			startHeading = gf.getEndHeading();
  			
  		}
  		
  		return dir;
  	}


  	/**
     * Computes a single line of a multi-line directions String that
     * represents the instructions for traversing a single geographic
     * feature.
     * @requires geoFeature != null
     * @param geoFeature the geographical feature to traverse.
   	 * @param origHeading the initial heading.
     * @return A newline-terminated <tt>String</tt> that gives directions
     * 		   on how to traverse this geographic feature.
     */
  	public abstract String computeLine(GeoFeature geoFeature, double origHeading);


  	/**
     * Computes directions to turn based on the heading change.
     * @requires 0 <= oldHeading < 360 &&
     *           0 <= newHeading < 360
     * @param origHeading the start heading.
   	 * @param newHeading the desired new heading.
     * @return English directions to go from the old heading to the new
     * 		   one. Let the angle from the original heading to the new
     * 		   heading be a. The turn should be annotated as:
     * <p>
     * <pre>
     * Continue             if a < 10
     * Turn slight right    if 10 <= a < 60
     * Turn right           if 60 <= a < 120
     * Turn sharp right     if 120 <= a < 179
     * U-turn               if 179 <= a
     * </pre>
     * and likewise for left turns.
     */
  	protected String getTurnString(double origHeading, double newHeading) {
  		
  		double a = newHeading - origHeading;
  		//double a = origHeading - newHeading  ;

  		System.out.println("a = " + a);
  		if( -10 < a && a < 10)
  			return "Continue";
  		if(a >= 10 && a < 60)
  			return "Turn slight right";
  		if(a >= 60 && a < 120)
  			return "Turn right";
  		if(a >= 120 && a < 179)
  			return "Turn sharp right";
  		if(a >= 179 )
  			return "U-turn";
  		if(a>= -60 && a < -10)
  			return "Turn slight left";
  		if(a>=-120 && a < -60)
  			return "Turn left";
  		if(a>=-179 && a < -120)
  			return "Turn sharp left";
  		if(a <= -179)
  			return "U-turn";
  		return "";
  	}

}
