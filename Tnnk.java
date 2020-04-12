package Tnnk;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;

public class Tnnk extends JPanel implements KeyListener {

    ArrayList<Point> list = new ArrayList<>();
    ArrayList<Point> list2 = new ArrayList<>();
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Point> trees = new ArrayList<>();
    
    String turn = "red";

    Tnk tnk;
    Tnk2 tnk2;

    boolean starting = true;

    int power = 110;
    int power2 = 110;
    int animationSpeed = 1;
    static final double G = 980.00;
    static int size = 900, ballDiameter = 10;
    double startX, startY, ballX, ballY;
    double xSpeed, ySpeed, lastPointX, lastPointY;
    double time, deltaTime = 0.002;
    Timer timer;

    boolean s = true;
    boolean s2 = true;
    int angle = 0;
    double aangle = 0;
    int angle2 = 0;
    double aangle2 = 0;

    boolean shooting = false;

    Polygon ppp = new Polygon();

    Random rand = new Random();

    int diffX = 111110, diffY = 111110;

    int was = 18;
    
    Graphics g = null;    
    JFrame j = new JFrame();

    class Point {
        int x, y;
    }
    
    class Tnk {
        int x, y;
        int sqtop_x, sqtop_y;
        int sqbot_x, sqbot_y;
        int can_x, can_y;
        
        Tnk(int xx, int yy) {
            x = xx;
            y = yy;
            sqbot_x = x;
            sqbot_y = y;
            sqtop_x = sqbot_x + 20;
            sqtop_y = sqbot_y - 30;
            can_x = sqtop_x + 50;
            can_y = sqtop_y;
        }
        
