package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //screen settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    public int maxScreenCol = 16;
    public int maxScreenRow = 12;

    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize *maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;
    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound se = new Sound();
    Sound music = new Sound();


    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public UI ui = new UI(this);
    Thread gameThread;
    //entity and object


    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];



    //set player's default pos
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        //draws everything
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame(){
        aSetter.setObject();

        playMusic(0);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }
    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;

            lastTime =currentTime;

            if(delta >= 1){

                update();
                repaint();
                delta --;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }



        }


        

    }

    public void update(){
        player.update();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        long drawStart = 0;
        if(keyH.checkDrawTime == true) {

            drawStart = System.nanoTime();
        }


        tileManager.draw(g2);

        for(int i = 0; i < obj.length; i++ ) {
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }

        }
        //playa
        player.draw(g2);

        ui.draw(g2);
        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;


            g2.setColor(Color.white);
            g2.drawString("Draw Time : " + passed, 10 ,400);
            System.out.println(passed);
        }


        g2.dispose();

    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }




}
