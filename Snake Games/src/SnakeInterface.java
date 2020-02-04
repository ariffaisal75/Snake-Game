
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.Random;
import static javafx.application.Platform.exit;
//import java.util.Timer;
import javax.swing.Timer;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arif
 */
public class SnakeInterface extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form SnakeInterface
     */
    private final int B_width=765; //  width of the panel
    private final int B_height=710; // height of the panel
    private final int dotSize=10; //size of dot
    private final int allDot=900; //maximum saiz of possible dot
    private final int rand_post=29;
    private final int rand_postY=75;
    private final int DELAY=140;
    
    
    private final int x[] = new int[allDot];
    private final int y[] = new int[allDot];
    
    private Timer timer;
    private Timer timer2;
    private Image snakeBody;
    private Image apple;
    private Image snakeHead;
    
    
    InsertBoard getName=new InsertBoard();
    private int dots;
    private int storeDot=3;
    private int apple_x;
    private int apple_y;
    
    private boolean leftDirection=false;
    private boolean rightDirection = true;
    private boolean downDirection = false;
    private boolean upDirection = false;
    private boolean inGame = true;
    private boolean pause=false;
    private boolean contLeft;
    private boolean contRight;
    private boolean contDown;
    private boolean contUp;
    private boolean up=false;
    private boolean down=false;
    private boolean left=false;
    private boolean right=false;
    
    public int life=3;
    TAdapter control=new TAdapter();
    public int score=0;
    
