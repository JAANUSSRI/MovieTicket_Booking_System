import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
public class Movie implements ActionListener{
    private int width, height;
    private int selectMovie, selectLang, selectDate, selectTime,screen;
    private database db;
    private static ImageIcon icon1;
    static ImageIcon icon2;
    private static ImageIcon[] seat_icons;
    private ArrayList<String> bookedSeats;
    static JFrame f;
    private String name;
    private JButton next, prev, print;
    private JButton[][] seats = new JButton[10][6];
    private JComboBox<String> date, time, movies, lang;
    private JLabel t1, t2, t3;
    //


   //
    //private JButton[][] seats = new JButton[10]16];
    //
    private Movie() throws IOException{
        width = 500;
        height = 300;
        db = new database();
        icon1 = new ImageIcon("images/icon1.png");  //"C:\Users\Admin\OneDrive\Desktop\project\src\images\icon1.png"
        icon2 = new ImageIcon("images/icon2.png");
        seat_icons = new ImageIcon[2];
        bookedSeats = new ArrayList<>();
        seat_icons[0] = new ImageIcon("images/seat0.png");
        seat_icons[1] = new ImageIcon("images/seat1.png");
        input();
        f = new JFrame("Movie");
        screen = 1;
        sc1();
    }

    public static void main(String[] args) throws IOException {
        new Movie();
    }
    private void input()
    {
        name = JOptionPane.showInputDialog(null, "ENTER NAME    : ", "WELCOME", JOptionPane.PLAIN_MESSAGE);
        if (name == null) {
            System.err.println("Name not Entered");
            System.exit(1);
        }
    }
    private void sc1(){
        f.setSize(width, height);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setIconImage(icon1.getImage());

        String[] movi = db.getMovies();
        movies = new JComboBox<>(movi);
        movies.setBounds(100, 75, 250, 25);
        movies.setSelectedIndex(-1);

        lang = new JComboBox<>(db.getLang());
        lang.setBounds(100, 125, 250, 25);
        lang.setSelectedIndex(-1);

        t1 = new JLabel();
        t1.setText("Select Movie    : ");
        t1.setBounds(0,75,100,25);
        t1.setHorizontalAlignment(SwingConstants.CENTER);

        t2 = new JLabel();
        t2.setText("Select Language : ");
        t2.setBounds(0,125,100,25);
        t2.setHorizontalAlignment(SwingConstants.CENTER);

        t3 = new JLabel();
        t3.setText("Welcome "+name);
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setBounds(0,10,width,20);

        next = new JButton();
        next.setText("NEXT");
        next.setBounds(width - 95 , 180, 75, 25);
        next.addActionListener(this);

        f.add(movies);
        f.add(lang);
        f.add(t1);
        f.add(t2);
        f.add(t3);
        f.add(next);
        f.setLayout(null);
        f.setVisible(true);
    }
    private void sc2()
    {
        String movie = movies.getItemAt(selectMovie);
        t1.setText("Movie  : "+movie);
        t1.setBounds(0,10,width,20);
        t1.setHorizontalAlignment(SwingConstants.LEFT);

        t2.setText("Language: "+lang.getSelectedItem());
        t2.setBounds(0,40,width,20);
        t2.setHorizontalAlignment(SwingConstants.LEFT);

        prev = new JButton();
        prev.setText("PREV");
        prev.setBounds(10 , 180, 75, 25);
        prev.addActionListener(this);

        next.setBounds(width - 95 , 180, 75, 25);

        date = new JComboBox<>(schedule.getDates(5));
        date.setBounds(100,75,250,25);
        date.setSelectedIndex(-1);
        date.addActionListener(this);

        time = new JComboBox<>(schedule.getTimes((String)date.getSelectedItem()));
        time.setBounds(100, 125, 250, 25);
        time.setSelectedIndex(-1);

        t3 = new JLabel();
        t3.setText("Date     ");
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setBounds(0,75,100,25);

        JLabel t4 = new JLabel();
        t4.setText("Time     ");
        t4.setHorizontalAlignment(SwingConstants.CENTER);
        t4.setBounds(0,125,100,25);

        f.add(t1);
        f.add(date);
        f.add(time);
        f.add(t2);
        f.add(t3);
        f.add(t4);
        f.add(next);
        f.add(prev);
    }

    private void sc3() {
        t1.setText("--------- MOVIE DISPLAYED HERE ---------");
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        t1.setBounds(width/2 - 150,10,300,25);

        next.setBounds(width - 95,10,75,25);
        prev.setBounds(10,10,75,25);

        String[] seats_oc = db.getSeats();

        int xs = (width-30)/seats.length;
        int ys = (height-80)/seats[0].length;
        for(int i = 0; i < seats.length;i++){
            for(int j = 0; j < seats[i].length;j++){
                String seatCode = ""+(char)(j+65)+(i+1);
                JButton seat;
                seat = new JButton();
                seat.setBounds(10 + (xs*i),40+(ys*j),xs-5,ys-5);
                seat.setIcon(seat_icons[0]);
                seat.setText(seatCode);
                seat.setMargin(new Insets(0,0,0,0));
                seat.setFont(new Font("Consolas", Font.PLAIN, 10));
                seat.setHorizontalTextPosition(JButton.CENTER);
                seat.setVerticalTextPosition(JButton.CENTER);
                seat.addActionListener(this);
                seat.setToolTipText("Rs. "+database.price(seatCode));
                f.add(seat);
                seats[i][j] = seat;
            }
        }
        for (String st : seats_oc) {
            String[] tokens = st.split("\t");
            if (tokens.length < 6) continue;
            if (tokens[0].equals(movies.getItemAt(selectMovie)))
                if (tokens[1].equals(lang.getItemAt(selectLang)))
                    if (tokens[2].equals(date.getItemAt(selectDate)))
                        if (tokens[3].equals(time.getItemAt(selectTime))) {
                            for (int j = 5; j < tokens.length; j++) {
                                String seatID = tokens[j];
                                int y = seatID.charAt(0) - 65;
                                int x = Integer.parseInt(seatID.substring(1)) - 1;
                                seats[x][y].setEnabled(false);
                                seats[x][y].removeActionListener(this);
                            }
                        }
        }
        f.add(t1);
        f.add(next);
        f.add(prev);
        bookedSeats = new ArrayList<>();
    }

