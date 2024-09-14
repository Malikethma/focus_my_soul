package com.example.experiment20249.Class;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import com.example.experiment20249.R;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Date {
    private String TAG = "Date";
    public String Year;
    public String Month;
    public String Day;
    public String dayofWeek;//星期几
    public LocalDateTime currentMonday;

    public ArrayList<LocalDateTime> weekList;//本周日期列表
    public int weekOffset;//距离本周偏移量

    public void getDate(){//获取当前时间

        LocalDateTime currentDate = LocalDateTime.now();
        Year = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));
        Month = currentDate.format(DateTimeFormatter.ofPattern("MM"));
        Day = currentDate.format(DateTimeFormatter.ofPattern("dd"));
        dayofWeek = dayinWeek_getChinese(currentDate.getDayOfWeek().toString());
        currentMonday = getStartofWeek(currentDate);//这一周周一的日期
        weekList = generateWeekDates(currentMonday);//本周日期列表
        weekOffset = 0;

        Log.d(TAG, "Date: " + Year + "年" + Month + "月" + Day + "日" + dayofWeek);
        Log.d(TAG, "Date: " + weekList.toString());

    }

    public String getYear(){
        getDate();
        return Year;
    }
    public String getMonth(){
        getDate();
        return Month;
    }
    public String getDay(){
        getDate();
        return Day;
    }

    public ArrayList<LocalDateTime> getWeekList(){
        if(weekList == null || weekList.isEmpty()){
            getDate();
        }
        return weekList;
    }


    public ArrayList<String> getWeekList_String(){
        return generateWeekDateString(getWeekList());
    }
    public String dayinWeek_getChinese(String dayinWeek){
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
    public LocalDateTime getStartofWeek(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();//获取当前日期是星期几
        int daysUntilMonday = dayOfWeek.getValue() - DayOfWeek.MONDAY.getValue();//计算当前日期到周一的天数
        if(daysUntilMonday < 0){
            daysUntilMonday += 7;
        }
        return date.minusDays(daysUntilMonday);//返回本周周一的日期
    }
    public ArrayList<LocalDateTime> generateWeekDates(LocalDateTime date) {
        ArrayList<LocalDateTime> weekDates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekDates.add(date.plusDays(i));
        }
        return weekDates;
    }
    public ArrayList<String> generateWeekDateString(ArrayList<LocalDateTime> dates) {
        ArrayList<String> weekDateString = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        for (LocalDateTime date : dates) {
            weekDateString.add(date.format(formatter));
        }
        return weekDateString;
    }

    public void addWeek() {
        weekOffset++;
        currentMonday = currentMonday.plusWeeks(1);
        weekList = generateWeekDates(currentMonday);
    }

    public void subtractWeek() {
        weekOffset--;
        currentMonday = currentMonday.minusWeeks(1);
        weekList = generateWeekDates(currentMonday);
    }
}
