import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Othello2 extends JFrame {
	int[][][][] data = new int[8][8][21][2];
	Stone[][] stones = new Stone[8][8];
	int[] score = {2,2};
	ArrayList<Stone> ex = new ArrayList<>(); 
	int play_turn = 2;
	JLabel turn_label = new JLabel();
	JLabel score_label = new JLabel();
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
	JPanel situation_panel = new JPanel();

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
					board[y][x] = play_turn;
					stones[y][x] = new Stone(play_turn, x, y);
					for(int i = 0; i < data[y][x].length; i++){
						if(data[y][x][i][1] > -1 && data[y][x][i][0] > -1){
							stones[data[y][x][i][1]][data[y][x][i][0]].change(play_turn);
							board[data[y][x][i][1]][data[y][x][i][0]] = play_turn;
						}
						else break;
					}

					if(play_turn == 1){
						play_turn = 2;
						turn_label.setText("BLACK");
					}
					else{
						play_turn = 1;
						turn_label.setText("WHITE");
					}
					int[] score = map();
					score_label.setText("<html>BLACK : "+score[0]+"<br>"+"WHITE : "+score[1]+"</html>");


					for(int i = 0; i < ex.size(); i++){
						ex.get(i).delete();
					}
					ex.clear();
					checking(play_turn);
					repaint();
					map();
				}
			});
		}
	}
	public int[] map(){
		int b = 0;
		int w = 0;
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if(board[i][j] == 2)
					b++;
				else if(board[i][j] == 1)
					w++;
			}
		}
		return new int[]{b,w};

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

		game_panel.setBounds(0, 0, 800, 1000);
		game_panel.setLayout(null);
		turn_label.setText("BLACK");
		turn_label.setFont(new Font("Serif", Font.BOLD, 50));
		turn_label.setHorizontalAlignment(SwingConstants.CENTER);
		turn_label.setBounds(0,0,300,300);
		score_label.setText("<html>BLACK : "+2+"<br>"+"WHITE : "+2+"</html>");
		score_label.setFont(new Font("Serif", Font.BOLD, 30));
		score_label.setBounds(0,300,300,300);
		score_label.setHorizontalAlignment(SwingConstants.CENTER);
		situation_panel.add(turn_label);
		situation_panel.add(score_label);
		situation_panel.setBackground(Color.white);
		situation_panel.setBounds(800,0,300,1000);
		situation_panel.setLayout(null);
		add(game_panel);
		add(situation_panel);
		checking(2);
	}
	public void checking(int turn){	
		data = new int[8][8][21][2];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				int cnt = 0;
				if(board[y][x] == 0){
					// 위
					int tmp = 0;
					for(int j = y - 1; j >= 0 ; j--){
						if(board[j][x] == 0) break;
						if(board[j][x] == turn){
							if(tmp == 0) break;
							for(int c = 0; c < tmp; c++){
								data[y][x][cnt][0] = x;
								data[y][x][cnt++][1] = y-c-1;
							}
							break;
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
							break;
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
							break;
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
							break;
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
							break;
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
							break;
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
							break;
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
							break;
						}
						tmp++;
					}
					data[y][x][cnt][0] = -1;
					data[y][x][cnt][1] = -1;
				}
				if(cnt > 0){
					ex.add(new Stone(3, x, y));
				}
			}			
		}
	}

	public static void main(String args[]) {
		new Othello2();
	}
}
