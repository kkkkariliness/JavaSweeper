import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class Main extends JFrame {

    private final Game game;

    private final int IMAGE_SIZE = 50;

    private JLabel label;

    public static void main(String[] args) {
        new Main().setVisible(true); // Вызывает JavaSweeper
    }

    public Main() {
        int COLS = 9;
        int ROWS = 9;
        int BOMBS = 10;
        game = new Game(COLS, ROWS, BOMBS);
        game.start();

        setImages();
        initLabel(); //состояние игры
        initPanel();
        initFrame(); // Подготавливает заготовки InitFrame
    }

    private void initLabel() {
        label = new JLabel("WelCome!");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage(
                            (Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE,
                            coord.y * IMAGE_SIZE,
                            this
                    );
                }
            }
        };

        //Мышечный адаптер, чтобы кликать
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(coord);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(coord);
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                label.setText(getMassage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private String getMassage() {
        switch (game.getState()) {
            case played:
                return "Умеешь думать? Подумай: осталось бомб: " + game.getCountOfBombsLeft();
            case bombed:
                return "Ло-о-о-ох";
            case winner:
                return "По-бе-да!";
            default:
                return " ";
        }
    }


    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);

        //setIconImage(getImage("name"));
    }

    private void setImages() {
        for (Box box : Box.values()) box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage(String name) {
        String filename = "images/" + name + ".png";
        URL url = getClass().getResource(filename);
        if (url == null) {
            throw new IllegalArgumentException("Не найден ресурс: " + filename);
        }
        ImageIcon icon = new ImageIcon(url);

        return icon.getImage();
    }
}