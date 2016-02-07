import java.awt.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.*;

public class sosim extends Frame {

	BufferedImage image;
    Graphics buffer;
    
    // initial width and height of window
    int width=1000;
    int height=800;
    
    // how many orbs are there
    public static int amountorbs=9;
    
    // array of orbs
    static orb[] body = new orb[amountorbs];
    
    // Definition of some constants
	public static final double GAMMA=6.674E-11;
	public static final double AE=149597870700.0;

	// time passed in simulation
	public static double time=0;
	
	// in which time calculates
	public static double delta=1;
	
	public sosim() {
  	  super( "galasim" );
  	  this.setSize(width,height);
  	  this.setVisible(true);
  	  this.setResizable(false);
  	  this.setBackground(Color.black);
    
  	  image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
 	  buffer = image.getGraphics();
  	
      addWindowListener(
	    new WindowAdapter()
	    {
	      public void windowClosing( WindowEvent ev )
	      {
	        dispose();
	        System.exit( 0 );
	      }
	    }
	  );
	}

    public void update(Graphics g) {
	    paint(g);
	}

    public void paint( Graphics g )
	{
    	width=this.getWidth();
    	height=this.getHeight();
    	
		buffer.setColor(Color.black);
		buffer.fillRect(0, 0, width, height);

		buffer.setColor(Color.white);
		buffer.drawString(String.valueOf(Math.round(sosim.time))+" Days", 10, 60);
		
		int i,x,y;

		// draw all orbs
		for (i=0;i<amountorbs;i++) {
		
			x=(int)(width/2+(body[i].position.x/AE*200));
			y=(int)(height/2+(body[i].position.y/AE*200));
	
			buffer.setColor(body[i].color);
			buffer.fillArc(x,y,3,3,0,360);
		}

		g.drawImage(image, 0, 0, null);
		
	}
	
	// main method
	public static void main(String[] args) {

  		sosim sosim = new sosim();

  		// used for loop
  		int i,j;
  		
  		// Values taken from nasa.gov site. Date is 27.06.2013 00:00, will take actual values later :)
		body[0]= new orb("Sun",1.9891E30, Color.white, 0,0,0,0,0,0);
		body[1]= new orb("Mercury",3.302E23, Color.orange, -1.465480716710949E-01,-4.418953231293142E-01,-2.265957000223763E-02,2.105240917748310E-02,-7.456813786474758E-03,-2.540856877789417E-03);
		body[2]= new orb("Venus",48.685E23, Color.green, -6.424031195897032E-01,3.197431279730666E-01,4.145622109510711E-02,-9.099909631951507E-03,-1.820433348113340E-02,2.757375814619214E-04);
		body[3]= new orb("Earth",5.9736E24,Color.blue, 9.448724943017101E-02,-1.012109403984998E+00,2.848131908362421E-05,1.684565838211787E-02,1.529585774473097E-03,4.836469039711925E-09);
		body[4]= new orb("Mars",6.4185E23, Color.red, 5.849925481307623E-01,1.395572917273793E+00,1.487611290514910E-02,-1.237513520057178E-02,6.598573495188453E-03,4.420726322192113E-04);
		body[5]= new orb("Jupiter",1898.13E24, Color.yellow, 9.138388037335735E-02,5.122663974709755E+00,-2.332005070601915E-02,-7.642412205156286E-03,4.928783348769734E-04,1.689801412744638E-04);
		body[6]= new orb("Saturn",5.68319E26, Color.lightGray, -7.540010716539238E+00,-6.304065689998531E+00,4.098038000540534E-01,3.270398159047236E-03,-4.293417354035670E-03,-5.570578741394238E-05);
		body[7]= new orb("Uranus",86.8103E24, Color.gray, 1.978345749120409E+01,3.229806669859318E+00,4.098038000540534E-01,-6.683046293613055E-04,3.697977117469523E-03,2.248162924927207E-05);
		body[8]= new orb("Neputne",102.41E24, Color.darkGray,2.680948346057039E+01,1.342536540628716E+01,-3.412512458891169E-01,1.378211498927596E-03,2.824711826035513E-03,-8.975394572267962E-05);

		sosim.repaint();

		while (true) {
			
			sosim.repaint();

			// clear all acceleration of all orbs
			for (i=0; i<amountorbs; i++) {
			
				body[i].accelerationclear();
		
			}
				
			// calculate all acceleration of all orbs based on gravitation
			for (i=0; i<amountorbs; i++) {
				
				for (j=0; j<amountorbs; j++) {
					
					// not for the orb itselfs
					if (i!=j) {
						body[i].acceleration(body[j]);
					}
					
				}
			}
			
			// move all orbs and calculate new velocity
			for (i=0; i<amountorbs; i++) {
				
				body[i].movement();
				body[i].velocity();
		
			}

			// increase time
			time+=delta/86400;
		}
	}
}
