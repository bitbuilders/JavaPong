package app;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.text.Document;

public class Test {

	private static Timer timer;
	private static JFrame frame;
	private static Button player1;
	private static Button ball;
	private static Button player2;
	private static Label scoreBoard;
	private static int frameWidth = 800;
	private static int frameHeight = 600;
	private static int frameBarHeight = 72;
	private static int frameBarWidth = 23;
	private static ArrayList<String> pressedKeys = new ArrayList<>();
	private static int playerSpeed = 2;
	private static int ballSpeed = 2;
	private static int ballDirection = 3;
	private static int round = 1;
	private static int player1Score = 0;
	private static int player2Score = 0;

	public static void main(String[] args) {
		createFrame();
		createTimer();
	}

	public static void moveBall() {
		String keys = "";
		
		for (int i = 0; i < pressedKeys.size(); i++) {
			keys += pressedKeys.get(i).replace("[", "").replace("]", "");
		}
		
		if (keys.contains("UP") && keys.length() == 2) {
			if (player1.getY() > 0) {
				player1.setBounds(player1.getX(), player1.getY() - playerSpeed, player1.getWidth(), player1.getHeight());
			}
		}
		if (keys.contains("DOWN") && keys.length() == 4) {
			if (player1.getY() + player1.getHeight() + frameBarHeight < frameHeight) {
				player1.setBounds(player1.getX(), player1.getY() + playerSpeed, player1.getWidth(), player1.getHeight());
			}
		}
		if (keys.contains("Q") && keys.length() == 1) {
			if (player2.getY() > 0) {
				player2.setBounds(player2.getX(), player2.getY() - playerSpeed, player2.getWidth(), player2.getHeight());
			}
		}
		if (keys.contains("A") && keys.length() == 1) {
			if (player2.getY() + player2.getHeight() + frameBarHeight < frameHeight) {
				player2.setBounds(player2.getX(), player2.getY() + playerSpeed, player2.getWidth(), player2.getHeight());
			}
		}
		else if (keys.contains("DOWN") && keys.contains("Q")) {
			if (player1.getY() + player1.getHeight() + frameBarHeight < frameHeight) {
				player1.setBounds(player1.getX(), player1.getY() + playerSpeed, player1.getWidth(), player1.getHeight());
			}
			
			if (player2.getY() > 0) {
				player2.setBounds(player2.getX(), player2.getY() - playerSpeed, player2.getWidth(), player2.getHeight());
			}
		}
		else if (keys.contains("DOWN") && keys.contains("A")) {
			if (player1.getY() + player1.getHeight() + frameBarHeight < frameHeight) {
				player1.setBounds(player1.getX(), player1.getY() + playerSpeed, player1.getWidth(), player1.getHeight());
			}
			
			if (player2.getY() + player2.getHeight() + frameBarHeight < frameHeight) {
				player2.setBounds(player2.getX(), player2.getY() + playerSpeed, player2.getWidth(), player2.getHeight());
			}
		}
		else if (keys.contains("UP") && keys.contains("Q")) {
			if (player1.getY() > 0) {
				player1.setBounds(player1.getX(), player1.getY() - playerSpeed, player1.getWidth(), player1.getHeight());
			}
			
			if (player2.getY() > 0) {
				player2.setBounds(player2.getX(), player2.getY() - playerSpeed, player2.getWidth(), player2.getHeight());
			}
		}
		else if (keys.contains("UP") && keys.contains("A")) {
			if (player1.getY() > 0) {
				player1.setBounds(player1.getX(), player1.getY() - playerSpeed, player1.getWidth(), player1.getHeight());
			}
			
			if (player2.getY() + player2.getHeight() + frameBarHeight < frameHeight) {
				player2.setBounds(player2.getX(), player2.getY() + playerSpeed, player2.getWidth(), player2.getHeight());
			}
		}
		
		checkLose();
		
		if (isTouching()) {
			ballSpeed *= -1;
		}
		
		if (ball.getY() <= 0) {
			ballDirection *= -1;
		}
		if (ball.getY() + ball.getHeight() + frameBarHeight >= frameHeight) {
			ballDirection *= -1;
		}
		
		ball.setBounds(ball.getX() + ballSpeed, ball.getY() + ballDirection, ball.getWidth(), ball.getHeight());
	}

