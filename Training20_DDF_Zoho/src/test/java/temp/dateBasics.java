package temp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dateBasics {

	public static void main(String[] args) throws ParseException {

		//d=current date
		Date d = new Date();
		System.out.println(d);
		
		String date = "13/04/2005";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date d1 = sdf.parse(date);
		System.out.println(d1);
		
		//if date=13/04/2005 -> date is less than current date -> return 1
		//if date=13/04/2105 -> date is greater than current date -> return -1
		//if date=			 -> date is same with current date -> return 0
		System.out.println(d.compareTo(d1));
		
		//value -> "dd", "MM", "MMM"->short form of month, "MMMM"-> full form of month, "yyyy"
		sdf = new SimpleDateFormat("MMMM");
		System.out.println(sdf.format(d));
		System.out.println(sdf.format(d1));
		
	}

}
