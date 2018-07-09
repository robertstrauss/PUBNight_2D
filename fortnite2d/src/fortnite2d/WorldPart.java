package fortnite2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class WorldPart implements PhysicsAction{
	
	/**
	 * Bounding box of part.
	 * Keeps track of position and dimensions.
	 */
	Rectangle2D boundingBox = new Rectangle2D.Double(0, 0, 10, 10);
	
	/**
	 * Velocity of part
	 */
	Point2D velocity = new Point2D.Double(1, 0);
	
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
			double px = p.getMass()*p.getVelocity().getX();
			double py = p.getMass()*p.getVelocity().getY();
			double thispx = getMass()*getVelocity().getX();
			double thispy = getMass()*getVelocity().getY();
			double fx = px-thispx;
			double fy = py-thispy;
			//accelerate(fx ,fy);
			//double v1i = Math.hypot(getVelocity().getX(), getVelocity().getY());
			//double v2i = Math.hypot(p.getVelocity().getX(), p.getVelocity().getY());
			//double v1f = ((mass-p.mass)/(mass+p.mass)*v1i)+((2*p.mass)/(mass+p.mass)*v2i);
			//v1f*=(1-physicsSettings.drag);
			//p.accelerate();
		}
		
		/**
		 * Increments position by velocity
		 */
		@Override
		public void moving() {
			//update();
		}

		@Override
		protected boolean intersecting(WorldPart p) {
			return false;
		}
		
		/**
		 * Draws parts
		 * @param g
		 */
		void display(Graphics g) {
			g.setColor(color);
			g.drawRect((int) getBoundingBox().getX(), (int) getBoundingBox().getY(), (int) getBoundingBox().getWidth(), (int) getBoundingBox().getHeight());
		}
	
	}
	
	/**
	 * Part that does interact with other parts that interact.
	 * Uses elastic equations to bounce.
	 * 
	 * @author shelbs
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
			double px = p.getMass()*p.getVelocity().getX();
			double py = p.getMass()*p.getVelocity().getY();
			double thispx = getMass()*getVelocity().getX();
			double thispy = getMass()*getVelocity().getY();
			double fx = thispx-px;
			double fy = thispy-py;
			accelerate(fx ,fy);
			/**double v1i = Math.hypot(getVelocity().getX(), getVelocity().getY());
			double v2i = Math.hypot(p.getVelocity().getX(), p.getVelocity().getY());
			double v1f = ((mass-p.mass)/(mass+p.mass)*v1i)+((2*p.mass)/(mass+p.mass)*v2i);
			//v1f*=(1-physicsSettings.drag);
			p.accelerate(v1i, v2i);
			/*setVelocity(-(getBoundingBox().getX()-p.getBoundingBox().getX())*v1f, -(getBoundingBox().getY()-p.getBoundingBox().getY())*v1f);
			//setVelocity(0, 0);*/
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
			this.boundingBox = boundingBox;
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
				System.out.println("right");
				accelerate(speed, 0);
			}
			if (key == KeyEvent.VK_UP) {
				accelerate(0, -speed);
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
	
	public void accelerate(Point2D force) {
		setVelocity(getVelocity().getX()+force.getX(), getVelocity().getY()+force.getY());
	}
	
	public void accelerate(double x, double y) {
		setVelocity(getVelocity().getX()+x, getVelocity().getY()+y);
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

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
	
	/**
	 * Reaction that happens when this part touched part p
	 * @param p part this this part is touching
	 */
	protected abstract void interact(WorldPart p);//called when part is touched by another part
	
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
		interact(p);
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
