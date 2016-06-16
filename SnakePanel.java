/**
 * Created by xtstc131 on 16/6/15.
 */
package MySnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class SnakePanel extends JPanel implements ActionListener,KeyListener,Runnable
{
    //分数与速度
    int Score = 0;
    int Speed = 0;
    //食物坐标的随机数
    int rx = 0 ,ry = 0;
    //表示方向
    int FangX = 0;
    //表示是否开始的布尔值
    boolean StartBool = false;
    //表示是否死亡的布尔值
    boolean dead = false;
    JButton start ,end;
    JDialog dialog = new JDialog();
    JLabel label = new JLabel();
    Random r  = new Random();
    Thread nthread = new Thread(this);
    List<SnakeBody> snakeBodyList = new ArrayList<>();

    public  SnakePanel()
    {
        start = new JButton("Start");
        end  = new JButton("End");
        start.addActionListener(this);
        end.addActionListener(this);
        addKeyListener(this);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(start);
        add(end);
        dialog.add(label);
        dialog.setLayout(new FlowLayout(FlowLayout.CENTER));
        dialog.setSize(150,50);
        dialog.setVisible(false);


    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawRect(10, 40, 480, 430);
        g.drawString("Score: " + Score, 200, 22);
        g.drawString("Speed: " + Speed, 270, 22);
        g.setColor(new Color(175, 255, 164));
        if (StartBool)
        {
            g.fillRect(10 + rx * 10  ,40 + ry * 10, 10 , 10);
        }
        g.setColor(new Color(255, 200, 56));
        if (dead)
        {
            g.setColor(Color.BLACK);
        }
        for (int i = 0 ; i< snakeBodyList.size();i++)
        {
            g.fillRect(10+snakeBodyList.get(i).getX()*10,40 +snakeBodyList.get(i).getY()*10,10,10);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == start)
        {
            start.setEnabled(false);
            StartBool = true;
            rx = r.nextInt(48);
            ry = r.nextInt(43);
            SnakeBody sb = new SnakeBody();
            sb.setX(2);
            sb.setY(3);
            snakeBodyList.add(sb);
            requestFocus(true);
            nthread = new Thread(this);
            nthread.start();
            repaint();


        }
        else if(e.getSource() == end)
        {
            System.exit(0);
        }

    }
    public  void up()
    {
        FangX = 1;
        OtherMove();
        if (snakeBodyList.get(0).getY() > 0)
        {
            snakeBodyList.get(0).setY(snakeBodyList.get(0).getY() - 1);
        }
        else
        {
            snakeBodyList.get(0).setY(42);
        }
        die();
        eat();
        repaint();

    }
    public  void down()
    {
        FangX = 2;
        OtherMove();
        if (snakeBodyList.get(0).getY() < 42)
        {
            snakeBodyList.get(0).setY(snakeBodyList.get(0).getY() + 1);
        }
        else
        {
            snakeBodyList.get(0).setY(0);
        }
        die();
        eat();
        repaint();
    }

    public  void left()
    {
        FangX = 3;
        OtherMove();
        if (snakeBodyList.get(0).getX() > 0)
        {
            snakeBodyList.get(0).setX(snakeBodyList.get(0).getX() - 1);
        }
        else
        {
            snakeBodyList.get(0).setX(47);
        }
        die();
        eat();
        repaint();

    }

    public  void right()
    {
        FangX = 4;
        OtherMove();
        if (snakeBodyList.get(0).getX() < 47)
        {
            snakeBodyList.get(0).setX(snakeBodyList.get(0).getX() + 1);
        }
        else
        {
            snakeBodyList.get(0).setX(0);
        }
        die();
        eat();
        repaint();

    }
    public  void  eat()
    {
        if(rx == snakeBodyList.get(0).getX() && ry == snakeBodyList.get(0).getY())
        {
            Score += 10;
            SnakeBody temp  = new SnakeBody();
            rx = r.nextInt(48);
            ry = r.nextInt(43);
            temp.setX(snakeBodyList.get(snakeBodyList.size() - 1).getX());
            temp.setY(snakeBodyList.get(snakeBodyList.size() - 1).getY());
            snakeBodyList.add(temp);

        }

    }
    public  void  die()
    {
        for (int  i = 1 ; i < snakeBodyList.size();i++)
        {
            if (snakeBodyList.get(0).getX() == snakeBodyList.get(i).getX() && snakeBodyList.get(0).getY() == snakeBodyList.get(i).getY())
            {
                dead = true;
                //start.setEnabled(true);
                label.setText("Your Score is: "+Score);
                dialog.setVisible(true);
                break;
            }
        }

    }
    public  void  OtherMove()
    {
        SnakeBody tempSB;
        for (int i = 0 ; i < snakeBodyList.size() ; i++)
        {
            if (i == 1)
            {
                snakeBodyList.get(i).setX(snakeBodyList.get(0).getX());
                snakeBodyList.get(i).setY(snakeBodyList.get(0).getY());
            }
            else if(i > 1)
            {
                tempSB = snakeBodyList.get(i-1);
                snakeBodyList.set(i - 1,snakeBodyList.get(i));
                snakeBodyList.set(i ,tempSB);
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (StartBool)
        {
            if (FangX == 1 || FangX ==2)
            {
                switch (e.getKeyCode())
                {

                    case KeyEvent.VK_LEFT:
                        left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        right();
                        break;
                }
            }

            if (FangX == 3 ||FangX == 4)
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_UP:
                        up();
                        break;
                    case KeyEvent.VK_DOWN:
                        down();
                        break;
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e){
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public  void  run()
    {
        while (StartBool)
        {
            if (dead)
            {
                nthread.interrupt();
                nthread = null;
                break;
            }
            switch (FangX) {
                case 1:
                    up();
                    break;
                case 2:
                    down();
                    break;
                case 3:
                    left();
                    break;
                case 4:
                    right();
                    break;
                default:
                    up();
                    break;
            }
            if (!dead)
            {
                repaint();
            }
            if(!dead)
            {
                try
                {
                    Thread.sleep(50);
                }catch (InterruptedException e){e.printStackTrace();}
            }
        }
    }
}
