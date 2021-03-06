package homework1;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;

/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {
	
	// Implementation hint:
	// When asked to return an Iterator, consider using the iterator() method
	// in the List interface. Two nice classes that implement the List
	// interface are ArrayList and LinkedList. If comparing two Lists for
	// equality is needed, consider using the equals() method of List. More
	// info can be found at:
	//   http://docs.oracle.com/javase/8/docs/api/java/util/List.html
	
	private final GeoPoint start;
	private final GeoPoint end;
	private final double startHeading;
	private final double endHeading;
	private final double length;
	private final String name;
	private final LinkedList<GeoSegment> geoSegments;
	
  	// Abstract Function:
  	// A sequence of GeoSegments, all of which have the same name,
	// and for each element i>0 element[i].p2 = element[i+1].p1
	
 	// Representation invariant for GeoFeature: 
	// name != null && length >= 0 
	// An added segment must be properly oriented;
	// that is, its p1 field must correspond to the end of the original
	// GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
	// and the name of the GeoSegment being added must match the name of the
	// existing GeoFeature
	
	private void checkRep() {
		assert this.name != null : "name != null ";
		assert this.length >= 0 : "length >= 0";
		assert this.geoSegments.size() > 0 : "sequence of GeoSegments can't be empty";
		
		ListIterator<GeoSegment> listIterator = geoSegments.listIterator();
		GeoSegment gsPrev = listIterator.next();
		if(!listIterator.hasNext()){
			assert  gsPrev.getName() == this.name : "segment with a different name";
		}
		while (listIterator.hasNext()) {
			GeoSegment gsNext = listIterator.next();
			assert  gsPrev.getName() == this.name : "segment with a different name";
			if(listIterator.hasPrevious()){ //not first element
				assert gsPrev.getP2().equals(gsNext.getP1()) : "not properly oriented";
			}
			gsPrev.equals(gsNext);
		}
		
	}
  	

	
	/**
     * Constructs a new GeoFeature.
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public GeoFeature(GeoSegment gs) {
  		name = gs.getName();
  		start = gs.getP1();
  		end = gs.getP2();
  		length = gs.getLength();
  		geoSegments = new  LinkedList<GeoSegment>();
  		geoSegments.add(gs);
  		startHeading = gs.getHeading();
  		endHeading = gs.getHeading();
  		checkRep();
  	}
  	
  	
 	/**
     * Constructs a new GeoFeature 
     * @requires gs != null and gf != null
     * @effects Constructs a new GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public GeoFeature(GeoSegment gs, GeoFeature gf) {
  		
  		name = gs.getName();
  		start = gf.start;
  		end = gs.getP2();
  		length = gs.getLength() + gf.getLength();
  		geoSegments = new  LinkedList<GeoSegment>(gf.geoSegments);
  		geoSegments.add(gs);
  		startHeading = gf.startHeading;
  		endHeading = gs.getHeading();
  	}

 	/**
 	  * Returns name of geographic feature.
      * @return name of geographic feature
      */
  	public String getName() {
  		checkRep();
  		return this.name;
  	}


  	/**
  	 * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
  	public GeoPoint getStart() {
  		checkRep();
  		return this.start;
  	}


  	/**
  	 * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
  	public GeoPoint getEnd() {
  		checkRep();
  		return this.end;
  	}


  	/**
  	 * Returns direction of travel at the start of the geographic feature.
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees.
     *         If length is zero , then return zero.
     */
  	public double getStartHeading() {
  		checkRep();
  		return this.startHeading;
  	}


  	/**
  	 * Returns direction of travel at the end of the geographic feature.
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees.
     *         If length is zero , then return zero.
     */
  	public double getEndHeading() {
  		checkRep();
  		return this.endHeading;
  	}


  	/**
  	 * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
  	public double getLength() {
  		checkRep();
  		return this.length;
  	}


  	/**
   	 * Creates a new GeoFeature that is equal to this GeoFeature with gs
   	 * appended to its end.
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new GeoFeature r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *    	   r.length = this.length + gs.length
     **/
  	public GeoFeature addSegment(GeoSegment gs) {
  		checkRep();
  		GeoFeature gF = new GeoFeature(gs, this);
  		checkRep();
  		return gF;
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
  	public Iterator<GeoSegment> getGeoSegments() {
  		checkRep();
  		return geoSegments.iterator();
  	}


  	/**
     * Compares the argument with this GeoFeature for equality.
     * @return o != null && (o instanceof GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		checkRep();
  		return o != null && (o instanceof GeoFeature) && this.geoSegments.equals(((GeoFeature)o).geoSegments);
  	}


  	/**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
  		checkRep();
  		return geoSegments.hashCode();
  	}


  	/**
  	 * Returns a string representation of this.
   	 * @return a string representation of this.
     **/
  	public String toString() {
  		checkRep();
  		String str = "{" + this.name + ":";
		ListIterator<GeoSegment> listIterator = geoSegments.listIterator();
  		while(listIterator.hasNext()){
  			str += " " + listIterator.next().toString();
  		}
  		str += "}";
  		checkRep();
  		return str;
  	}
}
