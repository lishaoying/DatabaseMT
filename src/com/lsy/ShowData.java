package com.lsy;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class ShowData extends JFrame implements MouseListener{
	JTable t=null;
	MyTableModel tm=null;
	Mess m=new Mess();
	public EditData w=null;
	ShowData(Mess mess){
		//设定窗体的位置和大小
		int x=mess.getX();
		int y=mess.getY();
		int width=mess.getWidth1();
		int height=mess.getHeight();
		this.setLocation(x+width, y);
		
		//显示数据
		String database=mess.getDatabase();
		String table=mess.getTable();
		tm=new MyTableModel(database,table);
	    t=new JTable(tm);
		t.addMouseListener(this);
		//根据表的列数自动调整窗体的宽度
	    int w=tm.getColumnCount()*100;
		this.setSize(w, height);
		this.add(new JScrollPane(t));
		//重写Mess对象
		m.setDatabase(database);
		m.setTable(table);
		m.setX(x);
		m.setY(y+height);
		m.setWidth1(width);
		m.setWidth2(w);
		m.setHeight(height);
		m.setSd(mess.getSd());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2){  
			int row=t.getSelectedRow();
			HashMap<String,String> hm=new HashMap<String,String>();
			int cols=tm.getColumnCount();
			for(int i=0;i<cols;i++){
				hm.put(tm.getColumnName(i), (String)tm.getValueAt(row, i));
			}
			m.setHm(hm);
			if(w!=null){
				w.setVisible(false);
			}
			w=new EditData(m);
			w.setVisible(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
