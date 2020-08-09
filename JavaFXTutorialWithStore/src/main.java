import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Iterator;

public class main{

    static set<String> p1Set = new set<String>();
    static set<String> p2Set = new set<String>();
    static set<String> mSet  = new set<String>();

    static int selR, selC = -1;
    static GraphicsContext g;
    static card[][] cards;
    static int turn = -1;

    static Stage primaryStage;

    /*
    1 - p1 select
    2 - p1 choose
    3 - p2 select
    4 - p4 choose
     */

    public static void display() {

        primaryStage = new Stage();

        mSet.add("A");
        mSet.add("2");
        mSet.add("3");
        mSet.add("4");
        mSet.add("5");
        mSet.add("6");
        mSet.add("7");
        mSet.add("8");
        mSet.add("9");
        mSet.add("10");
        mSet.add("J");
        mSet.add("Q");
        mSet.add("K");

        cards = new card[][]{{new card(false, "A"), new card(false, "A"), new card(false, "A"), new card(false, "A"), new card(false, "2"), new card(false, "2"), new card(false, "2"), new card(false, "2"), new card(false, "3")},
                {new card(false, "3"), new card(false, "3"), new card(false, "3"), new card(false, "4"), new card(false, "4"), new card(false, "4"), new card(false, "4"), new card(false, "5"), new card(false, "5")},
                {new card(false, "5"), new card(false, "5"), new card(false, "6"), new card(false, "6"), new card(false, "6"), new card(false, "6"), new card(false, "7"), new card(false, "7"), new card(false, "7")},
                {new card(false, "7"), new card(false, "8"), new card(false, "8"), new card(false, "8"), new card(false, "8"), new card(false, "9"), new card(false, "9"), new card(false, "9"), new card(false, "9")},
                {new card(false, "10"), new card(false, "10"), new card(false, "10"), new card(false, "10"), new card(false, "J"), new card(false, "J"), new card(false, "J"), new card(false, "J"), new card(false, "Q")},
                {new card(false, "Q"), new card(false, "Q"), new card(false, "Q"), new card(false, "K"), new card(false, "K"), new card(false, "K"), new card(false, "K"), new card(false, " "), new card(false, " ")}};

        shuffleCards();
        turn = 1;

        primaryStage.setTitle("Matching Game");
        Group group = new Group();
        Canvas canvas = new Canvas(860, 500);
        group.getChildren().add(canvas);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        g = canvas.getGraphicsContext2D();
        draw(g);
        primaryStage.show();


        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards[0].length; c++) {
                System.out.print(cards[r][c].getValue()+" ");
            }
            System.out.println("");
        }




        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                int row = ((int) (y) - 20) / 80;
                int col = ((int) (x) - 170) / 60;
                if(!win()&&event.getButton().name().equals("PRIMARY")&&twoFlipped()==false&&inCheck(x,y)&&!(selC==col&&selR==row)&&!cards[row][col].getValue().equals(" ")) {

                    if(turn==1)
                    {
                        selR = row;
                        selC = col;
                        cards[row][col].setFlipped(true);
                        turn = 2;
                    }
                    else if(turn==2){
                        cards[row][col].setFlipped(true);
                        long flipTime = System.nanoTime();

                        new AnimationTimer()
                        {
                            boolean flipped = true;
                            @Override
                            public void handle(long l) {
                                if(flipped&&(l-flipTime)/1000000000>2) {
                                    if (cards[selR][selC].getValue().equals(cards[row][col].getValue())) {
                                        if(!p1Set.contains(cards[selR][selC].getValue())) {
                                            p1Set.add(cards[selR][selC].getValue());
                                            cards[row][col].setValue(" ");
                                            cards[selR][selC].setValue(" ");
                                            cards[row][col].setFlipped(false);
                                            cards[selR][selC].setFlipped(false);
                                            selR = -1;
                                            selC = -1;
                                            turn = 1;
                                        }
                                        else {
                                            cards[row][col].setFlipped(false);
                                            cards[selR][selC].setFlipped(false);
                                            selR = -1;
                                            selC = -1;
                                            turn = 3;
                                        }
                                    } else {
                                        cards[row][col].setFlipped(false);
                                        cards[selR][selC].setFlipped(false);
                                        selR = -1;
                                        selC = -1;
                                        turn = 3;
                                    }
                                    draw(g);
                                    flipped = false;
                                }
                            }

                        }.start();

                    }
                    else if(turn==3)
                    {
                        selR = row;
                        selC = col;
                        cards[row][col].setFlipped(true);
                        turn = 4;
                    }
                    else if(turn==4)
                    {
                        cards[row][col].setFlipped(true);
                        long flipTime = System.nanoTime();

                        new AnimationTimer()
                        {
                            boolean flipped = true;
                            @Override
                            public void handle(long l) {
                                if(flipped&&(l-flipTime)/1000000000>2) {
                                    if (cards[selR][selC].getValue().equals(cards[row][col].getValue())) {
                                        if(!p2Set.contains(cards[selR][selC].getValue())) {
                                            p2Set.add(cards[selR][selC].getValue());
                                            cards[row][col].setValue(" ");
                                            cards[selR][selC].setValue(" ");
                                            cards[row][col].setFlipped(false);
                                            cards[selR][selC].setFlipped(false);
                                            selR = -1;
                                            selC = -1;
                                            turn = 3;
                                        }
                                        else {
                                            cards[row][col].setFlipped(false);
                                            cards[selR][selC].setFlipped(false);
                                            selR = -1;
                                            selC = -1;
                                            turn = 1;
                                        }
                                    } else {
                                        cards[row][col].setFlipped(false);
                                        cards[selR][selC].setFlipped(false);
                                        selR = -1;
                                        selC = -1;
                                        turn = 1;
                                    }
                                    draw(g);
                                    flipped = false;
                                }
                            }

                        }.start();
                    }
                    draw(g);
                }

                if(win()&&event.getButton().name().equals("SECONDARY"))
                {
                    p1Set.clear();
                    p2Set.clear();
                    turn = 1;
                    cards = new card[][]{{new card(false, "A"), new card(false, "A"), new card(false, "A"), new card(false, "A"), new card(false, "2"), new card(false, "2"), new card(false, "2"), new card(false, "2"), new card(false, "3")},
                            {new card(false, "3"), new card(false, "3"), new card(false, "3"), new card(false, "4"), new card(false, "4"), new card(false, "4"), new card(false, "4"), new card(false, "5"), new card(false, "5")},
                            {new card(false, "5"), new card(false, "5"), new card(false, "6"), new card(false, "6"), new card(false, "6"), new card(false, "6"), new card(false, "7"), new card(false, "7"), new card(false, "7")},
                            {new card(false, "7"), new card(false, "8"), new card(false, "8"), new card(false, "8"), new card(false, "8"), new card(false, "9"), new card(false, "9"), new card(false, "9"), new card(false, "9")},
                            {new card(false, "10"), new card(false, "10"), new card(false, "10"), new card(false, "10"), new card(false, "J"), new card(false, "J"), new card(false, "J"), new card(false, "J"), new card(false, "Q")},
                            {new card(false, "Q"), new card(false, "Q"), new card(false, "Q"), new card(false, "K"), new card(false, "K"), new card(false, "K"), new card(false, "K"), new card(false, " "), new card(false, " ")}};

                    shuffleCards();
                    draw(g);
                }
                draw(g);
            }
        });
    }

    public static void draw(GraphicsContext g)
    {
        g.setFill(Color.DARKGREEN);
        g.fillRect(150,0, 560, 500);
        g.setFill(Color.DARKGRAY);
        g.fillRect(0,0,150,500);
        g.fillRect(710, 0, 150,500);

        g.setFill(Color.GRAY);
        g.fillRect(50, 15, 50, 25);
        g.fillRect(760,15,50,25);


        //System.out.println("turn: "+turn);
        if(turn==1||turn==2) {
            g.setFill(Color.GREEN);
            g.fillRect(50, 15, 50, 25);
        }
        else if(turn==3||turn==4) {
            g.setFill(Color.GREEN);
            g.fillRect(760, 15, 50, 25);
        }

        g.setFill(Color.BLACK);
        g.strokeRect(50, 15, 50, 25);
        g.strokeRect(760,15,50,25);
        g.setFont(new Font("Times New Roman", 12));
        g.fillText("Player 1", 55,30);
        g.fillText("Player 2", 765,30);

        g.setFill(Color.GRAY);
        g.fillRect(25, 80, 40,40);
        g.fillRect(80, 80, 40,40);
        g.fillRect(25, 130, 40,40);
        g.fillRect(80, 130, 40,40);
        g.fillRect(25, 180, 40,40);
        g.fillRect(80, 180, 40,40);
        g.fillRect(25, 230, 40,40);
        g.fillRect(80, 230, 40,40);
        g.fillRect(25, 280, 40,40);
        g.fillRect(80, 280, 40,40);
        g.fillRect(25, 330, 40,40);
        g.fillRect(80, 330, 40,40);
        g.fillRect(25, 380, 40,40);

        g.fillRect(735, 80, 40,40);
        g.fillRect(790, 80, 40,40);
        g.fillRect(735, 130, 40,40);
        g.fillRect(790, 130, 40,40);
        g.fillRect(735, 180, 40,40);
        g.fillRect(790, 180, 40,40);
        g.fillRect(735, 230, 40,40);
        g.fillRect(790, 230, 40,40);
        g.fillRect(735, 280, 40,40);
        g.fillRect(790, 280, 40,40);
        g.fillRect(735, 330, 40,40);
        g.fillRect(790, 330, 40,40);
        g.fillRect(735, 380, 40,40);

        g.setFill(Color.GREEN);
        Iterator<String> it = mSet.iterator();

        while(it.hasNext())
        {
            String value = it.next();
            if(p1Set.contains(value))
            {
                if(value.equals("A"))
                {
                    g.fillRect(25, 80, 40,40);
                }
                if(value.equals("2"))
                {
                    g.fillRect(25, 130, 40,40);
                }
                if(value.equals("3"))
                {
                    g.fillRect(25, 180, 40,40);
                }
                if(value.equals("4"))
                {
                    g.fillRect(25, 230, 40,40);
                }
                if(value.equals("5"))
                {
                    g.fillRect(25, 280, 40,40);
                }
                if(value.equals("6"))
                {
                    g.fillRect(25, 330, 40,40);
                }
                if(value.equals("7"))
                {
                    g.fillRect(25, 380, 40,40);
                }
                if(value.equals("8"))
                {
                    g.fillRect(80, 80, 40,40);
                }
                if(value.equals("9"))
                {
                    g.fillRect(80, 130, 40,40);
                }
                if(value.equals("10"))
                {
                    g.fillRect(80, 180, 40,40);
                }
                if(value.equals("J"))
                {
                    g.fillRect(80, 230, 40,40);
                }
                if(value.equals("Q"))
                {
                    g.fillRect(80, 280, 40,40);
                }
                if(value.equals("K"))
                {
                    g.fillRect(80, 330, 40,40);
                }
            }
        }


        g.setFill(Color.GREEN);
        Iterator<String> it2 = mSet.iterator();

        while(it2.hasNext())
        {
            String value = it2.next();
            if(p2Set.contains(value))
            {
                if(value.equals("A"))
                {
                    g.fillRect(735, 80, 40,40);
                }
                if(value.equals("2"))
                {
                    g.fillRect(735, 130, 40,40);
                }
                if(value.equals("3"))
                {
                    g.fillRect(735, 180, 40,40);
                }
                if(value.equals("4"))
                {
                    g.fillRect(735, 230, 40,40);
                }
                if(value.equals("5"))
                {
                    g.fillRect(735, 280, 40,40);
                }
                if(value.equals("6"))
                {
                    g.fillRect(735, 330, 40,40);
                }
                if(value.equals("7"))
                {
                    g.fillRect(735, 380, 40,40);
                }
                if(value.equals("8"))
                {
                    g.fillRect(790, 80, 40,40);
                }
                if(value.equals("9"))
                {
                    g.fillRect(790, 130, 40,40);
                }
                if(value.equals("10"))
                {
                    g.fillRect(790, 180, 40,40);
                }
                if(value.equals("J"))
                {
                    g.fillRect(790, 230, 40,40);
                }
                if(value.equals("Q"))
                {
                    g.fillRect(790, 280, 40,40);
                }
                if(value.equals("K"))
                {
                    g.fillRect(790, 330, 40,40);
                }
            }
        }

        g.setFont(new Font("Serif", 24));
        g.setFill(Color.BLACK);
        g.fillText("A", 37,108);
        g.fillText("2", 39,158);
        g.fillText("3", 39,208);
        g.fillText("4", 39,258);
        g.fillText("5", 39,308);
        g.fillText("6", 39,358);
        g.fillText("7", 39,408);

        g.fillText("8", 93, 108);
        g.fillText("9", 93, 158);
        g.fillText("10", 87, 208);
        g.fillText("J", 95, 258);
        g.fillText("Q", 91, 308);
        g.fillText("K", 91, 358);

        g.fillText("A", 747,108);
        g.fillText("2", 749,158);
        g.fillText("3", 749,208);
        g.fillText("4", 749,258);
        g.fillText("5", 749,308);
        g.fillText("6", 749,358);
        g.fillText("7", 749,408);

        g.fillText("8", 804, 108);
        g.fillText("9", 804, 158);
        g.fillText("10", 797, 208);
        g.fillText("J", 806, 258);
        g.fillText("Q", 800, 308);
        g.fillText("K", 802, 358);


        g.setFill(Color.TEAL);
        int xDraw = 170;
        int yDraw = 20;
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards[0].length; c++) {
                if(cards[r][c].getValue().equals(" "))
                {

                }
                else if(cards[r][c].isFlipped())
                {
                    g.fillRect(xDraw, yDraw, 40,60);
                    g.setFill(Color.BLACK);
                    if(cards[r][c].getValue().equals("10"))
                        g.fillText(cards[r][c].getValue(), xDraw+8, yDraw+37);
                    else if(cards[r][c].getValue().equals("J"))
                        g.fillText(cards[r][c].getValue(), xDraw+15, yDraw+37);
                    else if(cards[r][c].getValue().equals("Q"))
                        g.fillText(cards[r][c].getValue(), xDraw+11, yDraw+37);
                    else
                        g.fillText(cards[r][c].getValue(), xDraw+13, yDraw+37);
                    g.setFill(Color.TEAL);
                    g.setFill(Color.BLACK);
                    g.fillRect(xDraw, yDraw+5, 40, 5);
                    g.fillRect(xDraw, yDraw+50, 40, 5);
                    g.setFill(Color.TEAL);

                }
                else
                {
                    g.fillRect(xDraw, yDraw, 40,60);
                    g.setFill(Color.BLACK);
                    g.fillRect(xDraw+10, yDraw+25, 20, 5);
                    g.fillRect(xDraw+18, yDraw+18, 5, 20);
                    g.strokeOval(xDraw+10, yDraw+18, 20, 20);
                    g.fillRect(xDraw, yDraw+5, 40, 5);
                    g.fillRect(xDraw, yDraw+50, 40, 5);
                    g.setFill(Color.TEAL);
                }

                xDraw+=60;
            }
            xDraw = 170;
            yDraw += 80;
        }

        if(win())
        {
            if(p1Set.size()==13) {
                g.setFont(new Font("Serif", 24));
                g.setFill(Color.RED);
                g.fillText("Congratulations Player 1", 300, 25);
                System.out.println("win");
            }
            else
            {
                g.setFont(new Font("Serif", 24));
                g.setFill(Color.RED);
                g.fillText("Congratulations Player 2", 300, 25);
                System.out.println("win");
            }
        }

    }

    public static void shuffleCards()
    {
        int repeat = 0;
        int row,col, row2, col2;
        card temp;
        while(repeat<52)
        {
            row = (int)(Math.random()*6+0);
            col = (int)(Math.random()*9+0);
            row2 = (int)(Math.random()*6+0);
            col2 = (int)(Math.random()*9+0);

            while (cards[row][col].getValue().equals(" ")||cards[row2][col2].getValue().equals(" "))
            {
                row = (int)(Math.random()*6+0);
                col = (int)(Math.random()*9+0);
                row2 = (int)(Math.random()*6+0);
                col2 = (int)(Math.random()*9+0);
            }

            temp = cards[row][col];
            cards[row][col] = cards[row2][col2];
            cards[row2][col2] = temp;
            repeat++;
        }
    }

    public static boolean inCheck(double x, double y)
    {
        if(x<170)
            return false;
        if(x>690)
            return false;
        if(y<20)
            return false;
        if(y>480)
            return false;
        if(y>80&&y<100)
            return false;
        if(y>160&&y<180)
            return false;
        if(y>240&&y<260)
            return false;
        if(y>320&&y<340)
            return false;
        if(y>400&&y<420)
            return false;
        if(x>210&&x<230)
            return false;
        if(x>270&&x<290)
            return false;
        if(x>330&&x<350)
            return false;
        if(x>390&&x<410)
            return false;
        if(x>450&&x<470)
            return false;
        if(x>510&&x<530)
            return false;
        if(x>570&&x<590)
            return false;
        if(x>630&&x<650)
            return false;
        if(x>570&&y>400)
            return false;
        return true;
    }

    public static boolean twoFlipped()
    {
        int count = 0;
        for (int r = 0; r < cards.length; r++) {
            for (int c = 0; c < cards[0].length; c++) {
                if(cards[r][c].isFlipped()==true)
                    count++;
            }
        }

        return count>=2;
    }

    public static boolean win()
    {
        if(p1Set.size()==13) {
            return true;
        }
        else if(p2Set.size()==13) {
            return true;
        }
        return false;
    }


}