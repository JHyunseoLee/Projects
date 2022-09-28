import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Jude Lee
// Single player Farkle game using JFrame
// Will try to implement two players

public class Farkle implements ActionListener{
	
	Container diceCon = new Container();
	Container buttonCon = new Container();
	Container labelCon = new Container();
	JFrame frame = new JFrame();
	JFrame rulesFrame = new JFrame();
	JPanel rulesPanel = new JPanel();
	ImageIcon[] images = new ImageIcon[6];
	JButton[] buttons = new JButton[6];
	JButton roll = new JButton("Roll");
	JButton score = new JButton("Score");
	JButton end = new JButton("End turn");
	JButton rules = new JButton("Rules");
	JLabel rulesLabel = new JLabel();
	JLabel current = new JLabel("Current Score: 0");
	JLabel round = new JLabel("Round Number: 1");
	JLabel total = new JLabel("Total Score: 0");
	
	int currentScore = 0;
	int currRound = 1;
	int totalScore = 0;
	
	int[] buttonState = new int[6];
	int[] dieValue = new int[6];
	final int ROLL_DIE = 0;
	final int SCORE_DIE = 1;
	final int LOCKED_DIE = 2;
	
	public Farkle() {
		frame.setSize(600, 600);
		images[0] = new ImageIcon("./images/one.png");
		images[1] = new ImageIcon("./images/two.png");
		images[2] = new ImageIcon("./images/three.png");
		images[3] = new ImageIcon("./images/four.png");
		images[4] = new ImageIcon("./images/five.png");
		images[5] = new ImageIcon("./images/six.png");
		diceCon.setLayout(new GridLayout(2,3));
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setIcon(images[i]);
			buttons[i].setEnabled(false);
			buttons[i].addActionListener(this);
			buttons[i].setBackground(Color.cyan);
			diceCon.add(buttons[i]);
			
		}
		buttonCon.setLayout(new GridLayout(1,3));
		buttonCon.add(roll);
		roll.addActionListener(this);
		buttonCon.add(score);
		score.addActionListener(this);
		score.setEnabled(false);
		buttonCon.add(end);
		end.addActionListener(this);
		end.setEnabled(false);
		buttonCon.add(rules);
		rules.addActionListener(this);
		labelCon.setLayout(new GridLayout(3,1));
		labelCon.add(current);
		labelCon.add(round);
		labelCon.add(total);
		
		
		
