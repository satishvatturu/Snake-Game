import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener
{
    // setting the size of  the panel
    static final int width = 1200;
    static final int height = 600;

    int unit = 50;

    boolean flag = false;

    Random random;
    int score;

    int fx,fy;

    char dir = 'R';

    Timer timer;

    int length = 3;
    int[] xSnake = new int[288];
    int[] ySnake = new int[288];

    panel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        random = new Random();

        this.setFocusable(true); // to use the keys in the keyboards
        this.addKeyListener(new key());

        gameStart();
    }

    public void gameStart()
    {
        spawnFood();
        flag = true;
        timer = new Timer(160, this);
        timer.start();

    }

    // to assign random x and y coordinate to the food particle
    public void spawnFood()
    {
        fx = random.nextInt(width/unit) * 50;
        fy = random.nextInt(height/unit) * 50;
    }

    public void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic)
    {
        // when the game is running
        if(flag)
        {
            // drawing the food particle
            graphic.setColor(Color.yellow);
            graphic.fillOval(fx,fy,unit,unit); // radius of circle will be 50

            // to draw the snake
            for(int i=0;i<length;i++)
            {
                graphic.setColor(Color.green);
                graphic.fillRect(xSnake[i],ySnake[i],unit,unit);
            }

            // for drawing the score element
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD,40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Score: " + score, (width-fme.stringWidth("Score: " + score))/2,graphic.getFont().getSize());

        }
        //when the game is not running
        else
        {
            // to display the final score
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD,40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Score: " + score, (width-fme.stringWidth("Score: " + score))/2,graphic.getFont().getSize());

            // to display the game over text
            graphic.setColor(Color.RED);
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD,80));
            fme = getFontMetrics(graphic.getFont());
            graphic.drawString("GAME OVER!", (width-fme.stringWidth("GAME OVER!"))/2,height/2);

            // to display the replay prompt
            graphic.setColor(Color.GREEN);
            graphic.setFont(new Font("Comic Sans MS", Font.BOLD,40));
            fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Press R to replay", (width-fme.stringWidth("Press R to replay"))/2,height/2+150 );
        }
    }

    public void move()
    {
        for (int i=length;i>0;i--)
        {
            xSnake[i] = xSnake[i-1];
            ySnake[i] = ySnake[i-1];
        }

        switch (dir)
        {
            case 'R' : xSnake[0] = xSnake[0] + unit;
                        break;
            case 'L' : xSnake[0] = xSnake[0] - unit;
                        break;
            case 'U' : ySnake[0] = ySnake[0] - unit;
                        break;
            case 'D' : ySnake[0] = ySnake[0] + unit;
        }

    }

    public void foodEaten()
    {
        if((xSnake[0] == fx) && (ySnake[0] == fy))
        {
            length++;
            score++;
            spawnFood();
        }
    }

    public void checkHit()
    {
        // to check with the boundaries
        if(ySnake[0]<0)
        {
            flag = false;
        }
        if(ySnake[0]>600)
        {
            flag = false;
        }
        if(xSnake[0]<0)
        {
            flag = false;
        }
        if(xSnake[0]>1200)
        {
            flag = false;
        }

        // to check with its own body
        for(int i=length;i>0;i--)
        {
            if((xSnake[0] == xSnake[i]) && (ySnake[0] == ySnake[i]))
            {
                flag = false;
            }
        }

        if(flag == false)
        {
            timer.stop();
        }

    }

    public class key extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_LEFT :
                    if(dir != 'R')
                    {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT :
                    if(dir != 'L')
                    {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_UP :
                    if(dir != 'D')
                    {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir != 'U')
                    {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_R :
                    score = 0;
                    length = 3;
                    dir = 'R';
                    Arrays.fill(xSnake,0);
                    Arrays.fill(ySnake,0);
                    gameStart();
            }
        }
    }

    public void actionPerformed(ActionEvent evt)
    {
        if(flag)
        {
            move();
            foodEaten();
            checkHit();
        }
        repaint(); // it recalls the paintComponent function or it redraws everything with the new coordinates
    }

}
