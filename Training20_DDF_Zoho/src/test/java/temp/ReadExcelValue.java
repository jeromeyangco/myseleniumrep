package temp;

import com.qtpselenium.zoho.project.util.Xls_Reader2;

public class ReadExcelValue {

	public static void main(String[] args) {

		
		System.out.println("start");
		Xls_Reader2 xls;
		
		xls = new Xls_Reader2("C:\\data\\training20\\data02_zoho.xlsx");
		
		String text = xls.getCellData("tempsheet", "ColOne", 2);
		// = xls.getColumnCount("tempsheet");
		
		System.out.println(text);
		
	}

}
