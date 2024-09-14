package com.example.experiment20249.Class;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import com.example.experiment20249.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Date {
    private static final String TAG = "Date";
    public static String year;
    public static String month;
    public static String day;
    public static String currentTime;
    public static LocalDate currentMonday;
    public static ArrayList<LocalDate> weekList;//本周日期列表
    public static int weekOffset;//距离本周偏移量

    static {
        initialize();
    }

    public static void initialize(){
        LocalDate currentDate = LocalDate.now();
        currentTime = currentDate.toString();
        String[] date = currentTime.split("-");
        year = date[0];
        month = date[1];
        day = date[2];
        currentMonday = getStartofWeek(currentDate);//这一周周一的日期
        weekList = generateWeekDates(currentMonday);//本周日期列表
        weekOffset = 0;
    }

    public static String getYear(){
        return year;
    }
    public static String getMonth(){
        return month;
    }
    public static String getDay(){
        return day;
    }

    public static String getCurrentTime(){
        if (currentTime == null || !currentTime.equals(LocalDate.now().toString())) {
            initialize();
        }
        return currentTime;
    }

    public static LocalDate getCurrentMonday(){
        return currentMonday;
    }

    public static ArrayList<LocalDate> getWeekList(){
        return weekList;
    }

    public static ArrayList<String> getWeekList_String(){
        return generateWeekDatesString(getWeekList());
    }
    public static String dayinWeek_getChinese(String dayinWeek){
        switch (dayinWeek){
            case "MONDAY":
                return "周一";
            case "TUESDAY":
                return "周二";
            case "WEDNESDAY":
                return "周三";
            case "THURSDAY":
                return "周四";
            case "FRIDAY":
                return "周五";
            case "SATURDAY":
                return "周六";
            default:
                return "周天";
        }
    }
    public static LocalDate getStartofWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();//获取当前日期是星期几
        int daysUntilMonday = dayOfWeek.getValue() - DayOfWeek.MONDAY.getValue();//计算当前日期到周一的天数
        if(daysUntilMonday < 0){
            daysUntilMonday += 7;
        }
        return date.minusDays(daysUntilMonday);//返回本周周一的日期
    }
    public static ArrayList<LocalDate> generateWeekDates(LocalDate date) {//生成本周日期列表
        ArrayList<LocalDate> weekDates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekDates.add(date.plusDays(i));
        }
        return weekDates;
    }
    public static ArrayList<String> generateWeekDatesString(ArrayList<LocalDate> dates) {//生成本周日期列表字符串形势
        ArrayList<String> weekDateString = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (LocalDate date : dates) {
            weekDateString.add(date.format(formatter));
        }
        return weekDateString;
    }

    public static void addWeek() {
        weekOffset++;
        currentMonday = currentMonday.plusWeeks(1);
        weekList = generateWeekDates(currentMonday);
    }

    public static void subtractWeek() {
        weekOffset--;
        currentMonday = currentMonday.minusWeeks(1);
        weekList = generateWeekDates(currentMonday);
    }
}
