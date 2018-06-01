/*
 * Decompiled with CFR 0_115.
 */
public class Time {
    private int hour;
    private int min;

    public Time(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMin() {
        return this.min;
    }

    public void setHour(int x) {
        this.hour = x;
    }

    public void setMin(int x) {
        this.min = x;
    }

    public static Time subtract(Time later, Time early) {
        int hourDif;
        int lMin = later.getMin();
        int lHour = later.getHour();
        int eMin = early.getMin();
        int eHour = early.getHour();
        int minDif = lMin - eMin;
        if (minDif < 0) {
            minDif += 60;
            --lHour;
        }
        if ((hourDif = lHour - eHour) < 0) {
            hourDif += 24;
        }
        Time time = new Time(hourDif, minDif);
        return time;
    }

    public String toString() {
        return String.valueOf(this.getHour()) + " hours and " + this.getMin() + " minutes";
    }
}
