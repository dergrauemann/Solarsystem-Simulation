import java.awt.Color;

public class orb {

	// Definition of needed variables of the orb

    // Definition of some constants
	public final static double GAMMA=6.674E-11;
	public final double AE=149597870700.0;

	// Name
	String name;
	
	// Mass
	double mass;
	
	// which kind of type: 0=sun, 1=orb, 2=moon, 3=asteroid, 4=comet, 5=probe
	int type;
	
	// Color
	Color color;
	
	// actual Position 
	values position = new values(0,0,0);
	
	// Velocity
	values velocity = new values(0,0,0);
	
	// Acceleration
	values acceleration = new values(0,0,0);

	// new Position
	values newposition = new values(0,0,0);
	
	// is orb the sun ?
	public boolean issun(){
		if (this.type==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// is orb a planet ?
	public boolean isplanet(){
		if (this.type==1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// is orb a moon ?
	public boolean ismoon(){
		if (this.type==2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// is orb a asteroid ?
	public boolean isasteroid(){
		if (this.type==3) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// is orb a comet ?
	public boolean iscomet(){
		if (this.type==4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// is orb a probe ?
	public boolean isprobe(){
		if (this.type==5) {
			return true;
		}
		else {
			return false;
		}
	}
	
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
	
	// calculate acceleration between two orbs based on gravitation
	public void acceleration(orb second) {
		
		// both orbs are existent
		if ((this!=null) && (second!=null)) {
			
			// distance per axis
			double diffx=second.position.x-this.position.x;
			double diffy=second.position.y-this.position.y;
			double diffz=second.position.z-this.position.z;

			// distance in space
			double r=Math.sqrt(Math.pow(diffx,2)+Math.pow(diffy,2)+Math.pow(diffz,2));
			
			// acceleration based on gravitation
			double a=sosim.GAMMA*(second.mass/Math.pow(r,2));
			
			// acceleration per axis
			this.acceleration.x=this.acceleration.x+a*diffx/r;
			this.acceleration.y=this.acceleration.y+a*diffy/r;
			this.acceleration.z=this.acceleration.z+a*diffz/r;
		}
	}
	
	// clears all acceleration before calculation
	public void accelerationclear() {
		
		// orb is existent
		if (this!=null) {
			
			this.acceleration.x=0;
			this.acceleration.y=0;
			this.acceleration.z=0;
		}
	}

	// calculate new position of orb based on acceleration, velocity and actual position
	public void movement(double delta) {
		
		// orb is existent
		if (this!=null) {

			// new position is 1/2*acceleration*t^2+velocity*t+position
			this.position.x=this.position.x+0.5*this.acceleration.x*delta*delta+this.velocity.x*delta;
			this.position.y=this.position.y+0.5*this.acceleration.y*delta*delta+this.velocity.y*delta;
			this.position.z=this.position.z+0.5*this.acceleration.z*delta*delta+this.velocity.z*delta;
		}
	}
		
	// calculation of new velocity
	public void velocity(double delta) {

		// orb is existent
		if (this!=null) {
			
			// new velocity is actual velocity + velocity through acceleration*time
			this.velocity.x=this.acceleration.x*delta+this.velocity.x;
			this.velocity.y=this.acceleration.y*delta+this.velocity.y;
			this.velocity.z=this.acceleration.z*delta+this.velocity.z;
		}
	}
 	
	// standard Constructor
	public orb() {

		name="Noname";
		mass=1;
		type=0;
	}
	
	// Constructor with some Parameters (Position, Velocity)
	public orb(String pname, double pmass, int ptype, Color pcolor,double px,double py,double pz, double pvx, double pvy, double pvz) {

		name=pname;
		mass=pmass;
		color=pcolor;
		type=ptype;
		
		position.x=AE*px;
		position.y=AE*py;
		position.z=AE*pz;
		
		velocity.x=AE*pvx/86400;
		velocity.y=AE*pvy/86400;
		velocity.z=AE*pvz/86400;
	}

	// Constructor with full Parameters (Position, Velicity, Acceleration)
	public orb(String pname, double pmass, int ptype, Color pcolor,double px,double py,double pz, double pvx, double pvy, double pvz, double pax,double pay,double paz) {

		name=pname;
		mass=pmass;
		color=pcolor;
		type=ptype;
		
		position.x=AE*px;
		position.y=AE*py;
		position.z=AE*pz;
		
		velocity.x=AE*pvx/86400;
		velocity.y=AE*pvy/86400;
		velocity.z=AE*pvz/86400;
		
		acceleration.x=AE*pax;
		acceleration.y=AE*pay;
		acceleration.z=AE*paz;
	}
}
