package fortnite2d;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JPanel;

public class WorldListener implements Runnable {
	
	private Thread t;
	
	ArrayList<WorldPart> parts = new ArrayList<WorldPart>();
	private ArrayList<WorldPart> partsOld = new ArrayList<WorldPart>();
	JPanel panel;
	
	int checkSpeed = 2;
	
	WorldListener(ArrayList<WorldPart> parts, JPanel panel){
		
		t = new Thread(this);
		t.start();
		
		this.parts = parts;
		this.partsOld = parts;
		this.panel = panel;
	}
	
	boolean freeFalling(int index) {
		WorldPart p = parts.get(index);
		for (int i = 0;i<parts.size();i++) {
			if (!p.equals(parts.get(i))) {
				if (p.intersecting(parts.get(i))) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	boolean moving(int index) {
		WorldPart p = parts.get(index);
		return p.velocity.getX()!=0 || p.velocity.getY()!=0;
	}
	
	boolean firstContact(int prindex, int index) {
		return (parts.get(index).intersecting(parts.get(prindex)) && !partsOld.get(index).intersecting(partsOld.get(prindex)));// && (p.intersecting(parts.get(index)) && !p.intersecting(partsOld.get(index)));
	}
	
	boolean inContact(int prindex, int index) {
		return (parts.get(index).intersecting(parts.get(prindex)) && partsOld.get(index).intersecting(partsOld.get(prindex)));// && (p.intersecting(parts.get(index)) && p.intersecting(partsOld.get(index)));
	}
	
	boolean lastContact(int prindex, int index) {
		return (!parts.get(index).intersecting(partsOld.get(prindex)) && partsOld.get(index).intersecting(partsOld.get(prindex)));// && (!p.intersecting(parts.get(index)) && p.intersecting(partsOld.get(index)));
	}

	@Override
	public void run() {
		
		long beforeTime, timeDiff, sleep;//some variables
		
		beforeTime = System.currentTimeMillis();//elapsed time
		
		boolean edited = false;
		
		while (true) {
			for (int i = 0;i<parts.size();i++) {
				
				if (freeFalling(i)) {
					parts.get(i).freeFalling();
					edited=true;
				}
				
				for (int j = 0;j<parts.size();j++) {
					if (i!=j) {
						
						if (firstContact(j, i)) {
							System.out.println("once");
							parts.get(i).firstContact(parts.get(j));
							edited=true;
						}
						
						if (inContact(j, i)) {
							parts.get(i).inContact(parts.get(j));
							edited=true;
						}
						
						if (lastContact(j, i)) {
							parts.get(i).lastContact(parts.get(j));
							edited=true;
						}
						
					}
				}
				
			}
			
			for (int i = 0;i<parts.size();i++) {
				if (moving(i)) {
					parts.get(i).moving();
					edited=true;
				}
			}
			
			if (edited==true) {
				panel.invalidate();
				panel.updateUI();
				edited = false;
			}
			
			partsOld = (ArrayList<WorldPart>) parts.clone();
			
			timeDiff = System.currentTimeMillis() - beforeTime;// time elapsed since last check
            sleep = 17 - timeDiff;//amount of time to sleep

            if (sleep < 0) {//avoid funny stuff
                sleep = 2;
            }
            
			try {
				Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
			
			beforeTime = System.currentTimeMillis();
			
			//System.out.println("running");
			
		}
		
	}

	public ArrayList<WorldPart> getParts() {
		return parts;
	}

	public void setParts(ArrayList<WorldPart> parts) {
		this.parts = parts;
		this.partsOld = parts;
	}

	public int getCheckSpeed() {
		return checkSpeed;
	}

	public void setCheckSpeed(int checkSpeed) {
		this.checkSpeed = checkSpeed;
	}
	
}
