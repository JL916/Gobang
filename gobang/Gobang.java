package gobang;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Gobang类 项目的入口
 * 控制各个类
 */
public class Gobang extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Board board;			 // 棋盘
	private GamePanel panel;    	 // 游戏面板
	private GobangListener listener; // 监听器

	/**
	 * 构造器 初始化各个类 设置监听器
	 */
	public Gobang () {
		new Tool(this);			  // 初始化静态工具类
		board = new Board();	  // 初始化棋盘
		panel = new GamePanel();  // 初始化面板
		
		// 设置状态
		setTitle("gobang");
		setSize(Tool.GAMEWIDTH, Tool.GAMEHEIGHT);
		setResizable(false);
		setLocation(Tool.GAMELOCATION);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 设置布局
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		add(panel, BorderLayout.EAST);

		// 设置监听器
		listener = new GobangListener(this);
		addMouseListener(listener);
	}	
	
	/**
	 * 返回棋盘
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * 返回面板
	 */
	public GamePanel getPanel() {
		return panel;
	}
	
	/**
	 * 返回监听器
	 */
	public GobangListener getListener() {
		return listener;
	}
	
	/**
	 * 主函数 项目入口
	 */
	public static void main(String[] args) {	
		new Gobang();
	}
}