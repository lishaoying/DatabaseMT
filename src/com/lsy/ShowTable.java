package com.lsy;
import javax.swing.*;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;

public class ShowTable extends JFrame implements ActionListener,MouseListener{
	JComboBox cmbDatabase=new JComboBox();//显示数据库名的下拉列表框
	JList lstTable=new JList();//显示表名
	ShowData w;
	ShowTable(){
		this.setSize(200, 300);
		//表名可能比较多，加入滚动条
		JScrollPane sp=new JScrollPane(lstTable);
		//注册事件
		cmbDatabase.addActionListener(this);
		lstTable.addMouseListener(this);
		
		this.setLayout(new BorderLayout());
		this.add(cmbDatabase,BorderLayout.NORTH);
		this.add(sp,BorderLayout.CENTER);
		
		//添加数据库名字到下拉列表框中
		Connection cn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			cn=DataBase.getConnection();
			st=cn.createStatement();
			rs=st.executeQuery("show Databases");
			while(rs.next()){
				cmbDatabase.addItem(rs.getString(1));
			}
		}catch(Exception e){
		}finally{
			try{
				rs.close();
				st.close();
				cn.close();
			}catch(Exception e){}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShowTable w=new ShowTable();
		w.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String database=(String)cmbDatabase.getSelectedItem();
		Connection cn=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			cn=DataBase.getConnection(database);
			st=cn.createStatement();
			rs=st.executeQuery("show tables");
			Vector v=new Vector();
			while(rs.next()){
				v.add(rs.getString(1));
			}
			lstTable.setListData(v);
		}catch(Exception ex){}
		finally{
			try{
				rs.close();
				st.close();
				cn.close();
			}catch(Exception ex){}
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount()==2){//双击
			String database=cmbDatabase.getSelectedItem().toString();
			String table=lstTable.getSelectedValue().toString();
			int x=this.getX();
			int y=this.getY();
			int width=this.getWidth();
			int height=this.getHeight();
			
			Mess mess=new Mess();
			mess.setDatabase(database);
			mess.setTable(table);
			mess.setX(x);
			mess.setY(y);
			mess.setWidth1(width);
			mess.setHeight(height);
			mess.setSd(w);
			if(w!=null){
				if(w.w!=null){
					w.w.setVisible(false);
				}
				w.setVisible(false);
			}
			w=new ShowData(mess);
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
