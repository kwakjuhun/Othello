import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Othello2 extends JFrame {
	int[][][][] data = new int[8][8][21][2];
	Stone[][] stones = new Stone[8][8];
	int play_turn = 1;
	JPanel game_panel = new JPanel() {
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
	public class Stone{
		int s;
		int x, y;
		JLabel label;
		Stone(int state, int x_loc, int y_loc){
			s = state;
			x = x_loc;
			y = y_loc;
			drawLabel();
		}
		void drawLabel(){
			label = new JLabel(){
				public void paintComponent(Graphics g){
					switch(s){ 
						case 1:
							g.setColor(Color.white);
							break;
						case 2:
							g.setColor(Color.black);
							break;
						case 3:
							g.setColor(Color.CYAN);
							click(this);
							break;
						default:
							break;
					}
					g.fillOval(0, 0, 100, 100);
				}
			};
			label.setBounds(x * 100, y * 100, 100, 100);
			label.setVisible(true);
			game_panel.add(label);
		}
		void change(int state){
			s = state;
			game_panel.remove(label);
			drawLabel();
		}
		void delete(){
			game_panel.remove(label);	
		}
		void click(JLabel lab){
			lab.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					// 다른 3번애들은 다 지우기;
					// 데이터 가져와서 뒤집기
					// 이것도 뒤집어야함
					// 예측표 다시 띄우기

					// change();
				}
			});
		}
	}

	public Othello2() {
		setSize(1130, 850);
		setTitle("Othello");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		start();
		setVisible(true);
	};

	int board[][] = new int[8][8];
	public void start(){
		setTitle("Black");
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				board[i][j] = 0;
		board[3][3] = 2;
		stones[3][3] = new Stone(2, 3, 3);
		board[4][4] = 2;
		stones[4][4] = new Stone(2, 4, 4);
		board[3][4] = 1;
		stones[3][4] = new Stone(1, 4, 3);
		board[4][3] = 1;
		stones[4][3] = new Stone(1, 3, 4);

		game_panel.setBounds(0, 0, 1000, 1000);
		game_panel.setLayout(null);
		add(game_panel);

		checking(2);
	}
	public void checking(int turn){	
		data = new int[8][8][21][2];
		int cnt = 0;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if(board[y][x] == 0){
					// 위
					int tmp = 0;
					cnt = 0;
					for(int j = y - 1; j >= 0 ; j--){
						if(board[j][x] == 0) break;
						if(board[j][x] == turn){
							if(tmp == 0) break;
							for(int c = 0; c < tmp; c++){
								data[y][x][cnt][0] = x;
								data[y][x][cnt++][1] = y-c-1;
							}
						}
						tmp++;
					}

					// 아래
					tmp = 0;
					for (int j = y + 1; j < 8; j++) {
						if (board[j][x] == 0) break;
						if (board[j][x] == turn) {
							if(tmp == 0) break;
							for (int c = 0; c < tmp; c++) {
								data[y][x][cnt][0] = x;
								data[y][x][cnt++][1] = y+c+1;
							}
						}
						tmp++;
					}

					// 오른
					tmp = 0;
					for (int j = x + 1; j < 8; j++) {
						if (board[y][j] == 0) break;
						if (board[y][j] == turn) {
							if(tmp == 0) break;
							for (int c = 0; c < tmp; c++) {
								data[y][x][cnt][0] = x+c+1;
								data[y][x][cnt++][1] = y;
							}
						}
						tmp++;
					}
			
					// 왼
					tmp = 0;
					for (int j = x - 1; j >= 0; j--) {
						if (board[y][j] == 0) break;
						if (board[y][j] == turn) {
							if(tmp == 0) break;
							for (int c = 0; c < tmp; c++) {
								data[y][x][cnt][0] = x-c-1;
								data[y][x][cnt++][1] = y;
							}
						}
						tmp++;
					}
			

					// 왼 위
					tmp = 0;
					for (int a = x - 1, b = y - 1; a >= 0 && b >= 0; a--, b--) {
						if (board[b][a] == 0) break;
						if (board[b][a] == turn) {
							if(tmp == 0) break;
							for (int c = 0; c < tmp; c++) {
								data[y][x][cnt][0] = x-c-1;
								data[y][x][cnt++][1] = y-c-1;
							}
						}
						tmp++;
					}
			
					// 오 위
					tmp = 0;
					for (int a = x + 1, b = y - 1; a < 8 && b >= 0; a++, b--) {
						if (board[b][a] == 0) break;
						if (board[b][a] == turn) {
							if(tmp == 0) break;
							for (int c = 0; c < tmp; c++) {
								data[y][x][cnt][0] = x+c+1;
								data[y][x][cnt++][1] = y-c-1;
							}
						}
						tmp++;
					}

					// 왼 아
					tmp = 0;
					for (int a = x - 1, b = y + 1; a >= 0 && b < 8; a--, b++) {
						if (board[b][a] == 0) break;
						if (board[b][a] == turn) {
							if(tmp == 0) break;
							for (int c = 0; c < tmp; c++) {
								data[y][x][cnt][0] = x-c-1;
								data[y][x][cnt++][1] = y+c+1;
							}
						}
						tmp++;
					}
			
					// 오 아
					tmp = 0;
					for (int a = x + 1, b = y + 1; a < 8 && b < 8; a++, b++) {
						if (board[b][a] == 0) break;
						if (board[b][a] == turn) {
							if(tmp == 0) break;
							for (int c = 0; c < tmp; c++) {
								data[y][x][cnt][0] = x+c+1;
								data[y][x][cnt++][1] = y+c+1;
							}
						}
						tmp++;
					}
				}
				if(cnt > 0){
					stones[y][x] = new Stone(3, x, y);
				}
			}			
		}
	}

	public static void main(String args[]) {
		new Othello2();
	}
}
