import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Checkbox;
import java.awt.CheckboxMenuItem;
import java.awt.RadioButton;
import java.awt.RadioButtonMenuItem;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.KeyStroke;

public class sosim extends Frame implements KeyListener  {

	BufferedImage image;
    Graphics buffer;
    
    // initial width and height of window
    public int width=1000;
    public int height=800;
    
    // how many orbs are there
    public int amountorbs=11;
    
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

	// centerpoint of view
	public int center=0;
	
	// time between tracking
	public int timetrack=21600;
	
	// rotation of the axis
	public int rotatex=90;
	public int rotatey=0;
	
	// show names
	public boolean shownames=true;
	
	// show tracks
	public boolean showtracks=true;

	// show moons
	public boolean showmoons=false;
	
	// pause or not
	public boolean pause=false;

	// 2d Array of tracked positions
	public values[][] track = new values[amountorbs][amounttracks];
	
	// Array of points to draw
	public int[] pointx = new int[amounttracks];
	public int[] pointy = new int[amounttracks];
	
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
  	  
  	  // add menubar
  	  MenuBar bar = new MenuBar();
  	  
  	  // add menu
  	  Menu menucenter = new Menu("Center");

	  ButtonGroup centergroup = new ButtonGroup();

  	  // add menuitems to menu
  	  RadioButtonMenuItem itemsun = new RadioButtonMenuItem("Sun");
  	  itemsun.setSelected(true);
  	  centergroup.add(itemsun);
  	  menucenter.add(itemsun);

  	  RadioButtonMenuItem itemmercury = new RadioButtonMenuItem("Mercury");
  	  centergroup.add(itemmecury);
  	  menucenter.add(itemmercury);

  	  RadioButtonMenuItem itemvenus = new RadioButtonMenuItem("Venus");
  	  centergroup.add(itemvenus);
  	  menucenter.add(itemvenus);

  	  RadioButtonMenuItem itemearth = new RadioButtonMenuItem("Earth");
  	  centergroup.add(itemearth);
  	  menucenter.add(itemearth);

  	  RadioButtonMenuItem itemmars = new RadioButtonMenuItem("Mars");
  	  centergroup.add(itemmars);
  	  menucenter.add(itemmars);

  	  RadioButtonMenuItem itemjupiter = new RadioButtonMenuItem("Jupiter");
  	  centergroup.add(itemjupiter);
  	  menucenter.add(itemjupiter);

  	  RadioButtonMenuItem itemsaturn = new RadioButtonMenuItem("Saturn");
  	  centergroup.add(itemsaturn);
  	  menucenter.add(itemsaturn);

  	  RadioButtonMenuItem itemuranus = new RadioButtonMenuItem("Uranus");
  	  centergroup.add(itemuranus);
  	  menucenter.add(itemuranus);

  	  RadioButtonMenuItem itemneptune = new RadioButtonMenuItem("Neptune");
  	  centergroup.add(itemneptune);
  	  menucenter.add(itemneptune);

  	  // add menu
  	  Menu menushow = new Menu("Show");

  	  // add menuitems to menu
  	  CheckboxMenuItem itemnames = new CheckboxMenuItem("Names");
  	  itemnames.setState(shownames);
  	  menushow.add(itemnames);

  	  CheckboxMenuItem itemmoons = new CheckboxMenuItem("Moons");
  	  itemmoons.setState(showmoons);
  	  menushow.add(itemmoons);

  	  CheckboxMenuItem itemtracks = new CheckboxMenuItem("Tracks");
  	  itemtracks.setState(showtracks);
  	  menushow.add(itemtracks);

  	  // add menu
  	  Menu menuaction = new Menu("Action");

  	  // add menuitems to menu
  	  MenuItem itemrotatexinc = new MenuItem("RotateX increase");
  	  menuaction.add(itemrotatexinc);

  	  MenuItem itemrotatexdec = new MenuItem("RotateX -decrease");
  	  menuaction.add(itemrotatexdec);

  	  menuaction.addSeparator();

  	  // add menuitems to menu
  	  MenuItem itemrotateyinc = new MenuItem("RotateY increase");
  	  menuaction.add(itemrotateyinc);

  	  MenuItem itemrotateydec = new MenuItem("RotateY -decrease");
  	  menuaction.add(itemrotateydec);

  	  menuaction.addSeparator();

  	  // add menuitems to menu
  	  MenuItem itemzoomin = new MenuItem("Zoom in");
  	  menuaction.add(itemzoomin);

  	  MenuItem itemzoomout = new MenuItem("Zoom out");
  	  menuaction.add(itemzoomout);

  	  menuaction.addSeparator();

  	  // add menuitems to menu
  	  MenuItem itemtrackinc = new MenuItem("Tracking increase");
  	  menuaction.add(itemtrackinc);

  	  MenuItem itemtrackdec = new MenuItem("Tracking decrease");
  	  menuaction.add(itemtrackdec);

  	  menuaction.addSeparator();

  	  // add menuitems to menu
  	  MenuItem itemspeedinc = new MenuItem("Speed increase");
  	  menuaction.add(itemspeedinc);

  	  MenuItem itemspeeddec = new MenuItem("Speed decrease");
  	  menuaction.add(itemspeeddec);

  	  // add menu to bar
  	  bar.add(menucenter);
  	  bar.add(menushow);
  	  bar.add(menuaction);
  	  
