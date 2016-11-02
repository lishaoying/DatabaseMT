package com.lsy;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class EditData extends JFrame implements ActionListener{
   ArrayList<JLabel> colNames=new ArrayList();
   ArrayList<JTextField> values=new ArrayList();
   HashMap<String,String> data;
   ShowData w;
   Mess m=new Mess();
   private String database;
   private String table;
   boolean un=true;//标记是新建还是修改，true是修改，false是新建
   public EditData(Mess mess){
	   w=mess.getSd();
	   database=mess.getDatabase();
	   table=mess.getTable();
	   int x=mess.getX();
	   int y=mess.getY();
	   int width1=mess.getWidth1();
	   int width2=mess.getWidth2();
	   int height=mess.getHeight();
	   this.setLocation(x,y);
	   
	   m.setDatabase(database);
	   m.setTable(table);
	   m.setX(width1);
	   m.setHeight(y-height);
	   m.setWidth2(width2);
	   m.setHeight(height);
	   //根据窗体宽度，计算每行能够安排的项目数，每列提供200个像素点
	   int cols=(width1+width2)/200;
	   //计算需要几行能够显示全部项目
	   data=mess.getHm();
	   int rows=0;
	   if(data.size()%cols==0){
		   rows=data.size()/cols;
	   }else{
		   rows=data.size()/cols+1;
	   }
	   //计算窗体的高度为，每行15个像素点，再加下方按钮的100个像素点
	   int h=rows*15+100;
	   this.setSize(width1+width2, h);
	   
	   //循环new JTable
	   for(String col:data.keySet()){
		   JLabel l=new JLabel(col);
		   colNames.add(l);
	   }
	   //循环new JTextField
	   for(String v:data.values()){
		   JTextField f=new JTextField(v);
		   values.add(f);
	   }
	   //new 功能按钮
	   JButton btnAdd=new JButton("添加");
	   JButton btnDelete=new JButton("删除");
	   JButton btnSave=new JButton("保存");
	   //注册监听
	   btnAdd.addActionListener(this);
	   btnDelete.addActionListener(this);
	   btnSave.addActionListener(this);
	   //按钮布局
	   JPanel panButton=new JPanel();
	   panButton.setLayout(new GridLayout(1,3));
	   panButton.add(btnAdd);
	   panButton.add(btnDelete);
	   panButton.add(btnSave);
	   //输入框布局
	   JPanel panInput=new JPanel();
	   panInput.setLayout(new GridLayout(rows,cols));
	   //将标签和输入框组合到一个小面板中，否则两者不在一起
	   for(int i=0;i<data.size();i++){
		   JPanel p=new JPanel();
		   p.setLayout(new GridLayout(1,2));
		   p.add((JLabel)colNames.get(i));
		   p.add((JTextField)values.get(i));
		   panInput.add(p);
	   }
	   this.setLayout(new BorderLayout());
	   this.add(panInput,BorderLayout.CENTER);
	   this.add(panButton,BorderLayout.SOUTH);
   }
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getActionCommand().equals("添加")){
		//清空所有的输入框
	    un=false;
	    for(JTextField txt:values){
	    	txt.setText("");
	    }
	}
	if(e.getActionCommand().equals("删除")){
		try{
			//拼接SQL语句
			String SQLStr="delete from "+table;
			SQLStr+=" where 1=1 ";
			for(String col:data.keySet()){
				SQLStr+="and "+col+"='";
				SQLStr+=changeCode(data.get(col).toString())+"'";
			}
			//发出SQL指令
			Connection cn=DataBase.getConnection(database);
			Statement st=cn.createStatement();
			st.executeUpdate(SQLStr);
			
			review();
		}catch(Exception ex){}
	}
	if(e.getActionCommand().equals("保存")){
		if(un){
			//修改
			try{
				//拼接SQLStr语句
				String SQLStr="update "+table+" set ";
				for(int i=0;i<colNames.size();i++){
					SQLStr+=colNames.get(i).getText()+"='";
					SQLStr+=changeCode(values.get(i).getText());
					SQLStr+="',";
				}
				//去掉最后的一个括号
				SQLStr=SQLStr.substring(0, SQLStr.length()-1);
				SQLStr+=" where 1=1";
				for(String col:data.keySet()){
					if(data.get(col)!=null){
						SQLStr+=" and "+col+"='";
						SQLStr+=changeCode(data.get(col).toString());
						SQLStr+="'";
					}
				}
				//发出SQL指令
				Connection cn=DataBase.getConnection(database);
				Statement st=cn.createStatement();
				st.executeUpdate(SQLStr);
				
				review();
			}catch(Exception ex){}
		}else{
		  //新建
			try{
				//拼接SQL语句
				String SQLStr="insert into "+table+"(";
				for(String col:data.keySet()){
					SQLStr+=col+",";
				}
				//去掉最后一个逗号
				SQLStr=SQLStr.substring(0, SQLStr.length()-1);
				SQLStr+=") values(";
				for(JTextField txt:values){
					SQLStr+="'"+txt.getText()+"',";
				}
				//去掉最后一个逗号
				SQLStr=SQLStr.substring(0, SQLStr.length()-1);
				SQLStr+=")";
				//发出SQL指令
				Connection cn=DataBase.getConnection(database);
				Statement st=cn.createStatement();
				st.executeUpdate(SQLStr);
				
				review();
			}catch(Exception ex){}
		}
	}
}
//刷新ShowData显示的方法
public void review(){
	this.setVisible(false);
	w.setVisible(false);
	w=new ShowData(m);
	w.setVisible(true);
}
//字符串字符集转换
public String changeCode(String s){
	String ss="";
	try{
		ss=new String(s.getBytes("gb2312"),"iso-8859-1");
	}catch(Exception e){}
	return ss;
}
}
   