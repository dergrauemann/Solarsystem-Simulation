import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.*;

public class sosim extends Frame implements KeyListener  {

	BufferedImage image;
    Graphics buffer;
    
    // initial width and height of window
    public int width=1000;
    public int height=800;
    
    // how many orbs are there
    public int amountorbs=9;
    
    // array of orbs
    public orb[] body = new orb[amountorbs];
    
    // how long to track the path
    public int amounttracks=1000;
    
    // Definition of some constants
	public final static double GAMMA=6.674E-11;
	public final double AE=149597870700.0;

	// time passed in simulation
	public double time=0;
	
	// in which time calculates
	public double delta=1;
	
	// which zoom
	public double zoom=200;

	// rotation of the axis
	public int rotatex=90;
	public int rotatey=0;
	
	// show names
	public boolean shownames=true;
	
	// pause or not
	public boolean pause=false;

	// show tracks
	public boolean showtracks=true;
	
	// 2d Array of tracked positions
	public values[][] track = new values[amountorbs][amounttracks];
	
	public sosim() {
  	  super( "sosim" );
  	  this.setSize(width,height);
  	  this.setVisible(true);
  	  this.setResizable(true);
  	  this.setBackground(Color.black);
    
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
  	  addKeyListener(this);
	}

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode())
        {
        	// Page up zooms in
	    	case 33: 	zoom*=2;
	    				break;
	    	// Page down zooms out
	        case 34: 	zoom/=2;
	        			break;
	        // Arrow up rotates up (x-axis)
	        case 38:	rotatex++;
	        			if (rotatex>359) {
	        				rotatex=0;
	        			}
	        			break;
	        // Arrow down rotates down (x-axis)
	        case 40:	rotatex--;
	        			if (rotatex<0) {
	        				rotatex=359;
	        			}
	        			break;
   	        // Arrow up rotates left (y-axis)
   	        case 37:	rotatey++;
						if (rotatey>359) {
							rotatey=0;
						}
   	        			break;
   	        // Arrow down rotates right (y-axis)
   	        case 39:	rotatey--;
						if (rotatey<0) {
							rotatey=359;
						}
   	        			break;
   	        // N for Names on/off
   	        case 78:	if (shownames) {
   	        				shownames=false;
   	        			} else {
   	        				shownames=true;
   	        			}
   	        			break;
   	        // P for Pauses Simulation
   	        case 80:	if (pause) {
   	        				pause=false;
   	        			} else {
   	        				pause=true;
   	        			}
   	        			break;
	        // Keypad + speeds up
		    case 107:	delta*=2;
		    			break;
		    // Keypad - slows down
		    case 109:	delta/=2;
		    			break;
        }
        repaint();
        // System.out.println(ke.getKeyCode());
    }

    public void keyReleased(KeyEvent ke) {
    }
	
    public void update(Graphics g) {
	    paint(g);
	}

    public void paint( Graphics g )
	{
      	width=this.getWidth();
    	height=this.getHeight();

    	image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
   	  	buffer = image.getGraphics();
    	
		buffer.setColor(Color.black);
		buffer.fillRect(0, 0, width, height);

		buffer.setColor(Color.white);
		buffer.drawString(String.valueOf(Math.round(time))+" Days", 10, 60);
		buffer.drawString(String.valueOf(Math.round(zoom))+" Zoom", 10, 80);
		buffer.drawString(String.valueOf(Math.round(delta))+" Delta", 10, 100);
		buffer.drawString(String.valueOf(Math.round(rotatex))+" Rotation X", 10, 120);
		buffer.drawString(String.valueOf(Math.round(rotatey))+" Rotation Y", 10, 140);
		
		int i,j,x,y;
		double px,py,pz,xx,yy;
		
		x=0;
		y=0;

		// draw all orbs
		for (i=0; i<amountorbs; i++) {

			for (j=0; j<amounttracks;j++) {

				// calculate position in space
				px=track[i][j].x/AE*zoom;
				py=track[i][j].y/AE*zoom;
				pz=track[i][j].z/AE*zoom;
					
				// calculate position on screen with rotation of the axis
				xx=((px*Math.cos(rotatey*Math.PI/180)+pz*Math.sin(rotatey*Math.PI/180)));
				yy=((py*Math.sin(rotatex*Math.PI/180)+pz*Math.cos(rotatex*Math.PI/180)));
	
				// center the positions in window
				x=(int)(width/2+xx);
				y=(int)(height/2+yy);
	
				buffer.setColor(body[i].color);
				buffer.fillArc(x,y,3,3,0,360);
				
				// Show Names
				if ((shownames) && (j==0))
		 		{
		 			buffer.drawString(body[i].name,x+5,y);
		 		}
			}
		}
		

		g.drawImage(image, 0, 0, null);

		image.flush();
		buffer.dispose();
		g.dispose();
		
	}
	
	// main method
	public static void main(String[] args) {

  		sosim sosim = new sosim();

  		// used for loop
  		int i,j;
  		
  		// Values taken from nasa.gov site. Date is 27.06.2013 00:00, will take actual values later :)
		sosim.body[0]= new orb("Sun",1.9891E30, 0, Color.white, 0,0,0,0,0,0);
		sosim.body[1]= new orb("Mercury",3.302E23, 1, Color.orange, -1.465480716710949E-01,-4.418953231293142E-01,-2.265957000223763E-02,2.105240917748310E-02,-7.456813786474758E-03,-2.540856877789417E-03);
		sosim.body[2]= new orb("Venus",48.685E23, 1, Color.green, -6.424031195897032E-01,3.197431279730666E-01,4.145622109510711E-02,-9.099909631951507E-03,-1.820433348113340E-02,2.757375814619214E-04);
		sosim.body[3]= new orb("Earth",5.9736E24, 1, Color.blue, 9.448724943017101E-02,-1.012109403984998E+00,2.848131908362421E-05,1.684565838211787E-02,1.529585774473097E-03,4.836469039711925E-09);
		sosim.body[4]= new orb("Mars",6.4185E23, 1, Color.red, 5.849925481307623E-01,1.395572917273793E+00,1.487611290514910E-02,-1.237513520057178E-02,6.598573495188453E-03,4.420726322192113E-04);
		sosim.body[5]= new orb("Jupiter",1898.13E24, 1, Color.yellow, 9.138388037335735E-02,5.122663974709755E+00,-2.332005070601915E-02,-7.642412205156286E-03,4.928783348769734E-04,1.689801412744638E-04);
		sosim.body[6]= new orb("Saturn",5.68319E26, 1, Color.lightGray, -7.540010716539238E+00,-6.304065689998531E+00,4.098038000540534E-01,3.270398159047236E-03,-4.293417354035670E-03,-5.570578741394238E-05);
		sosim.body[7]= new orb("Uranus",86.8103E24, 1, Color.gray, 1.978345749120409E+01,3.229806669859318E+00,4.098038000540534E-01,-6.683046293613055E-04,3.697977117469523E-03,2.248162924927207E-05);
		sosim.body[8]= new orb("Neptune",102.41E24, 1, Color.darkGray,2.680948346057039E+01,-1.342536540628716E+01,-3.412512458891169E-01,1.378211498927596E-03,2.824711826035513E-03,-8.975394572267962E-05);

		sosim.repaint();

		// "fill" all tracks with first position
		for (i=0; i<sosim.amountorbs; i++) {
			for (j=0; j<sosim.amounttracks; j++) {
				sosim.track[i][j]= new values(0,0,0);
				sosim.track[i][j].x=sosim.body[i].position.x;
				sosim.track[i][j].y=sosim.body[i].position.y;
				sosim.track[i][j].z=sosim.body[i].position.z;
			}
		}
		
		while (true) {
			
			sosim.repaint();

			if (!sosim.pause) {

				// clear all acceleration of all orbs
				for (i=0; i<sosim.amountorbs; i++) {
				
					sosim.body[i].accelerationclear();
			
				}
					
				// calculate all acceleration of all orbs based on gravitation
				for (i=0; i<sosim.amountorbs; i++) {
					
					for (j=0; j<sosim.amountorbs; j++) {
						
						// not for the orb itselfs
						if (i!=j) {
							sosim.body[i].acceleration(sosim.body[j]);
						}
						
					}
				}
				
				// move all orbs and calculate new velocity
				for (i=0; i<sosim.amountorbs; i++) {
					
					sosim.body[i].movement(sosim.delta);
					sosim.body[i].velocity(sosim.delta);
			
				}

				// "shift" all tracks
				for (i=0; i<sosim.amountorbs; i++) {
					
					for (j=sosim.amounttracks-1;j>0;j--) {
						sosim.track[i][j].x=sosim.track[i][j-1].x;
						sosim.track[i][j].y=sosim.track[i][j-1].y;
						sosim.track[i][j].z=sosim.track[i][j-1].z;
					}
					sosim.track[i][0].x=sosim.body[i].position.x;
					sosim.track[i][0].y=sosim.body[i].position.y;
					sosim.track[i][0].z=sosim.body[i].position.z;
				}

				// increase time
				sosim.time+=sosim.delta/86400;
			}
		}
	}
}
