package gobang;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * WinFrame类 设置frame
 * 获胜或认输时弹出
 */
public class WinFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JButton again;          // 再来一盘
	JButton exit;           // 退出游戏
	private JLabel winner;  // 获胜者
	
	private static String whitewin = "白棋获胜！";
	private static String blackwin = "黑棋获胜！";
	private static String draw = "平局！";
	
	/**
	 * 构造器 初始化
	 */
	public WinFrame () {
		// 设置属性
		setTitle("win");
		setSize(Tool.WINWIDTH, Tool.WINHEIGHT);
		setLocation(Tool.WINLOCATION);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 设置背景
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {   
		        g.drawImage(Tool.winframe, 0, 0, getSize().width, getSize().height, this);  
			}	
	    } ;
		panel.setOpaque(false);
	    
		// 胜利者
		winner = new JLabel();
		winner.setFont(Tool.getFont(Tool.fontkai, Font.BOLD, 36));
		
		// 再来一盘
		again = new JButton("再来一盘");	
		Tool.initComponent(again, Tool.getFont(Tool.fontkai, Font.PLAIN, 20));
		again.setIcon(new ImageIcon(Tool.blackChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT)));
		
		// 退出游戏
		exit = new JButton("退出游戏");
		Tool.initComponent(exit, Tool.getFont(Tool.fontkai, Font.PLAIN, 20));
		exit.setIcon(new ImageIcon(Tool.whiteChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT)));
		
		// 设置布局
		
		add(panel);
		
		panel.setLayout(new GridBagLayout());
		panel.add(winner, new GBC(0, 0, 2, 1).setInsets(15));
		panel.add(again, new GBC(0, 1).setInsets(10));
		panel.add(exit, new GBC(1, 1).setInsets(10));
	}
	
	/**
	 * 设置胜利者
	 * @param step 步数
	 */
	public void setWinner (int step) {
		// 根据步数奇偶性判断胜利者
		if (step % 2 == 1)
			winner.setText(whitewin);
		else
			winner.setText(blackwin);
	}
	
	/**
	 * 平局时
	 */
	public void setDraw() {
		winner.setText(draw);
	}
}
