
/**
* Name: Prakhar Bhasin 
* Roll Number: 1810110167
*/

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static java.awt.geom.AffineTransform.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;

class drawGraph extends JPanel {
    int[][] a = new int[5][5];

    public drawGraph(int[][] graph) {
        this.a = graph;
    }

    private void drawArrow(Graphics2D g2d, double x1, double y1, double x2, double y2) {
        double x, y;
        double phi = Math.PI / 6;
        double inradius = 10;
        double dy = y2 - y1;
        double dx = x2 - x1;
        double theta = Math.atan2(dy, dx);
        double finalAngle = phi + theta;
        for (int j = 0; j < 2; j++) {
            x = (x2 - inradius * Math.cos(finalAngle));
            y = (y2 - inradius * Math.sin(finalAngle));
            g2d.draw(new Line2D.Double(x2, y2, x, y));
            finalAngle = theta - phi;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 700, 700);
        Graphics2D g2 = (Graphics2D) g;
        double x = 325;
        double y = 325;
        double r = 175;
        double theta = Math.toRadians(72);
        g2.setStroke(new BasicStroke(2));
        double doubleCoordinates[][] = { { x, y - r }, { x - 0.9 * r * Math.sin(theta), y - r * Math.cos(theta) },
                { x + 0.9 * r * Math.sin(theta), y - r * Math.cos(theta) },
                { x - 0.72 * r * Math.sin(theta), y + 2 * r * Math.cos(theta) },
                { x + 0.72 * r * Math.sin(theta), y + 2 * r * Math.cos(theta) }, };

        final int[][] intCoordinates = new int[doubleCoordinates.length][2];
        for (int i = 0; i < intCoordinates.length; i++)
            for (int j = 0; j < 2; j++)
                intCoordinates[i][j] = (int) doubleCoordinates[i][j];
        


        double[][][] lineCoordinates = {
                { {}, { doubleCoordinates[0][0], doubleCoordinates[0][1] + 35 + 17.5, doubleCoordinates[1][0] + 52.5,
                        doubleCoordinates[1][1] },
                        { doubleCoordinates[0][0] + 35 + 35, doubleCoordinates[0][1] + 70 - 17.5,
                                doubleCoordinates[2][0] + 35 - 17.5, doubleCoordinates[2][1] },
                        { doubleCoordinates[0][0] - 17.5 + 35, doubleCoordinates[0][1] + 70,
                                doubleCoordinates[3][0] + 17.5 + 35, doubleCoordinates[3][1] },
                        { doubleCoordinates[0][0] + 17.5 + 35, doubleCoordinates[0][1] + 70,
                                doubleCoordinates[4][0] - 17.5 + 35, doubleCoordinates[4][1] } },

                { {}, {},
                        { doubleCoordinates[1][0] + 35 + 45, doubleCoordinates[1][1] + 35, doubleCoordinates[2][0] - 10,
                                doubleCoordinates[2][1] + 35 },
                        { doubleCoordinates[1][0] + 6 + 35, doubleCoordinates[1][1] + 70,
                                doubleCoordinates[3][0] - 6 + 35, doubleCoordinates[3][1] },
                        { doubleCoordinates[1][0] + 70, doubleCoordinates[1][1] + 70 - 17.5, doubleCoordinates[4][0],
                                doubleCoordinates[4][1] + 17.5 }, },

                { {}, {}, {},
                        { doubleCoordinates[2][0], doubleCoordinates[2][1] + 17.5 + 35, doubleCoordinates[3][0] + 70,
                                doubleCoordinates[3][1] - 17.5 + 35 },
                        { doubleCoordinates[2][0] - 6 + 35, doubleCoordinates[2][1] + 70,
                                doubleCoordinates[4][0] + 6 + 35, doubleCoordinates[4][1] }, },
                { {}, {}, {}, {}, { doubleCoordinates[3][0] + 35 + 45, doubleCoordinates[3][1] + 35,
                        doubleCoordinates[4][0] - 10, doubleCoordinates[4][1] + 35 }, } };

        for (int i = 0; i < intCoordinates.length; ++i) {
            for (int j = 0; j < intCoordinates.length; ++j) {

                g.setColor(new Color(240, 84, 84));
                g.fillOval(intCoordinates[i][0], intCoordinates[i][1], 70, 70);
                g.setColor(new Color(48, 71, 94));
                g.drawOval(intCoordinates[i][0], intCoordinates[i][1], 70, 70);
                g.setColor(new Color(48, 71, 94));
                g.setFont(new Font("Sans Serif", Font.BOLD, 22));
                g.drawString(Character.toString((char) (i + 65)), intCoordinates[i][0] + 30, intCoordinates[i][1] + 40);

            }
        }

        // logic for drawing the edges of the directed graph
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                g2.setColor(new Color(48, 71, 94));

                if (a[i][j] == 1 || a[j][i] == 1)
                    g2.draw(new Line2D.Double(lineCoordinates[i][j][0], lineCoordinates[i][j][1],
                            lineCoordinates[i][j][2], lineCoordinates[i][j][3]));

                if (a[i][j] == 1)
                    drawArrow(g2, lineCoordinates[i][j][0], lineCoordinates[i][j][1], lineCoordinates[i][j][2],
                            lineCoordinates[i][j][3]);

                if (a[j][i] == 1)
                    drawArrow(g2, lineCoordinates[i][j][2], lineCoordinates[i][j][3], lineCoordinates[i][j][0],
                            lineCoordinates[i][j][1]);
            }
        }

    }
}

