package com.workful.templates;

import org.joda.time.LocalDate;

public class Date {
	public static String currentDate(){
        LocalDate localDate = new LocalDate();
        return localDate.toString();

	}
}
