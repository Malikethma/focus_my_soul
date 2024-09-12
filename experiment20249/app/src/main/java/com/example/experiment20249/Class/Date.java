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
    private boolean isCurrentWeekCalculated = true;
    private boolean isLastWeekCalculated = false;
    private boolean isNextWeekCalculated = false;
    public ArrayList<LocalDateTime> weekList;//本周日期列表
    public ArrayList<LocalDateTime> weekList_Last;
    public ArrayList<LocalDateTime> weekList_Next;

    public void getDate(){//获取当前时间
        LocalDateTime currentDate = LocalDateTime.now();
        Year = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));
        Month = currentDate.format(DateTimeFormatter.ofPattern("MM"));
        Day = currentDate.format(DateTimeFormatter.ofPattern("dd"));
        dayofWeek = dayinWeek_getChinese(currentDate.getDayOfWeek().toString());

        LocalDateTime startofWeek = getStartofWeek(currentDate);//这一周周一的日期
        weekList = generateWeekDates(startofWeek);//本周日期列表
        weekList_Last = getLastWeek(weekList);
        weekList_Next = getNextWeek(weekList);

        Log.d( TAG, "Date: " + Year + "年" + Month + "月" + Day + "日" + dayofWeek);
        Log.d( TAG, "Date: " + weekList.toString());
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

    public boolean isLastWeekCalculated(){
        return isLastWeekCalculated;
    }

    public boolean isNextWeekCalculated(){
        return isCurrentWeekCalculated;
    }
    public ArrayList<LocalDateTime> getWeekList(){
        getDate();
        return weekList;
    }

    public ArrayList<LocalDateTime> getWeekList_Last(){
        getDate();
        return weekList_Last;
    }
    public ArrayList<LocalDateTime> getWeekList_Next(){
        getDate();
        return weekList_Next;
    }
    public ArrayList<String> getWeekList_String(){
        getDate();
        return generateWeekDateString(weekList);
    }
    public ArrayList<String> getWeekList_Last_String(){
        getDate();
        return generateWeekDateString(weekList_Last);
    }
    public ArrayList<String> getWeekList_Next_String(){
        getDate();
        return generateWeekDateString(weekList_Next);
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
    public ArrayList<LocalDateTime> getLastWeek(ArrayList<LocalDateTime> weekList){
        ArrayList<LocalDateTime> lastWeekList = new ArrayList<>();
        for(LocalDateTime date : weekList){
            lastWeekList.add(date.minusDays(7));
        }
        return lastWeekList;
    }
    public ArrayList<LocalDateTime> getNextWeek(ArrayList<LocalDateTime> weekList){
        ArrayList<LocalDateTime> nextWeekList = new ArrayList<>();
        for(LocalDateTime date : weekList){
            nextWeekList.add(date.plusDays(7));
        }
        return nextWeekList;
    }

    public void setLastWeek(){
        if(!isLastWeekCalculated){
        weekList = getLastWeek(weekList);
        weekList_Last = getLastWeek(weekList);
        weekList_Next = getNextWeek(weekList);
        isLastWeekCalculated = true;
        isCurrentWeekCalculated = false;
        isNextWeekCalculated = false;
        }
    }

    public void setNextWeek(){
        if(!isNextWeekCalculated){
            weekList = getNextWeek(weekList);
            weekList_Last = getLastWeek(weekList);
            weekList_Next = getNextWeek(weekList);
            isLastWeekCalculated = false;
            isCurrentWeekCalculated = false;
            isNextWeekCalculated = true;
        }
    }

    private void RefreshDateAttributes(){
        LocalDateTime currentDate = weekList.get(0);
        Year = currentDate.format(DateTimeFormatter.ofPattern("yyyy"));
        Month = currentDate.format(DateTimeFormatter.ofPattern("MM"));
        Day = currentDate.format(DateTimeFormatter.ofPattern("dd"));
    }
}
