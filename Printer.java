import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.awt.print.*;
import javax.print.attribute.*;

public class Printer extends JFrame implements ActionListener, Printable{

	static JButton tombolCetak, tombolPrint;
	static JLabel labelPathFile;
	static String pathFile = "";

	Printer(){

		tombolCetak = new JButton("Pilih File");
		tombolCetak.setBounds(100, 100, 100, 50);
		tombolCetak.addActionListener(this);
		add(tombolCetak);

		tombolPrint = new JButton("Print File");
		tombolPrint.setBounds(100, 150, 100, 50);
		tombolPrint.addActionListener(this);
		add(tombolPrint);

		labelPathFile = new JLabel();
		labelPathFile.setVisible(false);
		labelPathFile.setBounds(10, 20, 270, 100);
		add(labelPathFile);

		setSize(300,270);
		setLocation(500, 200);    
		setLayout(null);    
		setVisible(true);    
		setDefaultCloseOperation(EXIT_ON_CLOSE); 

	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == tombolCetak){
			JFileChooser pilihFile = new JFileChooser();

			int i = pilihFile.showOpenDialog(this);
			if(i == JFileChooser.APPROVE_OPTION){
				File file = pilihFile.getSelectedFile();
				pathFile = getPathFile(file.getAbsolutePath());
				labelPathFile.setText(pathFile);
				labelPathFile.setVisible(true);
			}
		}
		else if(e.getSource() == tombolPrint){
			if(pathFile.equals("")){
				JOptionPane.showMessageDialog(this, "tidak ada File", "alert", JOptionPane.WARNING_MESSAGE);
			} else{
				PrinterJob printer = PrinterJob.getPrinterJob();
		        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		        PageFormat pf = printer.pageDialog(aset);

		        printer.setPrintable(new Printer(), pf);
		        boolean ok = printer.printDialog(aset);
		        if(ok){
		            try {
		                 printer.print(aset);
		            } catch (PrinterException ex) {
		           		JOptionPane.showMessageDialog(this, ex, "alert", JOptionPane.WARNING_MESSAGE);  	
		            }
		        }
		        System.exit(0);
			}
		}	
	}

	public int print(Graphics g, PageFormat pf, int halaman) throws PrinterException {

        if (halaman > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D konversi_2d = (Graphics2D)g;
        konversi_2d.translate(pf.getImageableX(), pf.getImageableY());

        String dataContainer = "";
        FileInputStream penampungPathData;
        try {
        	penampungPathData = new FileInputStream(pathFile);
        	dataContainer = getFileContent(penampungPathData);
        } catch(Exception e){
        	System.out.println(e);
        }
        System.out.println(dataContainer);
        g.drawString(dataContainer, 100, 100);

        return PAGE_EXISTS;
    }

	public static String getPathFile(String path){
		return path;
	}

	public static String getFileContent(FileInputStream fis) throws Exception{
	    StringBuilder sb = new StringBuilder();
	    Reader r = new InputStreamReader(fis, "UTF-8");
	    int ch = r.read();
	    while(ch >= 0) {
	        sb.append((char)ch);
	        ch = r.read();
	    }
	    return sb.toString();
	}

	public static void main(String[] args){
		Printer a = new Printer();
	}

}