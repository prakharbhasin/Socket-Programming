
/**
* Name: Prakhar Bhasin 
* Roll Number: 1810110167
*/

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;



import java.awt.*;

class ImagePanel extends JFrame{
    BufferedImage image;
    public ImagePanel(BufferedImage img) {
        JPanel p = new JPanel();
        p.setBackground(new Color(48, 71, 94)); // blue color
        JPanel paint = new JPanel();
        paint.setBackground(new Color(232, 232, 232)); // white color

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        JLabel l = new JLabel("Prakhar Bhasin");
        JLabel ll = new JLabel("Socket Programming Assignment");

        Color c1 = new Color(48, 71, 94); // blue color
        l.setForeground(new Color(240, 84, 84)); // red color
        l.setBackground(c1);
        ll.setForeground(new Color(232, 232, 232));

        Font myfont = new Font("Monospace", Font.BOLD, 24);
        Font myfont1 = new Font("Sans Serif", Font.BOLD, 18);

        l.setFont(myfont);
        ll.setFont(myfont1);
        p1.setBackground(c1);
        p1.add(l, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(232, 232, 232));
        panel.add(p1);
        panel.add(p);
        panel.setLayout(new GridLayout(2, 1));
        p.add(ll, BorderLayout.CENTER);

        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        p2.setBackground(new Color(232, 232, 232));
        p2.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.add(panel, BorderLayout.NORTH);
        this.add(p2, BorderLayout.SOUTH);
        this.image = img;
        setSize(700,700);
        setTitle("Graph");
        setVisible(true);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }
}

public class client {

    public static String evalResult(boolean result, char source, char dest, int pathLength) {
        if (result == false) {
            return "Path of length " + pathLength + " does not exist from " + source + " to " + dest;
        }

        return "Path of length " + pathLength + " exists from " + source + " to " + dest;
    }

    public static void main(String[] args) throws Exception {
        int numNodes = 5;
        int array[][] = new int[numNodes][numNodes];
        Scanner input = new Scanner(System.in);

        System.out.println("Enter array: ");

        for (int row = 0; row < numNodes; row++) {
            for (int col = 0; col < numNodes; col++) {

                array[row][col] = input.nextInt();

            }
        }
        System.out.println("Array is:");
        for (int row = 0; row < numNodes; row++) {
            for (int col = 0; col < numNodes; col++) {
                System.out.print(array[row][col] +" ");}
            System.out.println("");}

        System.out.println("Enter Source");
        char s = input.next().charAt(0);

        System.out.println("Enter Destination");
        char d = input.next().charAt(0);

        System.out.println("Enter Path Length");
        int n = input.nextInt();

        try {
            Socket client = new Socket("localhost", 6660);

            DataInputStream ip = new DataInputStream(client.getInputStream());
            DataOutputStream dataOutput = new DataOutputStream(client.getOutputStream());

            dataOutput.writeChar(s);
            dataOutput.flush();

            dataOutput.writeChar(d);
            dataOutput.flush();

            dataOutput.writeInt(n);
            dataOutput.flush();

            dataOutput.writeInt(numNodes);
            dataOutput.flush();

            for (int i = 0; i < numNodes; i++)
                for (int j = 0; j < numNodes; j++)
                    dataOutput.writeInt(array[i][j]);
            dataOutput.flush();

            boolean res = ip.readBoolean();
            System.out.println(evalResult(res, s, d, n));

            BufferedImage image = ImageIO.read(ip);
            ImageIO.write(image,"png", new File("graph.jpg"));
            System.out.println("Saved Imaged Successfully!");
            new ImagePanel(image);
            

            client.close();

        } catch (IOException ex) {
        }
    }
}
