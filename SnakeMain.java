/**
 * Created by xtstc131 on 16/6/15.
 */
package MySnake;

import javax.swing.*;

public class SnakeMain extends JFrame
{
    public  SnakeMain()
    {

        SnakePanel sp = new SnakePanel();
        add(sp);
        setTitle("Snake");
        setSize(500,500);
        setVisible(true);

    }
}