    private void sc4() throws IOException{

        t1.setText("Movie Tickets Booked...");
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        t1.setBounds(width/2 - 125,10,250,20);

        t2.setText("THANK YOU");
        t2.setHorizontalAlignment(SwingConstants.CENTER);
        t2.setBounds(width/2 - 200,50,400,20);

        t3.setText("You can exit or print your tickets.");
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setBounds(width/2 - 200,90,400,20);

        next.setText("Close");
        next.setBounds(width - 95 , 180, 75, 25);

        print = new JButton("Print");
        print.setBounds(10 , 180, 75, 25);
        print.addActionListener(this);

        f.add(t1);
        f.add(t2);
        f.add(t3);
        f.add(next);
        f.add(print);

        String mov = movies.getItemAt(selectMovie);
        String lan = lang.getItemAt(selectLang);
        String dat = date.getItemAt(selectDate);
        String tim = time.getItemAt(selectTime);
        db.setSeats(mov, lan, dat, tim, name, bookedSeats);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == date)
        {
            f.remove(time);
            time = new JComboBox<>(schedule.getTimes((String) date.getSelectedItem()));
            time.setBounds(100, 125, 250, 25);
            time.setSelectedIndex(-1);
            f.add(time);
            f.revalidate();
            f.repaint();
        }
        if(e.getSource()== print){
            printTicket.print();
        }
        if (e.getSource() == next) {
            if (screen == 1) {
                selectMovie = movies.getSelectedIndex();
                selectLang = lang.getSelectedIndex();

                if(selectMovie < 0 && selectLang < 0){
                    JOptionPane.showMessageDialog(f, "Kindly Choose Movie and Language", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if(selectMovie < 0){
                    JOptionPane.showMessageDialog(f, "Please Choose Movie", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if(selectLang < 0){
                    JOptionPane.showMessageDialog(f, "Please Choose Language", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //System.out.println("Movie Selected    : " + movies.getSelectedItem());
                //System.out.println("Language Selected : " + lang.getSelectedItem());
                f.getContentPane().removeAll();
                sc2();
            }
            else if (screen==2){
                selectDate = date.getSelectedIndex();
                selectTime= time.getSelectedIndex();

                if(selectDate< 0 && selectTime< 0) {
                    JOptionPane.showMessageDialog(f, "Please Choose Date and Time", "Required Fields are empty", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(selectDate < 0) {
                    JOptionPane.showMessageDialog(f, "Please Choose Date", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(selectTime< 0) {
                    JOptionPane.showMessageDialog(f, "Please Choose Time", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //System.out.println("Date Selected: " + date.getSelectedItem());
                //System.out.println("Time Selected: " + time.getSelectedItem());
                f.getContentPane().removeAll();
                sc3();
            }
            else if (screen==3){
                if(bookedSeats.isEmpty()){
                    JOptionPane.showMessageDialog(f, "Please choose at least one seat.", "No Seats Chosen", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //else
                {
                    StringBuilder seats = new StringBuilder();
                    int price = 0;
                    for (String seat :bookedSeats) {
                        seats.append(seat).append(" ");
                        price += database.price(seat);
                    }
                    String msg = "Confirm your tickets?" +
                            "\nName      : " + name +
                            "\nMovie     : " + movies.getItemAt(selectMovie) +
                            "\nLanguage  : " + lang.getItemAt(selectLang) +
                            "\nDate      : " + date.getItemAt(selectDate) +
                            "\nTime      : " + time.getItemAt(selectTime) +
                            "\nSeats     : " + seats +
                            "\nPrice     : " + price;
                    int confirm = JOptionPane.showConfirmDialog(f, msg, "CONFIRMATION BOX", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirm == 1) return;
                }
                //System.out.println("Selected seats : "+seats);
                f.getContentPane().removeAll();
                try {
                    sc4();
                }catch (IOException ignored){ }
            }
            if(screen == 4){
                System.exit(0);
            }
            f.revalidate();
            f.repaint();
            screen++;
        }
        if (e.getSource()==prev){
            f.getContentPane().removeAll();
            if (screen==2)
            {
                sc1();
            }
            if (screen == 3) {
                sc2();
            }
            f.revalidate();
            f.repaint();
            screen--;
        }
        if(seats[0][0]==null) return;
        for(int i = 0 ; i < seats.length;i++){
            for(int j = 0;j<seats[i].length;j++)
            {
                JButton seat = seats[i][j];
                if(e.getSource() == seat){
                    if(seat.getIcon()==seat_icons[0]){
                        seat.setIcon(seat_icons[1]);
                        bookedSeats.add(""+(char)(j+65)+(i+1));
                    }
                    else if(seat.getIcon()==seat_icons[1]) {
                        seat.setIcon(seat_icons[0]);
                        bookedSeats.remove(""+(char)(j+65)+(i+1));
                    }
                }
            }
        }
    }
}