		frame.setLayout(new BorderLayout());
		frame.add(diceCon, BorderLayout.CENTER);
		frame.add(buttonCon,BorderLayout.NORTH);
		frame.add(labelCon, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new Farkle();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(rules)) {
			JTextArea rulesText = new JTextArea();
			rulesText.setText("\r\n"
					+ "1 - 100 points\r\n"
					+ "5 - 50 points\r\n"
					+ "Three 1's - 1,000 points\r\n"
					+ "Three 2's - 200 points\r\n"
					+ "Three 3's - 300 points\r\n"
					+ "Three 4's - 400 points\r\n"
					+ "Three 5's - 500 points\r\n"
					+ "Three 6's - 600 points\r\n"
					+ "1-2-3-4-5-6 - 3000 points\r\n"
					+ "You can have multiple triples or pairs (Ex. Two 1s and two 5s. Three 4s and three 6s.");
			rulesText.setBounds(208,28,150,30); 
			rulesFrame.add(rulesPanel);
			rulesFrame.setTitle("Rules");
			rulesFrame.setSize(500,500);
			rulesText.setEditable(false);
			rulesFrame.add(rulesText);
			rulesFrame.setVisible(true);
		}
		if(e.getSource().equals(roll)) {
			rules.setEnabled(false);
			for(int i = 0; i < buttons.length; i++) {
				if(buttonState[i] == ROLL_DIE) {
					int choice = (int)(Math.random() * 6);
					dieValue[i] = choice;
					buttons[i].setIcon(images[choice]);
					buttons[i].setEnabled(true);
					roll.setEnabled(false);
					score.setEnabled(true);
					end.setEnabled(true);
				}
			}
		}
		else if(e.getSource().equals(score)) {
			int[] valueCount = new int[7];
			for(int i = 0; i < buttons.length; i++) {
				
				if(buttonState[i] == SCORE_DIE) {
					
				valueCount[dieValue[i] + 1]++;
				
				}
				
			}
			if((valueCount[2] > 0 && valueCount[2] < 3) || (valueCount[3] > 0 && valueCount[3] < 3) 
					|| (valueCount[4] > 0 && valueCount[4] < 3) || (valueCount[6] > 0 && valueCount[6] < 3)) {
				
				JOptionPane.showMessageDialog(frame, "Invalid Selection");
				
			}
			
			else if((valueCount[1] == 0) && (valueCount[2] == 0) && (valueCount[3] == 0) && (valueCount[4] == 0) && 
					(valueCount[5] == 0) && (valueCount[6] == 0)) {
				
				Object[] options = {"Yes", "No"};
				int dialogChoice = JOptionPane.showOptionDialog(frame, "Forfeit Score?", "Forfeit Score?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				if(dialogChoice == JOptionPane.YES_OPTION) {
					currentScore = 0;
					currRound++;
					current.setText("Current Score: " + currentScore);
					round.setText("Round Number: " + currRound);
					
					for(int i = 0; i < buttons.length; i++) {
						buttons[i].setEnabled(false);	
						buttonState[i] = ROLL_DIE;
						buttons[i].setBackground(Color.cyan);
					}
					roll.setEnabled(true);
					score.setEnabled(false);
					end.setEnabled(false);
				}
				
			}		
			
			else {
				if(valueCount[1] >= 3) {
					currentScore += (valueCount[1] -2) * 1000;
				}
				if(valueCount[2] >= 3) {
					currentScore += (valueCount[2]-2) * 200;
				}
				if(valueCount[3] >= 3) {
					currentScore += (valueCount[3]-2) * 300;
				}
				if(valueCount[4] >= 3) {
					currentScore += (valueCount[4]-2) * 400;
				}
				if(valueCount[5] >= 3) {
					currentScore += (valueCount[5]-2) * 500;
				}
				if(valueCount[6] >= 3) {
					currentScore += (valueCount[6]-2) * 600;
				}
				if(valueCount[1] < 3) {
					currentScore += valueCount[1] * 100;
				}
				if(valueCount[5] < 3) {
					currentScore += valueCount[5] * 50;
				}
				if(valueCount[1] == 1 && valueCount[2] == 1 && valueCount[3] == 1 && valueCount[4] == 1 && valueCount[5] == 1 
						&& valueCount[6] == 1) {
					currentScore += 3000;
				}
				current.setText("Current Score:" + currentScore);
				for(int i = 0; i < buttons.length; i++) {
					if(buttonState[i] == SCORE_DIE) {
						buttonState[i] = LOCKED_DIE;
						buttons[i].setBackground(Color.black);
					}
					buttons[i].setEnabled(false);
				}
				int lockedCount = 0;
				for(int i = 0; i < buttons.length; i++) {
					if(buttonState[i] == LOCKED_DIE) {
						lockedCount++;
					}
				}
				if(lockedCount == 6) {
					for(int i = 0; i < buttons.length; i++) {
						buttonState[i] = ROLL_DIE;
						buttons[i].setBackground(Color.cyan);
					}
				}
				roll.setEnabled(true);
				score.setEnabled(false);
				end.setEnabled(true);
			}
			
		}
		
		else if(e.getSource().equals(end)) {
			totalScore += currentScore;
			currentScore = 0;
			current.setText("Current Score " + currentScore);
			total.setText("Total Score " + totalScore);
			currRound++;
			round.setText("Round Number: " + currRound);
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].setEnabled(false);	
				buttonState[i] = ROLL_DIE;
				buttons[i].setBackground(Color.cyan);
			}
			roll.setEnabled(true);
			score.setEnabled(false);
			end.setEnabled(false);
		}
		else {
			for(int i = 0; i < buttons.length; i++) {
				if(e.getSource().equals(buttons[i])) {
					if(buttonState[i] == ROLL_DIE) {
						buttons[i].setBackground(Color.GRAY);
						buttonState[i] = SCORE_DIE;
					}
					else {
						buttons[i].setBackground(Color.cyan);
						buttonState[i] = ROLL_DIE;
						
					}
				}
			}
		}
	}
}
