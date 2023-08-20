import java.awt.*;
import java.awt.print.*;

public class printTicket implements Printable {
    static void print(){
        PrinterJob job = PrinterJob.getPrinterJob();
        printTicket pt = new printTicket();
        job.setPrintable(pt);
        if (job.printDialog()) {
            try {
                job.print();
            }
            catch (PrinterException e) {
                System.out.print("Could not Print\n"+e.toString());
            }
        }
    }
    public int print(Graphics g, PageFormat pf, int page) {
        if (page > 0) return NO_SUCH_PAGE;
        if (Movie.f == null)   return NO_SUCH_PAGE;
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        String[] labels = {"Movie  ","Language  ","Date  ","Time  ","Name  ","Seats  ","Price  "};
        int y = 300;
        g.setFont(new Font("Arial",Font.PLAIN,55));
        g.drawString("MOVIE-TIME",50,100);
        g.drawImage(Movie.icon2.getImage(), 400, 10, 150, 150,null);
        String[] receipt = database.getReceipt();
        for(int i = 0; i<labels.length;i++) {
            String label = labels[i];
            String data = receipt[i];
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString(label, 20, y);

            g.setFont(new Font("Consolas",Font.PLAIN,16));
            y = printExcess(g,data,y,40);
            y += 30;
        }
        return PAGE_EXISTS;
    }

    private int printExcess(Graphics g, String data, int y, int i) {
        if(data.length()<i) {
            g.drawString(data,200,y);
            return y;
        }
        g.drawString(data.substring(0,i),200,y);
        return printExcess(g, data.substring(i),y+25,i);
    }

}