//    ProgressInterface test1=new ProgressInterface();
    
    public SnakeInterface() {
        initComponents();
        
        
        lifeNumber.setText(Integer.toString(life));
        scoreNumber.setText(Integer.toString(score));
        initBoard();
        setResizable(false);
//        pack()
       
        
    }
    private void initBoard(){
        
        addKeyListener(control);
        grassField.setPreferredSize(new Dimension(B_width,B_height));
        initGame();
       
        loadImages();
//        snakeBody=test1.loadBody();
//        snakeHead = test1.loadHead();
//        apple=test1.loadFood();
        
        
    }
     private void loadImages(){
        ImageIcon body = new ImageIcon("src/dot.png");
        snakeBody = body.getImage();
        ImageIcon head = new ImageIcon("src/head.png");
        snakeHead = head.getImage();
        ImageIcon food = new ImageIcon("src/apple.png");
        apple=food.getImage();
    }
    public void paint(Graphics g) {
        super.paintComponents(g);

        doDrawing(g);
    }
    
    public void doDrawing(Graphics g) {
        
        if (inGame && !pause) {
            

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(snakeHead, x[z], y[z], this);
                } else {
                    g.drawImage(snakeBody, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        
        }else if(inGame && pause){
            gamePause(g);
        }else {

            gameOver(g);
        }        
    }
 private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (B_width - metr.stringWidth(msg)) / 2, B_height / 2);
         g.drawString("Press any key to continue", (B_width - metr.stringWidth(msg)) / 2-50, (B_height / 2)+40);
         
    }
    
 private void gamePause(Graphics g){
     String msg = "Pause";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (B_width - metr.stringWidth(msg)) / 2, B_height / 2);
        g.drawString("Press space to continue....", (B_width - metr.stringWidth(msg)) / 2-70, (B_height / 2)+40);
        
      
 }
   
    
    
    private void initGame(){
        dots = storeDot;
        lifeNumber.setText(Integer.toString(life));
        for(int z=0;z<dots;z++){
            x[z]=300-z*10;
            y[z]=300;
        }
        
       locateApple();
//        System.out.println("delay aaaaaaaaaaaa"+DELAY);
       timer = new Timer(DELAY,this);
       timer.start();

    }
    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            score+=1;
            scoreNumber.setText(Integer.toString(score));
            dots++;
            locateApple();
        }
    }
    
    private void move() {
        
        int count=0;
         count++;
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            if(count<=10 && down){
                y[0] += dotSize;
                down=false;
            }
            else if(count<=10 && up){
                y[0] -= dotSize;
                up=false;
            }
            
            
            x[0] -= dotSize;
            
        }

        if (rightDirection) {
            if(count<=10 && down){
                y[0] += dotSize;
                down=false;
            }
            else if(count<=10 && up){
                y[0] -= dotSize;
                up=false;
            }
            x[0] += dotSize;
            
        }

        if (upDirection) {
            if(count<=10 && right){
                x[0] += dotSize;
                right=false;
            }
            else if(count<=10 && left){
                x[0] -= dotSize;
                left=false;
            }
            y[0] -= dotSize;
          
        }

        if (downDirection) {
            if(count<=10 && right){
                x[0] += dotSize;
                right=false;
            }
            else if(count<=10 && left){
                x[0] -= dotSize;
                left=false;
            }
            y[0] += dotSize;
           
            
        }
        
        
    }
     private void checkCollision() {
         
         storeDot=dots;
//         System.out.println("life"+life);
        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                 life=life-1;
                if(life!=0)
            {
//                System.out.println("Still have life");
                upDirection=false;
                downDirection=false;
                rightDirection=true;
                leftDirection=false;
                
                initGame();
                timer.stop();
            }
            else
            {
                
                inGame = false;
            }
            }
        }
        
        if (y[0] >= B_height) {
            life=life-1;
            if(life!=0)
            {
//                System.out.println("Still have life");
                upDirection=false;
                downDirection=false;
                rightDirection=true;
                leftDirection=false;
                
                
                initGame();
                timer.stop();
            }
            else
            {
                
                inGame = false;
            }
            
        }

        if (y[0] <= 75) {
            life=life-1;
            if(life!=0)
            {
//                System.out.println("Still have life");
                upDirection=false;
                downDirection=false;
                rightDirection=true;
                leftDirection=false;
               
                
                initGame();
                timer.stop();
                
            }
            else
            {
                
                inGame = false;
            }
        }

        if (x[0] >= B_width) {
            life=life-1;
            if(life!=0)
            {
//                System.out.println("Still have life");
                upDirection=false;
                downDirection=false;
                rightDirection=true;
                leftDirection=false;
                
                
                initGame();
                timer.stop();
            }
            else
            {
                
                inGame = false;
            }
        }

        if (x[0] <= 25) {
            life=life-1;
            if(life!=0)
            {
//                System.out.println("Still have life");
                upDirection=false;
                downDirection=false;
                rightDirection=true;
                leftDirection=false;
                
                
                initGame();
                timer.stop();
            }
            else
            {
                
                inGame = false;
            }
        }
        
        if (!inGame) {
            timer.stop();
        }
    }
     private void Pause(){
         upDirection=false;
          leftDirection=false;
            downDirection=false;
            rightDirection=false;
            pause=true;
         
     }
     private void Continue(){
          upDirection=contUp;
          leftDirection=contLeft;
            downDirection=contDown;
            rightDirection=contRight;
            pause=false;
     }
    private void locateApple(){
        Random rand=new Random();
//        int r=(int)(Math.random()*rand_postY);
        int r=rand.nextInt(51)+25;
//        apple_x=((r*dotSize));
//        Max Width=765
//          min Width = 25;
        apple_x = r*dotSize;
//        System.out.println("X"+apple_x);
//        r=(int)(Math.random()*71);
//        if(r==0){
//            rand.next
//        }
          r=(rand.nextInt(635)+75)/10;
//        apple_y=((r*dotSize));
//        Max Height = 710
//          min Height=75;
          apple_y=r*dotSize;
//        System.out.println("Y"+apple_y);
    }
   
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        scoreNumber = new javax.swing.JLabel();
        lifeNumber = new javax.swing.JLabel();
        grassField = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Life : ");

        jLabel2.setText("Score :");

        scoreNumber.setText("0");

        lifeNumber.setText("3");

        grassField.setBackground(new java.awt.Color(255, 255, 255));
        grassField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        grassField.setMaximumSize(new java.awt.Dimension(759, 653));
        grassField.setPreferredSize(new java.awt.Dimension(800, 800));
        grassField.setRequestFocusEnabled(false);

        javax.swing.GroupLayout grassFieldLayout = new javax.swing.GroupLayout(grassField);
        grassField.setLayout(grassFieldLayout);
        grassFieldLayout.setHorizontalGroup(
            grassFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 749, Short.MAX_VALUE)
        );
        grassFieldLayout.setVerticalGroup(
            grassFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scoreNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lifeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(grassField, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(scoreNumber)
                    .addComponent(lifeNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grassField, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SnakeInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SnakeInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SnakeInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SnakeInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SnakeInterface gameField=new SnakeInterface();
                
                gameField.setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel grassField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lifeNumber;
    private javax.swing.JLabel scoreNumber;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (inGame && !pause) {

            checkApple();
            checkCollision();
            move();
             repaint();
           
        }
        else if(inGame && pause)
        {
            Pause();
             repaint();
        }
       
        
//            repaint();
        
         
        
    }
    
    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(key!=KeyEvent.VK_ESCAPE && !inGame)
             {
                 
                 
                 getName.setVisible(true);
                 getName.getScore(score);
                 removeKeyListener(control);
                 
                 dispose();
                 //System.exit(0);
                 
                 
             }
            
               
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection) ) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                left=true;
                
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)  ) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                right=true;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
                up=true;    
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
                down=true;
            }
            
            
             if(key==KeyEvent.VK_SPACE && !pause){
                contUp=upDirection;
                contDown=downDirection;
                contRight = rightDirection;
                contLeft = leftDirection;
                System.out.println("pause" +pause);
                
                Pause();
                
                
            }
              else if(key==KeyEvent.VK_SPACE && pause){
                System.out.println(pause);
                System.out.println("Test");
                Continue();
               
            }
             
             
        }
    }
}


