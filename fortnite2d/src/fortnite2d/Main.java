package fortnite2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;


public class Main extends JFrame{
	
	static World w;
	
	Main(World w){
		this.w= w;
		WorldPart p = new WorldPart.Stationary(new Rectangle2D.Float(0, 200, 1000, 1000));
		p.setColor(new Color(0, 0, 255));
		p.setVelocity(0, 0);
		p.setMass(100000);
		this.w.addPart(p);
		this.w.addPart(new WorldPart.Dynamic(new Rectangle2D.Float(200, 100, 10, 10)));
		this.w.addPart(new WorldPart.Stationary(new Rectangle2D.Float(100, 100, 10, 10)));
		WorldPart.Player plyr = new WorldPart.Player(new Rectangle2D.Float(0, 0, 10, 10));
		plyr.setColor(new Color(0, 255, 255));
		plyr.setVelocity(0, 0);
		plyr.setName("player");
		this.w.addPart(plyr);
		
		
		addKeyListener(plyr);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public static void createAndShowGUI(){
		
		Main frame = new Main(new World());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400, 400));
		
		frame.add(w);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				createAndShowGUI();
			}
			
		});
	}
}
