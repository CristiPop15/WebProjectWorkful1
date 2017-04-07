package com.workful.handler;

import org.joda.time.LocalDate;

public class Date {
	public static String currentDate(){
        LocalDate localDate = new LocalDate();
        return localDate.toString();

	}
}
