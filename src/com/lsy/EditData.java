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
   boolean un=true;//������½������޸ģ�true���޸ģ�false���½�
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
	   //���ݴ����ȣ�����ÿ���ܹ����ŵ���Ŀ����ÿ���ṩ200�����ص�
	   int cols=(width1+width2)/200;
	   //������Ҫ�����ܹ���ʾȫ����Ŀ
	   data=mess.getHm();
	   int rows=0;
	   if(data.size()%cols==0){
		   rows=data.size()/cols;
	   }else{
		   rows=data.size()/cols+1;
	   }
	   //���㴰��ĸ߶�Ϊ��ÿ��15�����ص㣬�ټ��·���ť��100�����ص�
	   int h=rows*15+100;
	   this.setSize(width1+width2, h);
	   
	   //ѭ��new JTable
	   for(String col:data.keySet()){
		   JLabel l=new JLabel(col);
		   colNames.add(l);
	   }
	   //ѭ��new JTextField
	   for(String v:data.values()){
		   JTextField f=new JTextField(v);
		   values.add(f);
	   }
	   //new ���ܰ�ť
	   JButton btnAdd=new JButton("���");
	   JButton btnDelete=new JButton("ɾ��");
	   JButton btnSave=new JButton("����");
	   //ע�����
	   btnAdd.addActionListener(this);
	   btnDelete.addActionListener(this);
	   btnSave.addActionListener(this);
	   //��ť����
	   JPanel panButton=new JPanel();
	   panButton.setLayout(new GridLayout(1,3));
	   panButton.add(btnAdd);
	   panButton.add(btnDelete);
	   panButton.add(btnSave);
	   //����򲼾�
	   JPanel panInput=new JPanel();
	   panInput.setLayout(new GridLayout(rows,cols));
	   //����ǩ���������ϵ�һ��С����У��������߲���һ��
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
	if(e.getActionCommand().equals("���")){
		//������е������
	    un=false;
	    for(JTextField txt:values){
	    	txt.setText("");
	    }
	}
	if(e.getActionCommand().equals("ɾ��")){
		try{
			//ƴ��SQL���
			String SQLStr="delete from "+table;
			SQLStr+=" where 1=1 ";
			for(String col:data.keySet()){
				SQLStr+="and "+col+"='";
				SQLStr+=changeCode(data.get(col).toString())+"'";
			}
			//����SQLָ��
			Connection cn=DataBase.getConnection(database);
			Statement st=cn.createStatement();
			st.executeUpdate(SQLStr);
			
			review();
		}catch(Exception ex){}
	}
	if(e.getActionCommand().equals("����")){
		if(un){
			//�޸�
			try{
				//ƴ��SQLStr���
				String SQLStr="update "+table+" set ";
				for(int i=0;i<colNames.size();i++){
					SQLStr+=colNames.get(i).getText()+"='";
					SQLStr+=changeCode(values.get(i).getText());
					SQLStr+="',";
				}
				//ȥ������һ������
				SQLStr=SQLStr.substring(0, SQLStr.length()-1);
				SQLStr+=" where 1=1";
				for(String col:data.keySet()){
					if(data.get(col)!=null){
						SQLStr+=" and "+col+"='";
						SQLStr+=changeCode(data.get(col).toString());
						SQLStr+="'";
					}
				}
				//����SQLָ��
				Connection cn=DataBase.getConnection(database);
				Statement st=cn.createStatement();
				st.executeUpdate(SQLStr);
				
				review();
			}catch(Exception ex){}
		}else{
		  //�½�
			try{
				//ƴ��SQL���
				String SQLStr="insert into "+table+"(";
				for(String col:data.keySet()){
					SQLStr+=col+",";
				}
				//ȥ�����һ������
				SQLStr=SQLStr.substring(0, SQLStr.length()-1);
				SQLStr+=") values(";
				for(JTextField txt:values){
					SQLStr+="'"+txt.getText()+"',";
				}
				//ȥ�����һ������
				SQLStr=SQLStr.substring(0, SQLStr.length()-1);
				SQLStr+=")";
				//����SQLָ��
				Connection cn=DataBase.getConnection(database);
				Statement st=cn.createStatement();
				st.executeUpdate(SQLStr);
				
				review();
			}catch(Exception ex){}
		}
	}
}
//ˢ��ShowData��ʾ�ķ���
public void review(){
	this.setVisible(false);
	w.setVisible(false);
	w=new ShowData(m);
	w.setVisible(true);
}
//�ַ����ַ���ת��
public String changeCode(String s){
	String ss="";
	try{
		ss=new String(s.getBytes("gb2312"),"iso-8859-1");
	}catch(Exception e){}
	return ss;
}
}
   