        void drawMe() {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.red);
            g2.fillRect(sqbot_x, sqbot_y, 90, 20);
            g2.setColor(Color.black);
            g2.fillRect(sqbot_x, sqbot_y+20, 90, 15);
            g2.setColor(Color.red);
            g2.fillRect(sqtop_x, sqtop_y, 50, 30);
            g2.setColor(Color.black);
            g2.drawRect(sqbot_x-1, sqbot_y-1, 92, 22);
            g2.setColor(Color.black);
            g2.drawRect(sqbot_x-1, sqbot_y+20-1, 92, 17);
            g2.setColor(Color.black);
            g2.drawRect(sqtop_x-1, sqtop_y-1, 52, 32);
            g2.setStroke(new BasicStroke(10));
            g2.setColor(Color.red);
            if(s) {
                angle = can_y+10;
                s = false;
            }
            g2.drawLine(can_x, can_y+10, can_x + 30, angle);
        }
    }
    
    class Tnk2 {
        int x, y;
        int sqtop_x, sqtop_y;
        int sqbot_x, sqbot_y;
        int can_x, can_y;
        
        Tnk2(int xx, int yy) {
            x = xx;
            y = yy;
            sqbot_x = x;
            sqbot_y = y;
            sqtop_x = sqbot_x + 20;
            sqtop_y = sqbot_y - 30;
            can_x = sqtop_x + 50;
            can_y = sqtop_y;
        }
        
        void drawMe() {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.green);
            g2.fillRect(sqbot_x, sqbot_y, 90, 20);
            g2.setColor(Color.black);
            g2.fillRect(sqbot_x, sqbot_y+20, 90, 15);
            g2.setColor(Color.green);
            g2.fillRect(sqtop_x, sqtop_y, 50, 30);
            g2.setColor(Color.black);
            g2.drawRect(sqbot_x-1, sqbot_y-1, 92, 22);
            g2.setColor(Color.black);
            g2.drawRect(sqbot_x-1, sqbot_y+20-1, 92, 17);
            g2.setColor(Color.black);
            g2.drawRect(sqtop_x-1, sqtop_y-1, 52, 32);
            g2.setStroke(new BasicStroke(10));
            g2.setColor(Color.green);
            if(s2) {
                angle2 = can_y+10;
                s2 = false;
            }
            g2.drawLine(can_x-50, can_y+10, can_x - 80, angle2);
        }
    }

    public Tnnk() {

        setGUI();

        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        tnk.drawMe();
                        tnk2.drawMe();
                        Thread.sleep(100);
                    } catch(Exception e) {}
                }
            }
        };
        t.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if(starting) {
            list.clear();
            list2.clear();
        }

        for(int i=0; i<801; i++) {
            g.setColor(new Color(100, 155, 55+i/4));
            g.drawLine(0, i, 1200, i);
        }
        if(starting)
        for(int i=0; i<24; i++) {
            int v = 300 + rand.nextInt(80);
            Point o = new Point();
            o.x = i*50;
            o.y = v;
            list.add(o);
            
            if(i==3) {
                tnk = new Tnk(o.x, o.y);
            }

            if(i==18) {
                tnk2 = new Tnk2(o.x, o.y);
            }
        }
        g.setColor(new Color(140, 250, 70));
        points.clear();
        for(int i=0; i+1<list.size(); i++) {

            double v = (double)list.get(i+1).x;
            double u = -(double)list.get(i+1).y;
            double t = (double)list.get(i).x;

            double w = 0d;

            double m = 0d;
            double y1 = 0d;

            m = (u-(-1*(double)list.get(i).y))/(v - t);

            for(int j=(int) t; j<v; j++) {
                Point o = new Point();

                w = j;

                y1 = u - m*(v - w);

                y1 = Math.round(y1);

                int x = (int) w;
                int y = -(int) y1;

                o.x = x;
                o.y = y;

                points.add(o);
            }
        }
        if(starting)
        for(int i=0; i<122; i++) {
            int v = 20 + rand.nextInt(130);
            Point o = new Point();
            o.x = 20 + rand.nextInt(1100);
            o.y = v;
            list2.add(o);
        }
        g2.setColor(Color.WHITE);
        for(int i=0; i<list2.size(); i++) {
            try {
                g2.drawOval(list2.get(i).x, list2.get(i).y, 2, 2);
            } catch(Exception e) {}
        }
        ppp = new Polygon();
        for(int i=0; i<points.size(); i++) {
            ppp.addPoint(points.get(i).x, points.get(i).y);
        }
        ppp.addPoint(1200, 800);
        ppp.addPoint(0, 800);
        GradientPaint brownToSand = new GradientPaint(50, 50, new Color(102, 51, 0),
            800, 100, new Color(76, 70, 50));
        g2.setPaint(brownToSand);
        g2.fillPolygon(ppp);
        Polygon it = new Polygon();
        for(int i=0; i< 10; i++) {
            int y = 600 + rand.nextInt(30);
            int x = 100 + i*100 + rand.nextInt(30);
            it.addPoint(x, y);
        }
        it.addPoint(1100, 700);
        it.addPoint(500, 670);
        it.addPoint(100, 700);
        GradientPaint greenToBlue = new GradientPaint(150, 100, new Color(200, 255, 200),
            900, 100, Color.BLUE);
        g2.setPaint(greenToBlue);
        g2.fillPolygon(it);
        if(starting)
            for(int i=0; i<50; i++) {
                Point o = new Point();
                o.x = rand.nextInt(1200);
                o.y = 500 + rand.nextInt(200);
                trees.add(o);
            }
        for(int i=0; i<trees.size(); i++) {
            g.setColor(new Color(200, 100, 50));
            g.fillRect(trees.get(i).x+10, trees.get(i).y+25, 4, 45);
        }
        for(int i=0; i<trees.size(); i++) {
            Polygon tres = new Polygon();
            for(int j=0; j<30; j++) {
                Point o = new Point();
                o.x = trees.get(i).x - 15 + rand.nextInt(14) + j;
                o.y = trees.get(i).y + rand.nextInt(14) + j;
                tres.addPoint(o.x, o.y);
            }
            g2.setColor(Color.GREEN);
            g2.fillPolygon(tres);
        }
        if(starting)
            starting = false;
        
        g2.setColor(Color.GRAY);
        g2.fillOval(1000, 50, 100, 100);
        g2.setColor(new Color(150, 150, 150));
        g2.fillOval(1030, 60, 20, 20);
        g2.setColor(new Color(150, 150, 150));
        g2.fillOval(1060, 80, 20, 20);
        g2.setColor(new Color(150, 150, 150));
        g2.fillOval(1030, 100, 20, 20);

        g2.setColor(Color.BLUE);

        g2.setFont(new Font("arial", Font.PLAIN, 25));
        g2.drawString("Tank Fighter 2", 100, 30);
        
        g2.setFont(new Font("arial", Font.PLAIN, 15));
        g2.setColor(Color.RED);
        g2.drawString("RED: POWER: " + power + "   ANGLE: " + aangle, 100, 70);
        g2.setColor(Color.GREEN);
        g2.drawString("GREEN: POWER: " + power2 + "   ANGLE: " + aangle2, 100, 100);

        g2.setColor(Color.BLACK);
        
        g2.drawString("Power: LEFT / RIGHT", 100, 140);
        g2.drawString("Angle: UP / DOWN", 100, 170);
        g2.drawString("To shoot press SPACE", 100, 200);
    }

    private void drawExplosion(int x, int y) {
        java.awt.Image imgFb = null;

        String imageFb = "explosion.gif";

        javax.swing.ImageIcon iFb = new javax.swing.ImageIcon(this.getClass().getResource(imageFb));
        imgFb = iFb.getImage();

        g.drawImage(imgFb, x, y, 200, 200, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            if(turn.equals("red")) {
                angle -= 4;
                aangle += 7;
            } else if(turn.equals("green")) {
                angle2 -= 4;
                aangle2 += 7;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(turn.equals("red")) {
                angle += 4;
                aangle -= 7;
            } else if(turn.equals("green")) {
                angle2 += 4;
                aangle2 -= 7;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(turn.equals("red")) {
                power--;
            } else if(turn.equals("green")) {
                power2--;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(turn.equals("red")) {
                power++;
            } else if(turn.equals("green")) {
                power2++;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(!shooting) {
                shooting = true;
                time = 0;
                if(turn.equals("red")) {
                    ballX= lastPointX = startX = tnk.x + 90;
                    ballY = lastPointY = startY = tnk.y - 30;
                } else if(turn.equals("green")) {
                    ballX= lastPointX = startX = tnk2.x;
                    ballY = lastPointY = startY = tnk2.y - 30;
                }
                getUserInput();

                timer = new Timer(animationSpeed, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {

                        if(time != -1) {
                            try {
                                moveBall();
                            } catch(Exception e) {}

                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                            g2d.setColor(Color.BLUE);
                            g2d.fillOval((int)ballX,(int)ballY,ballDiameter,ballDiameter);

                            repaint();

                            if(!inBounds()) {
                                timer.stop();
                                shooting = false;
                            }
                        }
                    }
                });
                timer.start();
            }
        }
        repaint();
        
        tnk.drawMe();
        tnk2.drawMe();
    }

    private void moveBall() throws Exception {
        if(turn.equals("red")) {
            ballX = startX + (xSpeed * time);
        } else if(turn.equals("green")) {
            ballX = startX - (xSpeed * time);
        }
        ballY = startY - ((ySpeed *time)-(1.0 *G * Math.pow(time, 2))) ;
        time += deltaTime;
        
        int minX = 111110, minY = 111110;
        int listFound = -1;
        
        int x = -10, y = -10;
        
        for(int i=0; i<points.size(); i++) {
            int x2 = points.get(i).x;
            int y2 = points.get(i).y;
            if(ballX >= x2 && ballX <= x2 + 13 &&
                    ballY >= y2 && ballY <= y2 + 13) {
                x = x2;
                y = y2;
            }
        }
        
        double dis = 0d;
                
        double ddis = 100000d;
        
        if(x >= 0 && y >= 0)
        for(int xxxx=0; xxxx<list.size(); xxxx++) {
            int x2 = list.get(xxxx).x;
            int y2 = list.get(xxxx).y;

            dis = Math.sqrt((x2-x)*(x2-x) + (y2-y)*(y2-y));

            if(dis < ddis) {
                minX = x2;
                minY = y2;
                ddis = dis;
                listFound = xxxx;
            }
        }
        
        if(x >= 0 && y >= 0) {
            diffX = 111110; diffY = 111110;
        }

        if(x >= 0 && y >= 0)
        if(minX != 111110 && minY != 111110) {
            drawExplosion(minX,minY);

            list.remove(list.get(listFound+1));
            list.remove(list.get(listFound+2));

            Point l = new Point();
            l.x = (int) minX + 2;
            l.y = (int) minY+30;
            list.add(listFound+1, l);
            l = new Point();
            l.x = (int) minX + 20;
            l.y = (int) minY + 30;
            list.add(listFound+2, l);

            list.get(listFound).x = list.get(listFound).x - 50;
            list.get(listFound).y = l.y + 30;

            list.get(listFound+1).x = list.get(listFound+1).x;
            list.get(listFound+1).y = l.y + 30;

//            list.get(listFound+2).x = list.get(listFound+2).x;
//            list.get(listFound+2).y = l.y + 30;

            was++;
            
            time = -1;

            shooting = false;
        }

        repaint();

        if(!shooting) {
            if(turn.equals("red")) {

                try {
                    tnk = new Tnk(list.get(3).x, list.get(3).y);
                    angle = 0;
                    aangle = 0;
                    s = true;
                    tnk.drawMe();
                } catch(Exception e3) {}

                turn = "green";

            } else if(turn.equals("green")) {

                try {
                    tnk2 = new Tnk2(list.get((18)).x, list.get((18)).y);
                    angle2 = 0;
                    aangle2 = 0;
                    s2 = true;
                    tnk2.drawMe();
                } catch(Exception e3) {}

                turn = "red";

            }
        }
    }

    private void getUserInput() {

        double speed = 0d;
        if(turn.equals("red")) {
            speed = 10 * power;
            xSpeed = speed * Math.cos(aangle * (Math.PI / 180));
            ySpeed = speed * Math.sin(aangle * (Math.PI / 180));
        } else if(turn.equals("green")) {
            speed = 10 * power2;
            xSpeed = speed * Math.cos(aangle2 * (Math.PI / 180));
            ySpeed = speed * Math.sin(aangle2 * (Math.PI / 180));
        }
    }

    private boolean inBounds() {
        if((ballX < 0) || (ballX > (getWidth())) || ( ballY  > (getHeight() - ballDiameter))) {
            return false;
        }
        return true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    void setGraphics() {
        
        g = this.getGraphics();
    }
    
    void setGUI() {
 
        j.setTitle("Tank Fighter 2");
        
        j.setLayout(null);
        j.setBounds(0, 0, 1200, 800);
        this.setBounds(j.getBounds());
        j.add(this);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setVisible(true);
  
        setGraphics();
 
        j.addKeyListener(this);
    }
    
    public static void main(String[] args) {
       try {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Tnnk();
                }
            });
        } catch(Exception e) {}
    }
}