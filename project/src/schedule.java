import java.util.Calendar;

public class schedule{
    static String[] getTimes(String date) {
        String[] times = new String[]{"07:00","10:15","13:30","16:45","20:00","23:00"};
        if (date == null) return times;
        Calendar c = Calendar.getInstance();
        String present = c.getTime().toString();
        present = present.substring(0, 10) + " " + present.substring(present.length() - 4);
        if(date.equals(present))
        {
            Calendar showtime = Calendar.getInstance();
            for(int i = times.length-1; i >=0;i--)
            {
                showtime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[i].substring(0,2)));
                showtime.set(Calendar.MINUTE,Integer.parseInt(times[i].substring(3)));
                if(showtime.before(c)){
                    String[] tmp = new String[times.length - i - 1];
                    System.arraycopy(times,i+1,tmp,0,tmp.length);
                    times = tmp;
                    break;
                }
            }
        }
        return times;
    }
    static String[] getDates(int no_days) {
        String[] dates = new String[no_days];
        Calendar c = Calendar.getInstance();
        for(int i = 0 ; i < no_days; i++) {
            String date = c.getTime().toString();
            date = date.substring(0, 10) + " " + date.substring(date.length() - 4);
            dates[i] = date;
            c.add(Calendar.DATE,1);
        }
        return dates;
    }
}
