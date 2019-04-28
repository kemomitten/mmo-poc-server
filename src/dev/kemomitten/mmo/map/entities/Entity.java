package dev.kemomitten.mmo.map.entities;

import dev.kemomitten.mmo.map.Sprite;

public class Entity extends Sprite{
	
	protected double dy=0,dx=0,speed=10;
	
	public Entity() {}
	public Entity(double x, double y, double w, double h) {
		super(x, y, w, h);
	}
	
	public void update(double delta) {
		x += (delta/1000)*dx*speed;
		y += (delta/1000)*dy*speed;
	}
	
	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	
	
	public String getState() {
		return super.getState()+","+dx+","+dy;
	}
	public boolean proccessState(String[] argv) {
		if (super.proccessState(argv)) {
			try {
				dx = Double.parseDouble(argv[4]);
				dy = Double.parseDouble(argv[5]);
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}else {
			return false;
		}
	}
}
