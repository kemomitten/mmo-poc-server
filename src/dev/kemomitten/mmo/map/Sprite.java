package dev.kemomitten.mmo.map;

public class Sprite {
	
	protected double x=0,y=0,w=0,h=0;
	
	public Sprite() {}
	public Sprite(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getW() {
		return w;
	}
	public double getH() {
		return h;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setW(double w) {
		this.w = w;
	}
	public void setH(double h) {
		this.h = h;
	}
	
	public String getState() {
		return x+","+y+","+w+","+h;
	}
	public boolean proccessState(String[] argv) {
		try {
			this.x = Double.parseDouble(argv[0]);
			this.y = Double.parseDouble(argv[1]);
			this.w = Double.parseDouble(argv[2]);
			this.h = Double.parseDouble(argv[3]);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