  	  // set bar to window
  	  this.setMenuBar(bar);
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
	        // Pos1 increase tracking time
	        case 36:	timetrack+=1000;
	        			if (timetrack>21600) {
	        				timetrack=21600;
	        			}
	        			break;
	        // End decrease tracking time
	        case 35:	timetrack-=1000;
	        			if (timetrack<1000) {
	        				timetrack=1000;
	        			}
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
   	        // 0 for sun
	        case 48:	center=0;
 	        	        break;
   	        // 1 for mercury
   	        case 49:	center=1;
   	        			break;
	        // 2 for venus
	        case 50:	center=2;
	        			break;
   	        // 3 for earth
	        case 51:	center=3;
	        			break;
   	        // 4 for mars
	        case 52:	center=4;
	        			break;
   	        // 5 for jupiter
	        case 53:	center=5;
	           			break;
	        // 6 for saturn
	        case 54:	center=6;
	        			break;
	        // 7 for uranus
	        case 55:	center=7;
	        			break;
	        // 8 for neptune
   	        case 56:	center=8;
   	        			break;
	        // M for Moons on/off
	        case 77:	if (showmoons) {
	        				showmoons=false;
	        			} else {
	        				showmoons=true;
	        			}
	        			break;
   	        // N for Names on/off
   	        case 78:	if (shownames) {
   	        				shownames=false;
   	        			} else {
   	        				shownames=true;
   	        			}
   	        			break;
   	        // P for pauses Simulation
   	        case 80:	if (pause) {
   	        				pause=false;
   	        			} else {
   	        				pause=true;
   	        			}
   	        			break;
	        // T for showing Tracks or not
	        case 84:	if (showtracks) {
	        				showtracks=false;
	        			} else {
	        				showtracks=true;
	        			}
	        			break;
   	        // keypad0 for sun
	        case 96:	center=0;
 	        	        break;
   	        // keypad1 for mercury
   	        case 97:	center=1;
   	        			break;
	        // keypad2 for venus
	        case 98:	center=2;
	        			break;
   	        // keypad3 for earth
	        case 99:	center=3;
	        			break;
   	        // keypad4 for mars
	        case 100:	center=4;
	        			break;
   	        // keypad5 for jupiter
	        case 101:	center=5;
	           			break;
	        // keypad6 for saturn
	        case 102:	center=6;
	        			break;
	        // keypad7 for uranus
	        case 103:	center=7;
	        			break;
	        // keypad8 for neptune
   	        case 104:	center=8;
   	        			break;
	        // Keypad + speeds up
		    case 107:	delta*=2;
		    			break;
		    // Keypad - slows down
		    case 109:	delta/=2;
		    			break;
        }
        repaint();
        System.out.println(ke.getKeyCode());
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
		buffer.drawString(String.valueOf(Math.round(time))+" Days", 10, height-120);
		buffer.drawString(String.valueOf(Math.round(zoom/200))+"x Zoom", 10, height-100);
		buffer.drawString(String.valueOf(Math.round(delta))+" Delta", 10, height-80);
		buffer.drawString(String.valueOf(Math.round(rotatex))+" Rotation X", 10, height-60);
		buffer.drawString(String.valueOf(Math.round(rotatey))+" Rotation Y", 10, height-40);
		buffer.drawString(String.valueOf(Math.round(timetrack/216))+"% Timetrack", 10, height-20);
		
		int i,j,x,y;
		double px,py,pz,xx,yy;
		
		x=0;
		y=0;

		// draw all orbs
		for (i=0; i<amountorbs; i++) {

			buffer.setColor(body[i].color);

			for (j=0; j<amounttracks;j++) {

				// calculate position in space
				px=(track[i][j].x-track[center][0].x)/AE*zoom;
				py=(track[i][j].y-track[center][0].y)/AE*zoom;
				pz=(track[i][j].z-track[center][0].z)/AE*zoom;
					
				// calculate position on screen with rotation of the axis
				xx=((px*Math.cos(rotatey*Math.PI/180)+pz*Math.sin(rotatey*Math.PI/180)));
				yy=((py*Math.sin(rotatex*Math.PI/180)+pz*Math.cos(rotatex*Math.PI/180)));
	
				// center the positions in window
				x=(int)(width/2+xx);
				y=(int)(height/2+yy);
	
				pointx[j]=x;
				pointy[j]=y;
			}

			buffer.setColor(body[i].color);

			if (showtracks) {

				if ((body[i].ismoon() && showmoons) ||
					(body[i].isplanet()) ||
					(body[i].isprobe()) ||
					(body[i].issun()) ) {

					buffer.drawPolyline(pointx, pointy, amounttracks-1);
				}
			}

			if ((body[i].ismoon() && showmoons) ||
					(body[i].isplanet()) ||
					(body[i].isprobe()) ||
					(body[i].issun()) ) {

				// draw orb
				buffer.fillArc(pointx[0]-1,pointy[0]-1,3,3,0,360);
	
				if (shownames)
		 		{
					buffer.drawString(body[i].name,pointx[0]+5,pointy[0]);
				}
			}

			// if Pause show it :)
			if (pause) {
				buffer.setColor(Color.white);
				buffer.drawString("PAUSED",(int)width/2-30,80);
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
  		int i,j,ttt;
  		
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
		sosim.body[9]= new orb("Moon",734.9E20, 2, Color.lightGray,9.631176569191406E-02,-1.016057414360765E+00,1.788513804213026E-04,1.723781465974138E-02,2.010805409466552E-03,-5.772724084383609E-06);
		sosim.body[10]= new orb("Voyager 1",733, 5, Color.magenta,-2.637363252283945E+01,-9.864630535523268E+01,7.140822835838159E+01,-1.203564481477777E-03,-7.911988482147065E-03,5.716307810761908E-03);

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
		
		ttt=0;
		
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

				// when to track the current position 
				if (ttt>sosim.timetrack) {
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
					ttt=0;
				}
				
				ttt++;
				
				// increase time
				sosim.time+=sosim.delta/86400;
			}
		}
	}
}
