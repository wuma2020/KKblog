package TestServlet;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import cn.itcast.jdbc.TxQueryRunner;

public class testTwo {
	
	QueryRunner quneryRunner  = new TxQueryRunner();
	@Test
	public void test1(){
		
		
		  Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);//获取年份
	        int month=cal.get(Calendar.MONTH)+1;//获取月份 
	        int day=cal.get(Calendar.DATE);//获取日 
	        int hour=cal.get(Calendar.HOUR);//小时 
	        int minute=cal.get(Calendar.MINUTE);//分            
	        int second=cal.get(Calendar.SECOND);//秒 
	        System.out.println(cal.get(Calendar.AM_PM));
	        int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);//一周的第几天
		String to = year+month+day+hour+"";
		System.out.println(to);
		
		Date date = new Date();
		
		DateFormat datef1 =  DateFormat.getDateInstance();
		System.out.println(datef1.format(date));
		
		
		String time = date.toLocaleString();
		String time1 = date.toString();
		String time2 = date.getDate()+""+date.getYear();
		System.out.println(time +"aaa"+time1+"bb"+time2);
		
	}
}
