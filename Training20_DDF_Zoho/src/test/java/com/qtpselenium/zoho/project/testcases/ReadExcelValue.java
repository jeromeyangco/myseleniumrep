package com.qtpselenium.zoho.project.testcases;

import org.testng.annotations.Test;

import com.qtpselenium.zoho.project.util.Xls_Reader2;

public class ReadExcelValue {

	Xls_Reader2 xls;
	
	
		@Test
		public void run() {
			
			//System.out.println("start");
			System.out.println("====TESSSSST=====");
			
			xls = new Xls_Reader2("C:\\data\\training20\\data02_zoho.xlsx");
			
			String text = xls.getCellData("tempsheet", "ColOne", 2);
			// = xls.getColumnCount("tempsheet");
			
			System.out.println(text);
			System.out.println("====LAST LINE====");
			
		}
		
}
