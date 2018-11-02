package com.qtpselenium.zoho.project.util;

import java.util.Hashtable;

public class DataUtil {

	//make "static" method
	public static Object[][] getTestData(Xls_Reader2 xls, String testCaseName) {
		
		//xls = new Xls_Reader2("C:\\data\\training20\\data01.xlsx");
		String sheetName = "Data";
//		String testCaseName = "TestB";
		
		System.out.println("TEST CASE = " +testCaseName);
		
		int testStartRowNum = 1;
		while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
			testStartRowNum++;
		}
		System.out.println("Test Starts from Row -> " +testStartRowNum);
		
		int colStartRowNum = testStartRowNum+1;
		int dataStartRowNum = testStartRowNum+2;
		
		//calculate rows of data
		int rows=0;
		while(!xls.getCellData(sheetName, 0, dataStartRowNum+rows).equals("")) {
			rows++;
		}
		System.out.println("Total Number of Data Rows -> " +rows);
		
		
		//calculate total cols
		int cols = 1;
		while(!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
			cols++;
		}
		System.out.println("Total Number of Data Cols -> " +cols);
		
		
		//since we already got the total number of data (rows and cols)
		//we can now create an object (2 dimensional array)
		//Object[][] data = new Object[rows][cols]; -> this will fail if total columns on excel file is changed
		Object[][] data = new Object[rows][1];
		
		//read data
		int dataRow=0;
		Hashtable<String, String> table = null;
		for(int rNum=dataStartRowNum; rNum < dataStartRowNum+rows; rNum++) {
			table = new Hashtable<String, String>();
			for(int cNum=0; cNum < cols; cNum++) {
//				System.out.print(xls.getCellData(sheetName, cNum, rNum)+"\t");
				//data[objRow][cNum] = xls.getCellData(sheetName, cNum, rNum);
				//logic: 0,0 -> 0,1 -> 0,2 . . . 1,0 -> 1,1 -> 1,2 -> . . .
				String key = xls.getCellData(sheetName, cNum, colStartRowNum);
				String value = xls.getCellData(sheetName, cNum, rNum);
				table.put(key, value);//put all data to table on current row
				System.out.print(key +"--- "+value+"\t");
			}
			
			data[dataRow][0] = table; //data[1][0](table1) -> data[2][0](table2) -> data[3][0](table3)
			dataRow++;
			System.out.println();
		}
		return data;
	}
	
	
	
	//isRunnable method
	public static boolean isRunnable(String testName, Xls_Reader2 xls) {
		String sheetName="TestCases";
		String currentTestCaseName;
		
		int rows = xls.getRowCount(sheetName);
		for(int r=2; r<=rows; r++) {
			currentTestCaseName = xls.getCellData(sheetName, "TCID", r);
			if(currentTestCaseName.equals(testName)) {
				String runmode = xls.getCellData(sheetName, "Runmode", r);
				if(runmode.equals("Y")) {
					return true;
				} else {
					return false;
				}
			}
		}
		//in case you're not able to find the TestCase in xls, in that case by default return false
		return false;
	}
}
