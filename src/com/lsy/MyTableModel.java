package com.lsy;

import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import java.sql.*;

public class MyTableModel implements TableModel{
	private ResultSet rs=null;
	private ResultSetMetaData rsmd=null;
    public MyTableModel(String database,String table){
    	try{
    		Connection cn=DataBase.getConnection();
    		Statement  st=cn.createStatement();
    		st.execute("use "+database);
    		rs=st.executeQuery("select * from "+table);
    	    rsmd=rs.getMetaData();
    	}catch(Exception e){}
    }
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		int count=0;
		try{
			rs.last();
			count=rs.getRow();
		}catch(Exception e){}
		return count;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		int count=0;
		try{
			count=rsmd.getColumnCount();
		}catch(Exception e){}
		return count;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		String name=null;
		try{
	        name=rsmd.getColumnName(columnIndex+1);
		}catch(Exception e){}
		return name;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Object value=null;
		try{
			rs.absolute(rowIndex+1);
			value=new String(rs.getString(columnIndex+1).getBytes("ISO-8859-1"),"gb2312");
		}catch(Exception e){}
		return value;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		try{
			Connection cn=DataBase.getConnection("365buy");
			Statement st=cn.createStatement();
			String SQLStr="update products set ";
			SQLStr+=this.getColumnName(columnIndex);
			SQLStr+="='"+aValue+"' where 1=1";
			for(int i=0;i<this.getColumnCount();i++){
				if(this.getValueAt(rowIndex, i)!=null){
					SQLStr+=" and "+this.getColumnName(i);
					SQLStr+="='"+this.getValueAt(rowIndex, i)+"'";
				}
			}
			//处理中文问题
			SQLStr=new String(SQLStr.getBytes("gb2312"),"ISO-8859-1");
			//检查一下生成的SQL语句
			System.out.println(SQLStr);
			
			st.executeQuery(SQLStr);
			rs=st.executeQuery("select * from products");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

}
