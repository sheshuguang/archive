package com.yapu.system.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Vector;

import com.yapu.archive.dao.impl.SysTreeTempletDAOImpl;

public class testexcel {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Excel excel = new Excel();
		Vector v = new Vector();
		InputStream is = new FileInputStream("e:\\New Microsoft Excel 工作表.xls");
		try {
//			v = excel.readFromExcel("e:\\New Microsoft Excel 工作表.xls");
			v = excel.readFromExcel(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i=0;i<v.size();i++) {
			System.out.println(v.get(i));
		}
	}

}
