import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Wordle extends JFrame implements KeyListener {
    private final WordleGame game;
    private JTextField guessField;
    private JLabel[][] gridLabels;
    private final JLabel statusLabel = new JLabel("");
    private final Font myFont = new Font("Arial", Font.BOLD, 30);

    public Wordle() {
        game = new WordleGame();
        setUpGui();
    }

    public void setUpGui(){
        setTitle("Wordle");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,400);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel gridPanel = new JPanel(new GridLayout(6, 5, 5, 5));
        gridLabels = new JLabel[6][5];

        for(int row = 0; row<6; row++) {
            for (int column = 0; column < 5; column++) {
                JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(40, 40));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                label.setFont(myFont);

                gridLabels[row][column] = label;
                gridPanel.add(label);
            }
        }

        mainPanel.add(gridPanel);

        JPanel inputPanel = new JPanel();
        guessField = new JTextField(10);
        guessField.addKeyListener(this);
        JButton submitButton = new JButton("Submit");
        inputPanel.add(guessField);
        inputPanel.add(submitButton);
        mainPanel.add(inputPanel);
        mainPanel.add(statusLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeGuess();
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void updateGrid(String guess, String feedback){
        int row = game.currentGuess-1;
        for(int i = 0; i< guess.length(); i++) {
            gridLabels[row][i].setText(String.valueOf(guess.charAt(i)).toUpperCase());
            switch (feedback.charAt(i)){
                case 'G':
                    gridLabels[row][i].setBackground(Color.GREEN);
                    break;
                case'Y':
                    gridLabels[row][i].setBackground(Color.YELLOW);
                    break;
                default:
                    gridLabels[row][i].setBackground(Color.GRAY);
                    break;
            }
        }
    }

    private void makeGuess(){
        String userGuess = guessField.getText().toLowerCase();
        if(userGuess.length() != 5){
            statusLabel.setText("input a 5 letter word");
            return;
        }

        String feedback = game.makeGuess(userGuess);
        updateGrid(userGuess, feedback);
        guessField.setText("");


        if(feedback.equals("GGGGG")){
            statusLabel.setText("You win!");
            game.gameWon = true;
        } else if(game.isGameOver(game.gameWon, game.numberGuesses)){
            statusLabel.setText("Better luck next time! The word was: " + game.wordToGuess);
        }
    }

    public static void main(String[] args) {
        new Wordle();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            makeGuess();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}