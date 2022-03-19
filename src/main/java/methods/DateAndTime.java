package methods;

import java.sql.Date;
import java.sql.Time;

public class DateAndTime extends Time {
    public DateAndTime(int hour, int minute, int second) {
        super(hour, minute, second);
    }

    public DateAndTime(long time) {
        super(time);
    }

    @Override
    public String toString() {
        int hour = super.getHours();
        int minute = super.getMinutes();

        char[] buf = new char[5];
        DateAndTime.formatDecimalInt(hour, buf, 0, 2);
        buf[2] = ':';
        DateAndTime.formatDecimalInt(minute, buf, 3, 2);

        return new String(buf);
    }

    private static void formatDecimalInt(int val, char[] buf, int offset, int len) {
        int charPos = offset + len;
        do {
            buf[--charPos] = (char) ('0' + (val % 10));
            val /= 10;
        } while (charPos > offset);
    }
}
