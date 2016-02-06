
public class orb {

	// Definition of needed variables of the orb

	// Name
	String name;
	
	// Mass
	double mass;
	
	// actual Position 
	values position = new values(0,0,0);
	
	// Velocity
	values velocity = new values(0,0,0);
	
	// Acceleration
	values acceleration = new values(0,0,0);

	// new Position
	values newposition = new values(0,0,0);
	
	// calculate distance between two orbs
	public double distance(orb second) {
		
		double distance=0.0;
		
		// both orbs are existent
		if ((this!=null) && (second!=null)) {

			// calculate distance between both orbs
			distance=Math.sqrt(Math.pow((this.position.x-second.position.x),2)+Math.pow((this.position.y-second.position.y),2)+Math.pow((this.position.z-second.position.z),2));				
						
		}
		return distance;
	}
	
	// calculate distancex between two orbs
	public double distancex(orb second) {
		
		double distancex=0.0;
		
		// both orbs are existent
		if ((this!=null) && (second!=null)) {

			// calculate distance between both orbs
			distancex=Math.sqrt(Math.pow((this.position.x-second.position.x),2));				
						
		}
		return distancex;
	}
	
	// calculate distance between two orbs
	public double distancey(orb second) {
		
		double distancey=0.0;
		
		// both orbs are existent
		if ((this!=null) && (second!=null)) {

			// calculate distance between both orbs
			distancey=Math.sqrt(Math.pow((this.position.y-second.position.y),2));				
						
		}
		return distancey;
	}
	
	// calculate distance between two orbs
	public double distancez(orb second) {
		
		double distancez=0.0;
		
		// both orbs are existent
		if ((this!=null) && (second!=null)) {

			// calculate distance between both orbs
			distancez=Math.sqrt(Math.pow((this.position.z-second.position.z),2));				
						
		}
		return distancez;
	}
	
	// calculate acceleration between two orbs based on gravitation
	public void acceleration(orb second) {
		
		if ((this!=null) && (second!=null)) {
			
			double distance=this.distance(second);

			// Calculation of new accelartion is old acceleration + acceleration of gravitation from second orb "parted" to the axes (x,y,z)
			this.acceleration.x=this.acceleration.x+sosim.GAMMA*(second.mass/Math.pow(distance,2))*(this.distancex(second)/distance);
			this.acceleration.y=this.acceleration.y+sosim.GAMMA*(second.mass/Math.pow(distance,2))*(this.distancey(second)/distance);
			this.acceleration.z=this.acceleration.z+sosim.GAMMA*(second.mass/Math.pow(distance,2))*(this.distancez(second)/distance);
			
		}
		
	}

	// calculate new position of orb based on acceleration, velocity and actual position
	public void movement() {
		
		if (this!=null) {
			
			// new position is 1/2*acceleration*t^2+velocity*t+position
			this.position.x=0.5*this.acceleration.x*Math.pow(sosim.t, 2)+this.velocity.x*sosim.t+this.position.x;
			this.position.y=0.5*this.acceleration.y*Math.pow(sosim.t, 2)+this.velocity.y*sosim.t+this.position.y;
			this.position.z=0.5*this.acceleration.z*Math.pow(sosim.t, 2)+this.velocity.z*sosim.t+this.position.z;

		}
	}
		
	// calculation of new velocity
	public void velocity() {
		
		if (this!=null) {

			this.velocity.x=this.acceleration.x*sosim.t+this.velocity.x;
			this.velocity.y=this.acceleration.y*sosim.t+this.velocity.y;
			this.velocity.z=this.acceleration.z*sosim.t+this.velocity.z;

		}
	}
 	
	// standard Constructor
	public orb() {
		name="Noname";
		mass=1;
	}
	
	// Constructor with some Parameters (Position, Velocity)
	public orb(String pname, double pmass, double px,double py,double pz, double pvx, double pvy, double pvz) {
		name=pname;
		mass=pmass;
		position.x=px;
		position.y=py;
		position.z=pz;
		velocity.x=pvx;
		velocity.y=pvy;
		velocity.z=pvz;
	}

	// Constructor with full Parameters (Position, Velicity, Acceleration)
	public orb(String pname, double pmass, double px,double py,double pz, double pvx, double pvy, double pvz, double pax,double pay,double paz) {
		name=pname;
		mass=pmass;
		position.x=px;
		position.y=py;
		position.z=pz;
		velocity.x=pvx;
		velocity.y=pvy;
		velocity.z=pvz;
		acceleration.x=pax;
		acceleration.y=pay;
		acceleration.z=paz;
	}
}
