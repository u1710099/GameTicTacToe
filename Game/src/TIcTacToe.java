
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;



public class TIcTacToe implements ActionListener {
    private JFrame jFrame;
    private JPanel jPanelHome;
    private JPanel jPanelGameBoard;
    private JButton onePlayer;
    private JButton twoPlayer;
    private JPanel optionsPanel;

    private JButton homeBtn;
    private JButton resetBtn;
    private JButton switchFirstMove;

    private JButton[] btn = new JButton[9];
    private boolean[] marked = new boolean[9];
    private int move = 0;

    private Random rand = new Random();
    private boolean isOnePlayerGame;
    private boolean isPlayer1Move;
    private boolean isFirstMovePlayer1 = true;

    private boolean isWon = false;
    private int btnIndex;


   public void TicTacToeStart() {

        jFrame = new JFrame("Tic Tac Toe");

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jPanelHome = new JPanel(layout);

        onePlayer = new JButton("One Player");
        twoPlayer = new JButton("Two Player");

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelHome.add(onePlayer, gridBagConstraints);

        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelHome.add(twoPlayer, gridBagConstraints);


        jPanelGameBoard = new JPanel(new GridLayout(3, 3));


        for (int i = 0; i < 9; i++) {
            btn[i] = new JButton("");
            jPanelGameBoard.add(btn[i]);
            btn[i].addActionListener(this);
            marked[i] = false;
        }

        optionsPanel = new JPanel();
        homeBtn = new JButton("Home");
        resetBtn = new JButton("Reset");
        switchFirstMove = new JButton("Switch Sides");

        optionsPanel.add(homeBtn);
        optionsPanel.add(resetBtn);
        optionsPanel.add(switchFirstMove);



        jPanelHome.setBackground(Color.gray);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        onePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);

        homeBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        switchFirstMove.addActionListener(this);

        jFrame.add(jPanelHome);
        jFrame.setSize(300, 300);

        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }


    private boolean ifPlayerWon(String mark) {
        if (btn[0].getText().equals(mark) && btn[1].getText().equals(mark) && btn[2].getText().equals(mark)) {
            return true;
        } else if (btn[3].getText().equals(mark) && btn[4].getText().equals(mark) && btn[5].getText().equals(mark)) {
            return true;
        } else if (btn[6].getText().equals(mark) && btn[7].getText().equals(mark) && btn[8].getText().equals(mark)) {
            return true;
        } else if (btn[0].getText().equals(mark) && btn[3].getText().equals(mark) && btn[6].getText().equals(mark)) {
            return true;
        } else if (btn[1].getText().equals(mark) && btn[4].getText().equals(mark) && btn[7].getText().equals(mark)) {
            return true;
        } else if (btn[2].getText().equals(mark) && btn[5].getText().equals(mark) && btn[8].getText().equals(mark)) {
            return true;
        } else if (btn[0].getText().equals(mark) && btn[4].getText().equals(mark) && btn[8].getText().equals(mark)) {
            return true;
        } else if (btn[2].getText().equals(mark) && btn[4].getText().equals(mark) && btn[6].getText().equals(mark)) {
            return true;
        } else {
            return false;
        }
    }




    private void comMove(){
        if (!isPlayer1Move && isOnePlayerGame) {
            do {
                btnIndex = rand.nextInt(9);
            } while (marked[btnIndex]);

            markMoveOnBoard(btnIndex, "X");

            //check if the computer won
            isWon = ifPlayerWon("X");

            if (isWon) {


                JOptionPane.showMessageDialog(jFrame, "Sorry, Computer won!");
                resetBoard();
            } else if (move == 9) {


                JOptionPane.showMessageDialog(jFrame, " Draw !");
                resetBoard();
            } else {
                isPlayer1Move = true;
            }
        }

    }

    private void resetBoard() {
        move = 0;
        isPlayer1Move = isFirstMovePlayer1;
        for (int i = 0; i < 9; i++) {
            btn[i].setEnabled(true);
            btn[i].setText("");
            marked[i] = false;
        }

        if (isOnePlayerGame) {
            switchFirstMove.setEnabled(true);
            comMove();

        }
        else {
            switchFirstMove.setEnabled(false);
        }
    }

    private void markMoveOnBoard(int btnIndex, String mark) {
        btn[btnIndex].setText(mark);
        btn[btnIndex].setEnabled(false);
        marked[btnIndex] = true;
        move++;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == onePlayer || e.getSource() == twoPlayer) {

            jFrame.remove(jPanelHome);
            jFrame.add(jPanelGameBoard, BorderLayout.CENTER);
            jFrame.add(optionsPanel, BorderLayout.SOUTH);
            jFrame.invalidate();
            jFrame.validate();
            jFrame.repaint();
            isFirstMovePlayer1 = true;

            if (e.getSource() == onePlayer) {
                isOnePlayerGame = true;
            } else if (e.getSource() == twoPlayer) {
                isOnePlayerGame = false;
            }
            resetBoard();


        } else if (e.getSource() == homeBtn) {
            jFrame.remove(jPanelGameBoard);
            jFrame.remove(optionsPanel);
            jFrame.add(jPanelHome, BorderLayout.CENTER);
            jFrame.invalidate();
            jFrame.validate();
            jFrame.repaint();

        } else if (e.getSource() == resetBtn) {
            resetBoard();

        } else if (e.getSource() == switchFirstMove) {

            isFirstMovePlayer1 = !isFirstMovePlayer1;
            resetBoard();

        } else {
            if (isPlayer1Move) {
                int i = 0;
                while (!(e.getSource() == btn[i] && !marked[i] && i < 9)) {
                    i++;
                }
                btnIndex = i;
                markMoveOnBoard(btnIndex, "O"); //To mark the move on board


                isWon = ifPlayerWon("O");

                if (isWon) {
                    if (isOnePlayerGame) {

                        JOptionPane.showMessageDialog(jFrame, "You won!");
                    } else {
                        JOptionPane.showMessageDialog(jFrame, "Player 1 won!");
                    }
                    resetBoard();
                } else if (move == 9) {

                    JOptionPane.showMessageDialog(jFrame, "Draw!");
                    resetBoard();
                } else {
                    isPlayer1Move = false;
                }

            } else if (!isOnePlayerGame) {

                int i = 0;
                while (!(e.getSource() == btn[i] && !marked[i] && i < 9)) {


                    i++;
                }
                btnIndex = i;
                markMoveOnBoard(btnIndex, "X");

                //check if the player won
                isWon = ifPlayerWon("X");

                if (isWon) {
                    JOptionPane.showMessageDialog(jFrame, "Player 2 won!");
                    resetBoard();
                } else if (move == 9) {

                    JOptionPane.showMessageDialog(jFrame, "Draw!");
                    resetBoard();
                } else {
                    isPlayer1Move = true;
                }

            }
            comMove();
        }

    }
}



