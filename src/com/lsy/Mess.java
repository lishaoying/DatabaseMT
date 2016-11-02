package com.lsy;

import java.util.*;

public class Mess {
	String database;
	String table;
	int x,y;
	int width1,width2,height;
	public int getWidth1() {
		return width1;
	}
	public void setWidth1(int width1) {
		this.width1 = width1;
	}
	public int getWidth2() {
		return width2;
	}
	public void setWidth2(int width2) {
		this.width2 = width2;
	}
	HashMap<String,String> hm;
	ShowData sd;
	public ShowData getSd() {
		return sd;
	}
	public void setSd(ShowData sd) {
		this.sd = sd;
	}
	public HashMap<String, String> getHm() {
		return hm;
	}
	public void setHm(HashMap<String, String> hm) {
		this.hm = hm;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
