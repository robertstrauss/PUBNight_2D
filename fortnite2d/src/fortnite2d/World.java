package fortnite2d;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class World extends JPanel{
	
	/**
	 * contains global settings for physics
	 */
	WorldVars settings = new WorldVars();
	
	/**
	 * all parts objects
	 */
	ArrayList<WorldPart> parts = new ArrayList<WorldPart>();
	
	/**
	 * custom listener that sets off events for entire world
	 */
	WorldListener wListener;
	
	/**
	 * determines if game is paused
	 * --unimplemented
	 */
	boolean paused = false;
	
	World(){
		wListener = new WorldListener(parts, this);
		//EventQueue.invokeLater(wListener);
	}
	
	void addPart(WorldPart p) {
		parts.add(p);
		wListener.setParts(parts);
	}
	
	public WorldVars getSettings() {
		return settings;
	}
	
	public void setSettings(WorldVars settings) {
		this.settings = settings;
	}
	
	public ArrayList<WorldPart> getParts() {
		return parts;
	}
	
	public void setParts(ArrayList<WorldPart> parts) {
		this.parts = parts;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.out.println("displaying");
		setBackground(Color.WHITE);
		for (WorldPart part : parts) {
			part.display(g);
		}
    }
	
}
