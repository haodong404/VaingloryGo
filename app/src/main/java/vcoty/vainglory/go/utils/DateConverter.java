package vcoty.vainglory.go.utils;

import vcoty.vainglory.go.R;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;
import java.text.ParseException;
import android.content.*;
import java.util.*;

public class DateConverter {
	
	public static final long SECOND = 1000l;
	public static final long MINUTE = 60000l;
	public static final long HOUR = 3600000l;
	public static final long DAY = 86400000l;
	public static final long WEEK = 604800000l;
	public static final long MONTH = 2592000000l;
	
	public static String convert(Context ctx,String utc) throws ParseException {
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = df.parse(utc);
		Date nowDate = new Date(System.currentTimeMillis());
		
		Calendar dateCal = date2Calendar(date);  
		Calendar nowDateCal = date2Calendar(nowDate);
		
		long gap = nowDate.getTime() - date.getTime();
		
		int gapDay = 0;
		if(dateCal.get(Calendar.DAY_OF_MONTH) != nowDateCal.get(Calendar.DAY_OF_MONTH)){
			gapDay = nowDateCal.get(Calendar.DAY_OF_MONTH) - dateCal.get(Calendar.DAY_OF_MONTH) <= 0 ? getMaxDayByYearMonth(date.getYear(),date.getMonth()) + nowDateCal.get(Calendar.DAY_OF_MONTH) - dateCal.get(Calendar.DAY_OF_MONTH) : nowDateCal.get(Calendar.DAY_OF_MONTH) - dateCal.get(Calendar.DAY_OF_MONTH);
		}
		
		int gapMonth = 0;
		if(nowDateCal.get(Calendar.MONTH) != dateCal.get(Calendar.MONTH)){
			gapMonth = (nowDateCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH)) < 0 ? 12 + nowDateCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH) : nowDateCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH);
		}
		
		if(gap < MINUTE){//刚刚
			builder.append(getResString(ctx,R.string.date_just_recently));
		}else if(gap >= (1 * MINUTE) && gap < (2 * MINUTE)){//1分钟前
			builder.append(getResString(ctx,R.string.date_minute_ago));
		}else if(gap >= (2 * MINUTE) && gap < (1 * HOUR)){//n分钟前
			builder.append(gap / MINUTE + " ");
			builder.append(getResString(ctx,R.string.date_minutes_ago));
		}else if(gap >= (1 * HOUR) && gap < (2 * HOUR)){
			builder.append("1 ");
			builder.append(getResString(ctx,R.string.date_hour_ago));
		}else if(gap >= (2 * HOUR) && gapDay < 1){
			builder.append(gap / HOUR + " ");
			builder.append(getResString(ctx,R.string.date_hours_ago));
		}else if(gapDay >= 1 && gapDay < 2){
			builder.append(getResString(ctx,R.string.date_day_ago));
		}else if(gapDay >= 2 && gapDay < 7){
			builder.append(gapDay + " ");
			builder.append(getResString(ctx,R.string.date_days_ago));
		}else if(gapDay >= 7 && gapDay < (2 * 7)){
			builder.append(getResString(ctx,R.string.date_week_ago));
		}else if(gapDay >= (2 * 7) && gapDay <= (4 * 7)){
			builder.append(gapDay / 7 + " ");
			builder.append(getResString(ctx,R.string.date_weeks_ago));
		}else if(gapMonth >= 1 && gapMonth < 2){
			builder.append(getResString(ctx,R.string.date_month_ago));
		}else if(gapMonth >= 2 && gapMonth <= 8){
			builder.append(gapMonth + " ");
			builder.append(getResString(ctx,R.string.date_months_ago));
		}else{
			String month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1) + "";
			String day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate() + "";
			String hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours() + "";
			String minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes() + "";
			
			builder.append(hours).append(":").append(minutes).append("\n").append(month).append("-").append(day).append(" ");
		}
		
		return builder.toString();
	}
	
	public static String directionConvert(int direction){
		return direction / 60 + "'" + direction % 60 + "''";
	}
	
	/**
	 * 获得某个月最大天数
	 *
	 * @param year 年份
	 * @param month 月份 (1-12)
	 * @return 某个月最大天数
	 */
	public static int getMaxDayByYearMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year - 1);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DATE);
	}
	
	public static Calendar date2Calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
	
	public static String getResString(Context ctx,int id){
		return ctx.getResources().getString(id);
	}
}

