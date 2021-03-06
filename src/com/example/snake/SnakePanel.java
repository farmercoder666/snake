package com.example.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {
    //加载所有需要用到的图片
    ImageIcon up = new ImageIcon("images/up.jpg");
    ImageIcon down = new ImageIcon("images/down.jpg");
    ImageIcon right = new ImageIcon("images/right.jpg");
    ImageIcon left = new ImageIcon("images/left.jpg");
    ImageIcon body = new ImageIcon("images/body.png");
    ImageIcon title = new ImageIcon("images/title.png");
    ImageIcon food = new ImageIcon("images/food.png");

    //蛇的数据结构
    int[] sankex = new int[750];
    int[] snakey = new int[750];
    int len=3;
    String direction = "R";

    //统计分数
    int score=0;

    //食物的数据
    Random random = new Random();
    int foodx = random.nextInt(34)*25+25;
    int foody = random.nextInt(22)*25+75;

    //游戏是否开始
    boolean isStarted = false;

    //游戏是否失败
    boolean isFailed = false;

    //定时器
    Timer timer = new Timer(1,this);

    public SnakePanel() {
        this.setFocusable(true);
        initSnake();
        this.addKeyListener(this);//添加监听
        timer.start();
    }

    //初始化蛇
    public void initSnake(){
        isFailed=false;
        isStarted=false;
        len=3;
        score=0;
        direction="R";
        sankex[0]=100;
        snakey[0]=100;
        sankex[1]=75;
        snakey[1]=100;
        sankex[2]=50;
        snakey[2]=100;
    }

    public void paint(Graphics g){
        //设置画布的背景颜色
        this.setBackground(Color.BLACK);
        g.fillRect(25,75,850,550);

        //设置标题
        //title.paintIcon(this,g,25,11);
        //right.paintIcon(this,g,25,100);

        //画蛇头
        if(direction=="R"){
            right.paintIcon(this,g,sankex[0],snakey[0]);
        }else if(direction=="L") {
            left.paintIcon(this, g, sankex[0], snakey[0]);
        }else if(direction=="U"){
            up.paintIcon(this,g,sankex[0],snakey[0]);
        }else if(direction=="D"){
            down.paintIcon(this,g,sankex[0],snakey[0]);
        }
        //画蛇身
        for(int i=1;i<len;i++){
            body.paintIcon(this,g,sankex[i],snakey[i]);
        }
        //画开始提示语
        if(!isStarted){
            g.setColor(Color.WHITE);
            g.setFont(new Font("castellar",Font.BOLD,30));
            g.drawString("Press Space to Start/Pause",300,300);
        }
        //画失败提示语
        if(isFailed){
            g.setColor(Color.WHITE);
            g.setFont(new Font("castellar",Font.BOLD,30));
            g.drawString("Game Over,Press Space to Start",300,300);
        }
        //画食物
        food.paintIcon(this,g,foodx,foody);

        //画标题
        g.setColor(Color.RED);
        g.setFont(new Font("Snap ITC",Font.BOLD,30));
        g.drawString("SNAKE BATTLE",300,50);

        //分数和长度的统计
        g.setColor(Color.GREEN);
        g.setFont(new Font("arial",Font.PLAIN,15));
        g.drawString("Score:"+score,800,100);
        g.drawString("Length:"+len,800,125);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if(keyCode==KeyEvent.VK_SPACE){
            if(isFailed){
                initSnake();
            }else{
                isStarted=!isStarted;
            }
        }else if(keyCode==KeyEvent.VK_UP&&!direction.equals("D")){
            direction="U";
        }else if(keyCode==KeyEvent.VK_DOWN&&!direction.equals("U")){
            direction="D";
        }else if(keyCode==KeyEvent.VK_LEFT&&!direction.equals("R")){
            direction="L";
        }else if(keyCode==KeyEvent.VK_RIGHT&&!direction.equals("L")){
            direction="R";
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    /*
    1.重新定个闹钟
    2.蛇移动
    3.重画
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();

        if(isStarted&&!isFailed){
            //移动身体
            for(int i=len;i>0;i--){
                sankex[i]=sankex[i-1];
                snakey[i]=snakey[i-1];
            }
            //移动头
            if(direction=="R"){
                //横坐标+25
                sankex[0]+=25;
                if(sankex[0]>850){
                    sankex[0]=25;
                    //isFailed=true;
                }
            }else if(direction=="L") {
                sankex[0]-=25;
                if(sankex[0]<25){
                    sankex[0]=850;
                    //isFailed=true;
                }
            }else if(direction=="U"){
                snakey[0]-=25;
                if(snakey[0]<75){
                    snakey[0]=600;
                    //isFailed=true;
                }
            }else if(direction=="D"){
                snakey[0]+=25;
                if(snakey[0]>600){
                    snakey[0]=75;
                    //isFailed=true;
                }
            }
            //吃食物
            if(sankex[0]==foodx&&snakey[0]==foody){
                len++;
                score++;
                foodx = random.nextInt(34)*25+25;
                foody = random.nextInt(22)*25+75;

            }
            //死亡
            for(int i=1;i<len;i++){
                if(sankex[i]==sankex[0]&&snakey[i]==snakey[0]){
                    isFailed=true;
                }
            }
        }
        repaint();
    }
}
