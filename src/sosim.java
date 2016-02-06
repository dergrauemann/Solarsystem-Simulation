
public class sosim {

	// Definition of some constants
	public static final double GAMMA=6.674E-11;
	public static final double AE=149597870700.0;

	// in which time t in seconds
	public static int t=1;
	
	// main method
	public static void main(String[] args) {

		// Values taken from nasa.gov site. Date is 27.06.2013 00:00, will take actual values later :)
		orb sun= new orb("Sun",1.9891E30, 0,0,0,0,0,0);
		orb earth= new orb("Earth",5.9736E24,AE*9.448724943017101E-02,AE*-1.012109403984998E+00,AE*2.848131908362421E-05,AE*1.684565838211787E-02/86400,AE*1.529585774473097E-03/86400,AE*4.836469039711925E-09/86400);

		while (true) {
			
		}
		
	}
	
}
