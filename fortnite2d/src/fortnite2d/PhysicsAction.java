package fortnite2d;

import java.awt.geom.Point2D;

public interface PhysicsAction {
	
	/**
	 * Called when not touching any other parts
	 */
	void freeFalling();
	
	/**
	 * Called when velocity is not 0 to update position
	 */
	void moving();
	
	/**
	 * Called during first frame part touches
	 */
	void firstContact(WorldPart p);
	
	/** 
	 * Called while two parts are touching
	 */
	void inContact(WorldPart p);
	
	/**
	 * Called on last frame two parts will touch
	 */
	void lastContact(WorldPart p);
	
}
