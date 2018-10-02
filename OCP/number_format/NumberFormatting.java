import java.text.NumberFormat;
import java.text.ParseException;

public class NumberFormatting {
	public static void main(String[] args) throws ParseException{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		System.out.println(nf.parse("123.1234"));
	}
}
