package com.milansamardzic.zvrrmtel;

/**
 * Created by ms on 12/24/14.
 */
public class CustomComparator implements java.util.Comparator<People> {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compare(People lhs, People rhs) {

        return lhs.getName().compareTo(rhs.getName());
        //return String.valueOf(lhs.getDuration()).compareTo(String.valueOf(rhs.getDuration()));
    }

}