	public static void createTimer() {
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveBall();
			}
		};
		timer = new Timer(1, action);
		timer.setRepeats(true);
		timer.setInitialDelay(0);
		timer.start();

		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e1) {
		// System.out.println("You woke it up!");
		// }
	}
	
	public static void progressRounds() {
		round++;
		ball.setBounds(100, 100, 40, 40);
		
		if (ballSpeed < 0)
			ballSpeed *= -1;
		if (ballDirection < 0)
			ballDirection *= -1;
		
		if (round % 2 == 1)
			ballSpeed++;
		
		ballDirection++;
		scoreBoard.setText(player1Score + " | " + player2Score);
	}
	
	public static boolean isTouching() {
		if (ball.getX() + ball.getWidth() >= player2.getX() && ball.getY() + ball.getHeight() >= player2.getY() &&
				ball.getY() < player2.getY() + player2.getHeight()) {
			return true;
		}
		else if (ball.getX() <= player1.getX() + player1.getWidth() &&
				ball.getY() + ball.getHeight() >= player1.getY() && ball.getY() < player1.getY() + player1.getHeight()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void checkLose() {
		if (ball.getX() + ball.getWidth() + frameBarWidth >= frameWidth) {
			player1Score++;
			progressRounds();
		}
		if (ball.getX() <= 0) {
			player2Score++;
			progressRounds();
		}
		
		if (player1Score == 3) {
			timer.stop();
			scoreBoard.setBounds(150, 0, 600, 100);
			scoreBoard.setSize(600, 100);
			scoreBoard.setText("Player 1 has won the game!");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.dispose();
		}
		else if (player2Score == 3) {
			timer.stop();
			scoreBoard.setBounds(150, 0, 600, 100);
			scoreBoard.setSize(600, 100);
			scoreBoard.setText("Player 2 has won the game!");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.dispose();
		}
	}

	public static void createFrame() {
//		ActionListener action = new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//			}
//		};
		
		KeyListener keyPress = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (!pressedKeys.contains("UP"))
						pressedKeys.add("UP");
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (!pressedKeys.contains("DOWN"))
						pressedKeys.add("DOWN");
				}
				if (e.getKeyCode() == KeyEvent.VK_Q) {
					if (!pressedKeys.contains("Q"))
						pressedKeys.add("Q");
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					if (!pressedKeys.contains("A"))
						pressedKeys.add("A");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						pressedKeys.remove("UP");
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						pressedKeys.remove("DOWN");
					}
					if (e.getKeyCode() == KeyEvent.VK_Q) {
						pressedKeys.remove("Q");
					}
					if (e.getKeyCode() == KeyEvent.VK_A) {
						pressedKeys.remove("A");
					}
				}
			}
		};

		// 1. Create the frame.
		frame = new JFrame("FrameDemo");
		frame.setBackground(Color.BLACK);

		// 2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 3. Create components and put them in the frame.
		player1 = new Button("");
		player1.setEnabled(false);
		//player1.addActionListener(action);
		player1.setBackground(Color.GREEN);

		player2 = new Button("");
		player2.setEnabled(false);
		//player2.addActionListener(action);
		player2.setBackground(Color.GREEN);
		
		ball = new Button("");
		ball.setEnabled(false);
		//ball.addActionListener(action);
		ball.setBackground(Color.BLUE);
		
		scoreBoard = new Label("");
		Font f = new Font("Arial", Font.BOLD, 40);
		scoreBoard.setFont(f);
		scoreBoard.setText(player1Score + " | " + player2Score);

		// frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.getContentPane().add(player1);
		frame.getContentPane().add(player2);
		frame.getContentPane().add(ball);
		frame.getContentPane().add(scoreBoard);

//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		double width = screenSize.getWidth();
//		double height = screenSize.getHeight();
//
//		int widthI = (int) width;
//		int heightI = (int) height;

		frame.setBounds(0, 0, frameWidth, frameHeight);

		// 4. Size the frame.
		// frame.pack();

		// 5. Show it.
		frame.setVisible(true);
		frame.setLayout(null);
		frame.addKeyListener(keyPress);
		player1.setBounds(0, 100, 50, 250);
		player2.setBounds(723, 100, 50, 250);
		ball.setBounds(100, 100, 40, 40);
		scoreBoard.setBounds(frameWidth / 2 - 50, 0, 300, 100);
	}
}
