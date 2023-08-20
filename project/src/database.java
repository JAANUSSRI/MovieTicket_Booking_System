import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
public class database {
    static boolean update = true;
    private String[] movies, lang, seats;
    private File movies_f, seats_f;
    private static String[] receipt;
    database() throws IOException{
        receipt = new String[7];   //movie, language, date, time, Username, seats, price
        setMovies();
        setSeats();
        lang = new String[]{"English", "Hindi", "Tamil"};
    }

    private void setMovies() throws IOException {
        movies_f = new File("data/movies.txt");
        int i = 0;
        movies = new String[countLines_inFile(movies_f.getAbsolutePath())];
        RandomAccessFile raf = new RandomAccessFile(movies_f, "r");
        while(raf.getFilePointer()!= raf.length()){
            movies[i++] = raf.readLine();
        }
        //System.out.println("Movies array created");
    }
    private void setSeats() throws IOException {
        seats_f = new File("data/seats.txt");
        int j=0;
        seats = new String[countLines_inFile(seats_f.getAbsolutePath())];
        RandomAccessFile f = new RandomAccessFile(seats_f,"r");
        while(f.getFilePointer()!= f.length())
        {
            seats[j++] = f.readLine();
        }
        //System.out.println("Seats Array created");
    }
    static int countLines_inFile(String file){
        Path path = Paths.get(file);
        long count = 0;
        try
        {
            count = Files.lines(path).count();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return ((int)count);
    }
    void setSeats(String movie, String lang, String date, String time, String name , ArrayList<String> seats) throws IOException  {
        receipt[0] = movie;
        receipt[1] = lang;
        receipt[2] = date;
        receipt[3] = time;
        receipt[4] = name;
        receipt[5] = "";
        receipt[6] = "0";
        if(name.equals("")){
            name = "No Name";
        }
        RandomAccessFile f = new RandomAccessFile(seats_f,"rw");
        f.seek(f.length());
        f.writeBytes(movie+"\t"+lang+"\t"+date+"\t"+time+"\t"+name);
        for(String seat:seats)
        {
            receipt[5] += seat+" ";
            receipt[6] = Integer.parseInt(receipt[6]) + price(seat) + "";
            f.writeBytes("\t"+seat);
        }
        receipt[5] = receipt[5].trim();
        f.writeBytes("\n");
        f.close();
    }
    public String[] getMovies() {
        return movies;
    }
    public String[] getLang() {
        return lang;
    }
    public String[] getSeats() {
        return seats;
    }
    public static String[] getReceipt() {
        return receipt;
    }
    static int price(String seat){

        int row = seat.charAt(0) - 65 ;
        int cost = (row/2)+1;
        return cost*120;
    }

}
