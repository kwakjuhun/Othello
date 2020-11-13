// package practice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Othello extends JFrame {
	JPanel panel = new JPanel() {
		public void paintComponent(Graphics g) {
			int count = 0;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if(count % 2 == 0) g.setColor(Color.LIGHT_GRAY);
					else g.setColor(Color.GRAY);
					g.fillRect(i * 100, j * 100, 100, 100);
					count++;
					}
				count++;
			}
		}
	};
	JLabel label_white[][] = new JLabel[8][8];
	JLabel label_black[][] = new JLabel[8][8];
	JButton button[][] = new JButton[8][8];

	int bord[][] = new int[8][8];
	int count = 0;

	public Othello() {
		setSize(830, 850);
		setTitle("Black");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				bord[i][j] = 0;
		bord[3][3] = 2;
		bord[4][4] = 2;
		bord[3][4] = 1;
		bord[4][3] = 1;
		int x = 0;
		int y = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				label_black[i][j] = new JLabel() {
					public void paintComponent(Graphics g) {
						g.setColor(Color.BLACK);
						g.fillOval(0, 0, 100, 100);
					}
				};
				label_black[i][j].setBounds(j * 100, i * 100, 100, 100);
				label_black[i][j].setVisible(false);
				panel.add(label_black[i][j]);
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				label_white[i][j] = new JLabel() {
					public void paintComponent(Graphics g) {
						g.setColor(Color.white);
						g.fillOval(0, 0, 100, 100);
					}
				};
				label_white[i][j].setBounds(j * 100, i * 100, 100, 100);
				label_white[i][j].setVisible(false);
				panel.add(label_white[i][j]);
			}
		}
		label_white[3][3].setVisible(true);
		label_white[4][4].setVisible(true);
		label_black[3][4].setVisible(true);
		label_black[4][3].setVisible(true);

		panel.setBounds(0, 0, 1000, 1000);
		panel.setLayout(null);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				int x = e.getX() % 100;
				int y = e.getY() % 100;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX() / 100;
				int y = e.getY() / 100;
				try {
				if (bord[y][x] == 0) {
					boolean bool = gameset(count++ % 2 + 1, x, y);
					if (bool) {
						if(count%2+1 == 2)
							setTitle("white");
						else setTitle("Black");
						for (int i = 0; i < 8; i++)
							for (int j = 0; j < 8; j++) {
								label_black[i][j].setVisible(false);
								label_white[i][j].setVisible(false);
								switch (bord[i][j]) {
								case 2:
									label_white[i][j].setVisible(true);
									break;
								case 1:
									label_black[i][j].setVisible(true);
									break;
								case 0:
								}
							}
					} else
						count--;
				}
				}catch(Exception a) {}
				repaint();
				}
		});
		add(panel);
		setVisible(true);

	}

	public static void main(String args[]) {
		new Othello();
	}

	public boolean gameset(int color, int x, int y) {

		boolean ck = false;
		bord[y][x] = color;
		boolean bool = true;
		int count = 0;
		for (int j = y - 1; j >= 0 && bool; j--) {
			if (bord[j][x] == 0)
				break;
			else if (bord[j][x] == bord[y][x]) {
				for (int c = 0; c < count; c++) {
					bord[++j][x] = bord[y][x];
					ck = true;
				}
				bool = false;

			}
			count++;
		}
		bool = true;
		count = 0;
		for (int j = y + 1; j < 8 && bool; j++) {
			if (bord[j][x] == 0)
				break;
			else if (bord[j][x] == bord[y][x]) {
				for (int c = 0; c < count; c++) {
					bord[--j][x] = bord[y][x];
					ck = true;
				}
				bool = false;
			}
			count++;
		}

		bool = true;
		count = 0;
		for (int j = x + 1; j < 8 && bool; j++) {
			if (bord[y][j] == 0)
				break;
			else if (bord[y][j] == bord[y][x]) {
				for (int c = 0; c < count; c++) {
					bord[y][--j] = bord[y][x];
					ck = true;
				}
				bool = false;
			}
			count++;
		}

		bool = true;
		count = 0;
		for (int j = x - 1; j >= 0 && bool; j--) {
			if (bord[y][j] == 0)
				break;
			else if (bord[y][j] == bord[y][x]) {
				for (int c = 0; c < count; c++) {
					bord[y][++j] = bord[y][x];
					ck = true;
				}
				bool = false;
			}
			count++;
		}

		bool = true;
		count = 0;
		for (int a = x - 1, b = y - 1; a >= 0 && b >= 0 && bool; a--, b--) {
			if (bord[b][a] == 0)
				break;
			else if (bord[b][a] == bord[y][x]) {
				for (int j = 0; j < count; j++) {
					bord[++b][++a] = bord[y][x];
					ck = true;
				}
				bool = false;
			}
			count++;
		}

		bool = true;
		count = 0;
		for (int a = x + 1, b = y - 1; a < 8 && b >= 0 && bool; a++, b--) {
			if (bord[b][a] == 0)
				break;
			else if (bord[b][a] == bord[y][x]) {
				for (int j = 0; j < count; j++) {
					bord[++b][--a] = bord[y][x];
					ck = true;
				}
				bool = false;
			}
			count++;
		}
		bool = true;
		count = 0;
		for (int a = x - 1, b = y + 1; a >= 0 && b < 8 && bool; a--, b++) {
			if (bord[b][a] == 0)
				break;
			else if (bord[b][a] == bord[y][x]) {
				for (int j = 0; j < count; j++) {
					bord[--b][++a] = bord[y][x];
					ck = true;
				}
				bool = false;
			}
			count++;
		}

		bool = true;
		count = 0;
		for (int a = x + 1, b = y + 1; a < 8 && b < 8 && bool; a++, b++) {
			if (bord[b][a] == 0)
				break;
			else if (bord[b][a] == bord[y][x]) {
				for (int j = 0; j < count; j++) {
					bord[--b][--a] = bord[y][x];
					ck = true;
				}
				bool = false;
			}
			count++;
		}
		if (ck)
			return true;
		else {
			bord[y][x] = 0;
			return false;
		}
	}
}
