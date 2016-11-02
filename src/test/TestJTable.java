package test;
import javax.swing.*;

import java.sql.*;
import java.util.*;

public class TestJTable extends JFrame{
	public TestJTable(){
		this.setSize(400,300);
		MyTableModel tm=new MyTableModel();
		//创建JTable
		JTable t=new JTable(tm);
		//不加滚动条，表头显示不出来
		JScrollPane sp=new JScrollPane(t);
		this.add(sp);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestJTable w=new TestJTable();
		w.setVisible(true);
	}

}