class graphFrame {
    /**
     * @param args the command line arguments
     */
    int s = 35;
    JPanel paint;

    graphFrame(int[][] graph,DataOutputStream op) {
        JFrame f = new JFrame();
        drawGraph graphDrawing = new drawGraph(graph);
        f.add(graphDrawing, BorderLayout.CENTER);

        
        f.setVisible(false);
        f.setSize(700, 700);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        obtainImg(graphDrawing,op);

    }

    public void obtainImg(drawGraph Frame, DataOutputStream outputStream) {
        try {
            BufferedImage image = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);
            Frame.paintComponent(image.getGraphics());
            ImageIO.write(image, "png", outputStream);
            

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    

}

public class server {

    public static boolean depthFirstSearch(int adjMat[][], int source, int des, int n, boolean visited[]) {
        visited[source] = true;
        if (adjMat[source][des] != 0 && n == adjMat[source][des])
            return true;
        for (int i = 0; i < adjMat.length; i++) {
            if (adjMat[source][i] != 0 && !visited[i]) {
                if (depthFirstSearch(adjMat, i, des, (n - adjMat[source][i]), visited))
                    return true;
            }
        }
        visited[source] = false;
        return false;
    }

    public static String evalResult(boolean result, char source, char dest, int pathLength) {
        if (result == false) {
            return "No, Path of length " + pathLength + " does not exist from " + source + " to " + dest;
        }

        return "Yes, Path of length " + pathLength + " exists from " + source + " to " + dest;
    }

    public static void main(String[] args) {

        boolean[] visited = { false, false, false };
        try {
            ServerSocket serverSocket = new ServerSocket(6660);
            System.out.println("Server Started at Port 6662");
            while (true) {
                Socket socket = serverSocket.accept();

                DataInputStream ip = new DataInputStream(socket.getInputStream());
                DataOutputStream op = new DataOutputStream(socket.getOutputStream());

                // double radius = ip.readDouble();

                // double area = radius*radius*Math.PI;
                int source = (int) (ip.readChar()) - 65;
                int dest = (int) (ip.readChar()) - 65;

                System.out.println(source);
                System.out.println(dest);

                int pathLen = ip.readInt();
                int nodes = ip.readInt();

                int adjMatrix[][] = new int[nodes][nodes]; // Create the matrix
                for (int i = 0; i < nodes; i++) {
                    for (int j = 0; j < nodes; j++) {
                        adjMatrix[i][j] = ip.readInt();
                    }
                }

                boolean res = depthFirstSearch(adjMatrix, source, dest, pathLen, visited);
                op.writeBoolean(res);
                graphFrame graphFrame = new graphFrame(adjMatrix,op);
                
                

            }
            //close server
        } catch (IOException ex) {
        }

        ;

    }
}
