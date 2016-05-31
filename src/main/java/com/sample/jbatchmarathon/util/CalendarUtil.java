package com.sample.jbatchmarathon.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtil {

	/**
	 * 現在の日付・時刻から指定の【年数】を加算・減算した結果を返します。
	 * 
	 * @param addYera
	 *            加算・減算する年数
	 * @return 計算後の Calendar インスタンス。
	 */
	public static Calendar addYear(Calendar baseDate, int addYear) {
		return add(baseDate, addYear, 0, 0, 0, 0, 0, 0);
	}

	/**
	 * 現在の日付・時刻から指定の【月数】を加算・減算した結果を返します。
	 * 
	 * @param addMonth
	 *            加算・減算する月数
	 * @return 計算後の Calendar インスタンス。
	 */
	public static Calendar addMonth(Calendar baseDate, int addMonth) {
		return add(baseDate, 0, addMonth, 0, 0, 0, 0, 0);
	}

	/**
	 * ※Date版 現在の日付・時刻から指定の【月数】を加算・減算した結果を返します。
	 * 
	 * @param addMonth
	 *            加算・減算する月数
	 * @return 計算後の Date インスタンス。
	 */
	public static Date addMonth(Date baseDate, int addMonth) {

		Calendar cal = new GregorianCalendar();
		cal.setTime(baseDate);
		Calendar addedDate = add(cal, 0, addMonth, 0, 0, 0, 0, 0);

		// 入力が月末日かどうかを判定する
		if (isLastDayOfMonth(baseDate)
				&& !isLastDayOfMonth(addedDate.getTime())) {
			// ベース日付が月末で算出値が月末で無い場合には、月末調整を行う
			Calendar cal01 = addMonth(addedDate, 1);
			Calendar cal02 = new GregorianCalendar(cal01.get(Calendar.YEAR), cal01
					.get(Calendar.MONTH), 01);
			return addDate(cal02, -1).getTime();

		} else {
			return addedDate.getTime();
		}
	}

	/**
	 * 現在の日付・時刻から指定の【日数】を加算・減算した結果を返します。
	 * 
	 * @param addDate
	 *            加算・減算する日数
	 * @return 計算後の Calendar インスタンス。
	 */
	public static Calendar addDate(Calendar baseDate, int addDate) {
		return add(baseDate, 0, 0, addDate, 0, 0, 0, 0);
	}

	/**
	 * 現在の日付・時刻から指定の【時間】を加算・減算した結果を返します。
	 * 
	 * @param addHour
	 *            加算・減算する時間
	 * @return 計算後の Calendar インスタンス。
	 */
	public static Calendar addHour(Calendar baseDate, int addHour) {
		return add(baseDate, 0, 0, 0, addHour, 0, 0, 0);
	}

	/**
	 * 現在の日付・時刻から指定の【分】を加算・減算した結果を返します。
	 * 
	 * @param addMinute
	 *            加算・減算する分
	 * @return 計算後の Calendar インスタンス。
	 */
	public static Calendar addMinute(Calendar baseDate, int addMinute) {
		return add(baseDate, 0, 0, 0, 0, addMinute, 0, 0);
	}

	/**
	 * 現在の日付・時刻から指定の【秒】を加算・減算した結果を返します。
	 * 
	 * @param addSecond
	 *            加算・減算する秒
	 * @return 計算後の Calendar インスタンス。
	 */
	public static Calendar addSecond(Calendar baseDate, int addSecond) {
		return add(baseDate, 0, 0, 0, 0, 0, addSecond, 0);
	}

	/**
	 * 現在の日付・時刻から指定の時間量を加算・減算した結果を返します。 年、月、日、時間、分、秒、ミリ秒の各時間フィールドに対し、
	 * 任意の時間量を設定できます。 たとえば、現在の日付時刻から 10 日前を計算する場合は以下となります。 Calendar cal =
	 * add(null,0,0,-10,0,0,0,0);
	 * 
	 * 各時間フィールドの値がその範囲を超えた場合、次の大きい時間フィールドが 増分または減分されます。
	 * たとえば、以下では1時間と5分進めることになります。 Calendar cal = add(null,0,0,0,0,65,0,0);
	 * 
	 * 各時間フィールドに設定する数量が0の場合は、現在の値が設定されます。
	 * java.util.GregorianCalendarの内部処理では以下の分岐を行っている。 if (amount == 0) { return;
	 * }
	 * 
	 * @param cal
	 *            日付時刻の指定があればセットする。 nullの場合、現在の日付時刻で新しいCalendarインスタンスを生成する。
	 * @param addYera
	 *            加算・減算する年数
	 * @param addMonth
	 *            加算・減算する月数
	 * @param addDate
	 *            加算・減算する日数
	 * @param addHour
	 *            加算・減算する時間
	 * @param addMinute
	 *            加算・減算する分
	 * @param addSecond
	 *            加算・減算する秒
	 * @param addMillisecond
	 *            加算・減算するミリ秒
	 * @return 計算後の Calendar インスタンス。
	 */
	public static Calendar add(Calendar cal, int addYera, int addMonth,
			int addDate, int addHour, int addMinute, int addSecond,
			int addMillisecond) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}
		cal.add(Calendar.YEAR, addYera);
		cal.add(Calendar.MONTH, addMonth);
		cal.add(Calendar.DATE, addDate);
		cal.add(Calendar.HOUR_OF_DAY, addHour);
		cal.add(Calendar.MINUTE, addMinute);
		cal.add(Calendar.SECOND, addSecond);
		cal.add(Calendar.MILLISECOND, addMillisecond);
		return cal;
	}

	/**
	 * うるう年判定関数 うるう年の場合は真(true)、うるう年でない場合は偽(false)を返します。
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 月末日かどうかを判定します。
	 */
	public static boolean isLastDayOfMonth(Date date) {

		Calendar cal01 = new GregorianCalendar();
		cal01.setTime(date);

		// 今月の末日を取得する
		int dd = cal01.getActualMaximum(Calendar.DATE);

		Calendar cal02 = new GregorianCalendar(cal01.get(Calendar.YEAR), cal01
				.get(Calendar.MONTH), dd);

		if (cal01.equals(cal02)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 月末日を返します。
	 */
	public static Date getLastDayOfMonth(Date date) {

		Calendar cal01 = new GregorianCalendar();
		cal01.setTime(date);

		// 今月の末日を取得する
		int dd = cal01.getActualMaximum(Calendar.DATE);

		Calendar cal02 = new GregorianCalendar(cal01.get(Calendar.YEAR), cal01
				.get(Calendar.MONTH), dd);

		return cal02.getTime();
	}

	/**
	 * 渡された文字列(String)が日付かどうかを判定します
	 * 
	 * @param object
	 * @return
	 */

	public static boolean isDate(Object object) {

		String in = object.toString();

		Date date;

		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formater.parse(in);
		} catch (ParseException e) {
			return false;
		}
//		System.out.println(date);

		// 指定したフォーマットで日付が返される
		String dateStr = formater.format(date);
		
		if (dateStr.equals(in)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 渡された文字列(String)を日付型として返却します。
	 * 日付文字列はyyyy-MM-ddの形式である必要があります。
	 * 
	 * @param object
	 * @return
	 */

	public static Date toDate(Object object) {

		String in = object.toString();

		Date date;

		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formater.parse(in);
			return date;
		} catch (ParseException e) {
			return null;
		}

	}
	
	
	/**
	 * 
	 * http://sattontanabe.blog86.fc2.com/blog-entry-89.html
	 * 
	 * 2つの日付の月数の差を求めます。
	 * 日付文字列 strDate1 - strDate2 が何ヵ月かを整数で返します。
	 * ※端数の日数は無視します。
	 * 
	 * @param strDate1    日付文字列1    yyyy/MM/dd
	 * @param strDate2    日付文字列2    yyyy/MM/dd
	 * @return 2つの日付の月数の差
	 * @throws ParseException 日付フォーマットが不正な場合
	 */
	public static int differenceMonth(String strDate1, String strDate2) 
	    throws ParseException {
	    Date date1 = DateFormat.getDateInstance().parse(strDate1);
	    Date date2 = DateFormat.getDateInstance().parse(strDate2);
	    return differenceMonth(date1,date2);
	}
	/**
	 * 
	 * http://sattontanabe.blog86.fc2.com/blog-entry-89.html
	 * 
	 * 2つの日付の月数の差を求めます。
	 * java.util.Date 型の日付 date1 - date2 が何ヵ月かを整数で返します。
	 * ※端数の日数は無視します。
	 * 
	 * @param date1    日付1 java.util.Date
	 * @param date2    日付2 java.util.Date
	 * @return 2つの日付の月数の差
	 */
	public static int differenceMonth(Date date1, Date date2) {
	    Calendar cal1 = Calendar.getInstance();
	    cal1.setTime(date1);
	    cal1.set(Calendar.DATE, 1);
	    Calendar cal2 = Calendar.getInstance(); 
	    cal2.setTime(date2);
	    cal2.set(Calendar.DATE, 1);
	    int count = 0;
	    if (cal1.before(cal2)) {
	        while (cal1.before(cal2)) {
	            cal1.add(Calendar.MONTH, 1);
	            count--;
	        }
	    } else {
	        count--;
	        while (!cal1.before(cal2)) {
	            cal1.add(Calendar.MONTH, -1);
	            count++;
	        }
	    }
	    return count;
	}

	public static int differenceDay(Date toDate, Date fromDate) {
		Calendar to = Calendar.getInstance();
		to.setTime(toDate);
		Calendar from = Calendar.getInstance();
		from.setTime(fromDate);
		
		long day = (to.getTimeInMillis() - from.getTimeInMillis())/1000/60/60/24;
		return (int) day;
	}

}
