package fortnite2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public abstract class WorldPart implements PhysicsAction{
	
	/**
	 * Bounding box of part.
	 * Keeps track of position and dimensions.
	 */
	Rectangle2D boundingBox = new Rectangle2D.Double(0, 0, 10, 10);
	Rectangle2D playerShape = new Rectangle2D.Double(0, 0, 10, 20);
	/**
	 * Velocity of part
	 */
	Point2D velocity = new Point2D.Double(1, 0);
	Point2D fapplied = new Point2D.Double(0, 0);
	/**
	 * parts rotation --unimplemented
	 * TODO implement rotation
	 */
	double rotation = 0;
	
	/**
	 * Name of part. Can be any string. Two parts can share a name
	 */
	String name = "Default";
	
	/**
	 * Parts parent part. --unimplemented
	 * TODO implement parenting
	 */
	WorldPart parent;
	
	/**
	 * Parts children. --unimplemented
	 * TODO implement parenting
	 */
	ArrayList<WorldPart> children = new ArrayList<WorldPart>();
	
	/**
	 * Parts color.
	 */
	Color color = new Color(255, 100, 100);
	
	/**
	 * Parts mass in kg?
	 */
	float mass = 1;
	
	/**
	 * Layers that part can collide with other parts on. To collide parts must have same layers. --unimplemend
	 * TODO implement collision layers
	 */
	String collisionLayers = "abcdefghijklmnopqrstuvwxyz";
	
	/**
	 * Settings such as gravity and drag. Can be shared between parts.
	 */
	WorldVars physicsSettings = new WorldVars();
	
	/**
	 * Part that does not interact with other parts.
	 * 
	 * @author shelbs
	 *
	 */
	public static class Stationary extends WorldPart{
	
		Stationary(){
			setVelocity(0.0, 0.0);
		}
		
		Stationary(Rectangle2D boundingBox){
			this.boundingBox = boundingBox;
		}
		
		Stationary(String name){
			this.name = name;
		}
		
		Stationary(WorldPart parent){
			this.parent = parent;
		}

		protected void interact(WorldPart p) {
			double xbounce = 0.2;
			double ybounce = 0.2;
			Point2D v = new Point2D.Double(0, 0);
			if (p.boundingBox.getMinX() + 10 >= boundingBox.getMaxX() && boundingBox.getMaxX() >= p.boundingBox.getMinX()) {
				v = new Point2D.Double(v.getX() - xbounce, v.getY() + 0);
			}
			if (p.boundingBox.getMaxY() + 10 >= boundingBox.getMaxY() && boundingBox.getMaxY() >= p.boundingBox.getMinY()) {
				v = new Point2D.Double(v.getX() + 0, v.getY() - ybounce);
			}
			if (p.boundingBox.getMaxX() - 10 <= boundingBox.getMinX() && boundingBox.getMinX() <= p.boundingBox.getMaxX()) {
				v = new Point2D.Double(v.getX() + xbounce, v.getY() + 0);
			}
			if (p.boundingBox.getMaxY() - 10 <= boundingBox.getMinY() && boundingBox.getMinY() <= p.boundingBox.getMaxY()) {
				v = new Point2D.Double(v.getX() + 0, v.getY() + ybounce);
			}
			
			if (v != new Point2D.Double(0,0)) {
				setVelocity(v);
			}
			
			/*double bounce = -0.4;
			
			double m1 = p.getMass();
			double m2 = getMass();
			Point2D v1i = new Point2D.Double(p.getVelocity().getX(), p.getVelocity().getY());
			Point2D v2i = new Point2D.Double(getVelocity().getX(), getVelocity().getY());
			//double p1x = m1*v1ix;
			//double p1y = m1*v1iy;
			//double p2x = m2*v2ix;
			//double p2y = m2*v2iy;
			Point2D sig1 = new Point2D.Float(0, 1); //(int) (v1i.getX()/Math.abs(v1i.getX())), (int) -(v1i.getY()/Math.abs(v1i.getY())));
			Point2D sig2 = new Point2D.Float(0, 1); //(int) (v2i.getX()/Math.abs(v2i.getX())), (int)  (v2i.getY()/Math.abs(v2i.getY())));
			
			if (this.boundingBox.getMinY() >= p.boundingBox.getMaxY()) {
				bounce = -0.5;
			}
			if (this.boundingBox.getMaxY() <= p.boundingBox.getMinY()) {
				bounce = 0.5;
			}
			
			Point2D vc = new Point2D.Double(((v1i.getX()*m1+v2i.getX()*m2)/2),
											((v1i.getY()*m1+v2i.getY()*m2)/2));
			
			Point2D v1f = new Point2D.Double((vc.getX() - (v1i.getX() - vc.getX())),
											 (vc.getY() - (v1i.getY() - vc.getY()))+bounce);
			Point2D v2f = new Point2D.Double((vc.getX() - (v2i.getX() - vc.getX())),
											 (vc.getY() - (v2i.getY() - vc.getY()))+bounce);
			
			
			//Point2D dv1 = new Point2D.Double(v1f.getX(), v1f.getY());
			//Point2D dv2 = new Point2D.Double(v2f.getX(), v2f.getY());
			p.setVelocity(v1f);
			setVelocity(v2f);*/
			/**
			 * stationary only
			 */
			setVelocity(0, 0);
		}
		
		/**
		 * Increments position by velocity
		 */
		@Override
		public void moving() {
			//update(); // ----------------------------------------------------------------------------------------------------------------------------- ??????
		}

		/*@Override
		protected boolean intersecting(WorldPart p) {
			return false; // ------------------------------------------------------------------------------------------------------------------------------------ problem.
		}*/
		
		/**
		 * Draws parts
		 * @param g
		 */
		//void display(Graphics g) {
		//	g.setColor(color);
		//	g.drawRect((int) getBoundingBox().getX(), (int) getBoundingBox().getY(), (int) getBoundingBox().getWidth(), (int) getBoundingBox().getHeight());
		//}


	
	}
	
	/**
	 * Part that does interact with other parts that interact.
	 * Uses elastic equations to bounce.
	 * 
	 * @author shelbs, robert
	 *
	 */
	public static class Dynamic extends WorldPart{
	
		Dynamic(){
		}
		
		Dynamic(Rectangle2D boundingBox){
			this.boundingBox = boundingBox;
		}
		
		Dynamic(String name){
			this.name = name;
		}
		
		Dynamic(WorldPart parent){
			this.parent = parent;
		}
		
		protected void interact(WorldPart p) {
			double xbounce = 0.2;
			double ybounce = 0.2;
			Point2D v = new Point2D.Double(0, 0);
			if (p.boundingBox.getMinX() + 10 >= boundingBox.getMaxX() && boundingBox.getMaxX() >= p.boundingBox.getMinX()) {
				v = new Point2D.Double(v.getX() - xbounce, v.getY() + 0);
			}
			if (p.boundingBox.getMaxY() + 10 >= boundingBox.getMaxY() && boundingBox.getMaxY() >= p.boundingBox.getMinY()) {
				v = new Point2D.Double(v.getX() + 0, v.getY() - ybounce);
			}
			if (p.boundingBox.getMaxX() - 10 <= boundingBox.getMinX() && boundingBox.getMinX() <= p.boundingBox.getMaxX()) {
				v = new Point2D.Double(v.getX() + xbounce, v.getY() + 0);
			}
			if (p.boundingBox.getMaxY() - 10 <= boundingBox.getMinY() && boundingBox.getMinY() <= p.boundingBox.getMaxY()) {
				v = new Point2D.Double(v.getX() + 0, v.getY() + ybounce);
			}
			
			if (v != new Point2D.Double(0,0)) {
				setVelocity(v);
			}
			
			/*double bounce = -0.4;
			
			double m1 = p.getMass();
			double m2 = getMass();
			Point2D v1i = new Point2D.Double(p.getVelocity().getX(), p.getVelocity().getY());
			Point2D v2i = new Point2D.Double(getVelocity().getX(), getVelocity().getY());
			//double p1x = m1*v1ix;
			//double p1y = m1*v1iy;
			//double p2x = m2*v2ix;
			//double p2y = m2*v2iy;
			Point2D sig1 = new Point2D.Float(0, 1); //(int) (v1i.getX()/Math.abs(v1i.getX())), (int) -(v1i.getY()/Math.abs(v1i.getY())));
			Point2D sig2 = new Point2D.Float(0, 1); //(int) (v2i.getX()/Math.abs(v2i.getX())), (int)  (v2i.getY()/Math.abs(v2i.getY())));
			
			if (this.boundingBox.getMinY() >= p.boundingBox.getMaxY()) {
				bounce = -0.5;
			}
			if (this.boundingBox.getMaxY() <= p.boundingBox.getMinY()) {
				bounce = 0.5;
			}
			
			Point2D vc = new Point2D.Double(((v1i.getX()*m1+v2i.getX()*m2)/2),
											((v1i.getY()*m1+v2i.getY()*m2)/2));
			
			Point2D v1f = new Point2D.Double((vc.getX() - (v1i.getX() - vc.getX())),
											 (vc.getY() - (v1i.getY() - vc.getY()))+bounce);
			Point2D v2f = new Point2D.Double((vc.getX() - (v2i.getX() - vc.getX())),
											 (vc.getY() - (v2i.getY() - vc.getY()))+bounce);
			
			
			//Point2D dv1 = new Point2D.Double(v1f.getX(), v1f.getY());
			//Point2D dv2 = new Point2D.Double(v2f.getX(), v2f.getY());
			p.setVelocity(v1f);
			setVelocity(v2f);*/
		}
	}
	
	/**
	 * Player.
	 * Uses elastic equations to bounce.
	 * 
	 * @author robert
	 *
	 */
	public static class Player extends Dynamic implements KeyListener{
		
		float speed = 2;
		
		Player(){
		}
		
		Player(Rectangle2D boundingBox){
			this.boundingBox = playerShape;
		}
		
		Player(String name){
			this.name = name;
		}
		
		Player(WorldPart parent){
			this.parent = parent;
		}
	
		/**
		 * keyPressed from KeyListener.
		 * input.
		 * 
		 * arrow key input from the big gay moves the player
		 *  
		 * @author robert
		 */
		@Override
		public void keyPressed(KeyEvent ke) {
			int key = ke.getKeyCode();
			if (key == KeyEvent.VK_RIGHT) {
				accelerate(speed, 0);
			}
			if (key == KeyEvent.VK_UP) {
				setVelocity(0, -speed);
			}
			if (key == KeyEvent.VK_LEFT) {
				accelerate(-speed, 0);
			}
			if (key == KeyEvent.VK_DOWN) {
				//squat();
			}
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

	public Rectangle2D getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(Rectangle2D boundingBox) {
		this.boundingBox = boundingBox;
	}

	public void setPosition(Point2D position) {
		boundingBox.setRect(position.getX(), position.getY(), getBoundingBox().getWidth(), getBoundingBox().getHeight());
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}
	
	
	public void setVelocity(double x, double y) {
		this.velocity.setLocation(x, y);
	}
	public void setVelocity(float x, float y) {
		this.velocity.setLocation(x, y);
	}
	
	public void addVelocity(float x, float y) {
		setVelocity(getVelocity().getX()+x, getVelocity().getY()+y);
	}
	
	public void accelerate(Point2D force) {
		setVelocity(getVelocity().getX()+force.getX(), getVelocity().getY()+force.getY());
	}
	
	public void accelerate(double x, double y) {
		setVelocity(getVelocity().getX()+x, getVelocity().getY()+y);
	}
	/*public void force(Point2D f) {
		double fx = f.getX();
		double fy = f.getY();
		double fax = fapplied.getX();
		double fay = fapplied.getY();
		fapplied = new Point2D.Double(fax+fx, fay+fy);
	}
	public void force(double fx, double fy) {
		//double fx = f.getX();
		//double fy = f.getY();
		double fax = fapplied.getX();
		double fay = fapplied.getY();
		fapplied = new Point2D.Double(fax+fx, fay+fy);
	}*/
	/*public void applyForces() {
		accelerate(fapplied);
	}*/
	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	/*public Point2D getForce() {
		return (fapplied);
	}*/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WorldPart getParent() {
		return parent;
	}

	public void setParent(WorldPart parent) {
		this.parent = parent;
	}

	public WorldPart[] getChildren() {
		return children.toArray(new WorldPart[children.size()]);
	}

	public void addChild(WorldPart children) {
		this.children.add(children);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public WorldVars getPhysicsSettings() {
		return physicsSettings;
	}

	public void setPhysicsSettings(WorldVars physicsSettings) {
		this.physicsSettings = physicsSettings;
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	/**
	 * Reaction that happens when this part touched part p
	 * @param p part this this part is touching
	 */
	protected abstract void interact(WorldPart p);//called when part is touched by another part
	//protected abstract void rest(WorldPart p);//called when object as it rest pushing against another
	/**
	 * Test to see if this part is intersecting p
	 * @param p part that is being tested for intersection
	 * @return true when intersecting
	 */
	boolean intersecting(WorldPart p) {
		return boundingBox.intersects(p.getBoundingBox());
	}
	
	/**
	 * Test if intersecting any other parts in World w.
	 * 
	 * @param w World part belongs to
	 */
	void wallTest(World w) {
		for (WorldPart p : w.getParts()) {
			if (intersecting(p) && p.intersecting(this)) {
				interact(p);
			}
		}
	}
	
	/**
	 * Adds velocity to position
	 */
	void update() {
		double x = boundingBox.getX()+velocity.getX();
		double y = boundingBox.getY()+velocity.getY();
		boundingBox.setRect(x, y, boundingBox.getWidth(), boundingBox.getHeight());
		//applyForces();
		//fapplied = new Point2D.Double(0, 0);
	}
	
	/**
	 * Draws parts
	 * @param g
	 */
	void display(Graphics g) {
		g.setColor(color);
		g.fillRect((int) getBoundingBox().getX(), (int) getBoundingBox().getY(), (int) getBoundingBox().getWidth(), (int) getBoundingBox().getHeight());
	}
	
	/**
	 * Applys gravity to loose parts.
	 */
	@Override
	public void freeFalling() {
		accelerate(physicsSettings.gravity);
		//System.out.println("gravity");
	}
	/**
	 * 
	 */
	/**
	 * Increments position by velocity
	 */
	@Override
	public void moving() {
		update();
	}
	
	/**
	 * Causes interaction with part. Called when touched.
	 */
	@Override
	public void firstContact(WorldPart p) {
		Point2D push = p.fapplied;
		p.accelerate(push);
		p.setVelocity(getVelocity());
		System.out.print("first contact"); //Doesn't print!! first contact never occurs.
	}
	
	/**
	 * Causes interaction with part. Called while touched.
	 */
	@Override
	public void inContact(WorldPart p) {
		interact(p);
	}
	
	/**
	 * Causes interaction with part. Called after touched.
	 */
	@Override
	public void lastContact(WorldPart p) {
		interact(p);
	}
}
