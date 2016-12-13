package homework1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment 
 * to the end of a Route. An added segment must be properly oriented; that 
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {

	private final LinkedList<GeoFeature> geoFeatures;
	private final LinkedList<GeoSegment> geoSegments;
	private final double length;
	private final GeoPoint start;
	private final GeoPoint end;
	private final double startHeading;
	private final double endHeading;
	private final GeoSegment endingGeoSegment;

	
	// Abstract Function:
	// A sequence of GeoSegments or GeoFeatures with a final segment = this.endingGeoSegment,
	// start of the route = this.start,
	// end of the route = this.end, length of the route = this.length
	
	// Rep invariant:
	// start != null, end != null,  0 < startHeading < 360, 0 < endHeading < 360
	// length > 0, geoFeatures != null, geoSegments != null.
	
	private void checkRep(){
		assert geoFeatures.size() > 0 && geoSegments.size() > 0 && length > 0 ;
		assert start!= null && end != null;
		assert 0 <= startHeading && startHeading  < 360 && 0 <= endHeading && endHeading <360;
		assert geoSegments.getLast().equals(endingGeoSegment);
		Iterator<GeoSegment> it = geoFeatures.getLast().getGeoSegments();
		while(it.hasNext())
			it.next();
		assert it.equals(endingGeoSegment);
	}

  	/**
  	 * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public Route(GeoSegment gs) { 
  		
  		this.geoSegments = new LinkedList<GeoSegment>();
  		this.geoFeatures = new LinkedList<GeoFeature>();
  		this.geoSegments.add(gs);
  		this.length = gs.getLength();
  		this.geoFeatures.add(new GeoFeature(gs));
  		start = gs.getP1();
  		end = gs.getP2();
  		startHeading = gs.getHeading();
  		endHeading = gs.getHeading();
  		this.endingGeoSegment = new GeoSegment(gs.getName(), gs.getP1(), gs.getP2());

  		checkRep();
  	}
  	
  	/**
  	 * Constructs a new Route.
     * @requires gs != null, r != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	private Route(Route r, GeoSegment gs){
  		
  		
  		this.geoSegments = new LinkedList<GeoSegment>();
  		this.geoSegments.addAll(r.geoSegments);
  		this.geoSegments.add(gs);
  		
 		this.geoFeatures = new LinkedList<GeoFeature>(r.geoFeatures);
  		if (geoFeatures.getLast().getName().equals(gs.getName())) 
  		{
  			GeoFeature lastGeoFeature = this.geoFeatures.getLast(); 
  			this.geoFeatures.removeLast();
  			this.geoFeatures.addLast(lastGeoFeature.addSegment(gs));  			
  		}
  		else {
  			//otherwise, create a new feature
  			this.geoFeatures.addLast(new GeoFeature(gs));
  			
  		}
  		
  		this.length = r.length + gs.getLength();
  		start = r.start;
  		end = gs.getP2();
  		startHeading = r.startHeading;
  		endHeading = gs.getHeading();
  		endingGeoSegment = new GeoSegment(gs.getName(), gs.getP1(), gs.getP2());

  		checkRep();
  		
  	}


    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
  	public GeoPoint getStart() {
  		checkRep();
  		return this.start;
  	}


  	/**
  	 * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
  	public GeoPoint getEnd() {
  		checkRep();
  		return this.end;
  	}


  	/**
  	 * Returns direction of travel at the start of the route, in degrees.
   	 * @return direction (in compass heading) of travel at the start of the
   	 *         route, in degrees.
   	 **/
  	public double getStartHeading() {
  		checkRep();
  		return this.startHeading;
  	}


  	/**
  	 * Returns direction of travel at the end of the route, in degrees.
     * @return direction (in compass heading) of travel at the end of the
     *         route, in degrees.
     **/
  	public double getEndHeading() {
  		return this.endHeading;
  	}


  	/**
  	 * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
   	 **/
  	public double getLength() {
  		checkRep();
  		return this.length;
  	}


  	/**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
   	 * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
  	public Route addSegment(GeoSegment gs) {
  		checkRep();
  		Route r = new Route(this, gs);
  		return r;
  		
  	}


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
  	public Iterator<GeoFeature> getGeoFeatures() {
  		checkRep();
  		return this.geoFeatures.iterator();
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
  	public Iterator<GeoSegment> getGeoSegments() {
  		checkRep();
  		return this.geoSegments.iterator();
  	}


  	/**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		checkRep();
  		if(o != null && o instanceof Route){
  			checkRep();
  			return this.geoFeatures.equals(((Route)o).geoFeatures);
  		}
  		checkRep();
  		return false;
  	}


    /**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// for improved performance.
  		checkRep();
    	return Objects.hash(geoSegments.hashCode(), geoFeatures.hashCode());
  	}


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
  		String str = "";
		ListIterator<GeoFeature> listIterator = geoFeatures.listIterator();
  		while(listIterator.hasNext()){
  			str += " " + listIterator.next().toString();
  		}
  		return str;
  	}

}
