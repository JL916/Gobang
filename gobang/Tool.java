package gobang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

/**
 * Tool类 静态工具类
 * 只包含静态域和静态方法
 */
public class Tool {
	
	static Gobang game;  	// Gobang实例
	
	static final int ROWNUM = 15;			// 行数
	static final int COLNUM = 15;   		// 列数
	static final int BOARDSIZE = 750;  		// 棋盘大小
	static final int MARGIN = 25; 			// 棋盘边距
	static final int CELL = 50;				// 每个格子大小
	static final int CHESSSIZE = 45;	    // 棋子图片大小
	static final int BOARDPOINTSIZE = 10;   // 棋盘点大小(4个角+中央点)
	static final int ICONSIZE = 15;			// 图标大小(按钮旁边的黑白棋子)
	static final int LARGEBUTTONWIDTH = 155;// 大按钮的宽(开始游戏...)
	static final int LARGEBUTTONHEIGHT = 36;// 大按钮的高
	static final int SMALLBUTTONWIDTH = 75; // 小按钮的宽(悔棋...)
	static final int SMALLBUTTONHEIGHT = 24;// 小按钮的高
	static final int GAMEWIDTH = 950;		// 主窗口的宽
	static final int GAMEHEIGHT = 800;      // 主窗口的高
	static final Dimension PANELDIMENSION = new Dimension(180, 0); 	// 游戏面板显示位置
	static final Point GAMELOCATION = new Point(500, 100);          // 主窗口显示位置
	static final Point WINLOCATION = new Point(710, 420);			// 胜利窗口显示位置
	static final Point SETTINGLOCATION = new Point(710, 420);		// 设置窗口显示位置
	static final int SETTINWIDTH = 400;		// 设置窗口宽
	static final int SETTINGHEIGHT = 200;   // 设置窗口高
	static final int WINWIDTH = 300;		// 胜利窗口宽
	static final int WINHEIGHT = 180;       // 胜利窗口高
	static final int TITLEWIDTH = 100;      // 标题图片宽
	static final int TTITLEHEIGHT = 300;	// 标题图片高
	static final int RECTSIZE = 10;			// 方形标记尺寸
	static final int NUMOFFSETX = -5;		// 数字标记x向偏移量
	static final int NUMOFFSETY = 5;		// 数字标记y向偏移量
	static final int FONTSIZE = 20;         // 数字标记字号
	
	static final int PVE = 0;		// PVE定义为0
	static final int PVP = 1;       // PVP定义为1
	static int model;				// 模式变量
	static final int FIRSTP = 0;	// 玩家先手定义为0
	static final int FIRSTE = 1;    // 电脑先手定义为1
	static int first;               // 先手变量
	static final int SHOWLAST = 0;  // 只显示上一步定义为0
	static final int SHOWALL = 1;   // 显示所有定义为1
	static int show;				// 显示变量
	static boolean reviewflag;		// 复盘标志
	
	static String fontwei = "res\\STXINWEI.TTF";  // 华文新魏字体路径
	static String fontkai = "res\\STXINGKA.TTF";  // 华文行楷字体路径
	
	static Image blackChess = new ImageIcon("res\\black.png").getImage();  			// 黑棋图片
	static Image whiteChess = new ImageIcon("res\\white.png").getImage();  			// 白棋图片
	static Image select = new ImageIcon("res\\select.png").getImage();	   			// 按钮被选中图片
	static Image board = new ImageIcon("res\\board.jpg").getImage();	  			// 棋盘背景
	static Image settingframe = new ImageIcon("res\\settingframe.png").getImage();  // 设置背景
	static Image winframe = new ImageIcon("res\\winframe.png").getImage();			// 获胜背景
	static Image background = new ImageIcon("res\\background.png").getImage();		// 面板背景
	static Image gobang = new ImageIcon("res\\gobang.png").getImage();				// 五子棋标题图片
	
	/**
	 * 构造器
	 * @param gobang Gobang类实例
	 */
	public Tool(Gobang gobang) {
		game = gobang;
	}
	
	/**
	 * 初始化组件 设置背景透明 无边框 字体
	 * @param x 组件
	 * @param f 字体
	 */
	static void initComponent (JComponent x, Font f) {
		x.setBackground(new Color(255, 255, 255));
		x.setFont(f);
		x.setOpaque(false);
		x.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
	}
	
	/**
	 * 设置按钮被选中效果
	 * @param b 按钮
	 * @param width 图片宽
	 * @param height 图片高
	 */
	static void setEntered (JButton b, int width, int height) {
		b.setIcon(new ImageIcon(Tool.select.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
		b.setHorizontalTextPosition(SwingConstants.CENTER);
		b.setForeground(Color.WHITE);  // 字体颜色
	}
	
	/**
	 * 设置按钮未选中效果
	 * @param b 按钮
	 */
	static void setExited (JButton b) {
		b.setIcon(null);
		b.setForeground(Color.BLACK);  // 字体颜色
	}

	/**
	 * 为按钮添加鼠标监听器 实现选中效果
	 * @param b 按钮
	 * @param width 图片宽
	 * @param height 图片高
	 */
	static void addMListener(JButton b, int width, int height) {
		b.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {	
				Tool.setEntered(b, width, height);
				super.mouseEntered(arg0);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {	
				Tool.setExited(b);
				super.mouseExited(arg0);
			}
		});
	}
	
	/**
	 * 获取字体
	 * @param path 字体路径
	 * @param type 字体类型(plain,bold...)
	 * @param size 字体大小
	 * @return 生成字体
	 */
	static Font getFont(String path, int type, int size) {
		Font font = null;
		File file = new File(path);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, file);
			font = font.deriveFont(type, size);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return font;
	}
	
	/**
	 * 仅为上一步棋添加方形标记
	 * @param g 画笔
	 * @param r 记录
	 */
	private static void showlast (Graphics2D g, ArrayList<Record> r) {		
		Record last = r.get(r.size()-1);
		if (last == null)
			return ;
		
		int x = last.getI();
		int y = last.getJ();
		Color color = last.getColor().equals(Color.WHITE) ? Color.DARK_GRAY : Color.LIGHT_GRAY;
		g.setColor(color);
		g.setStroke(new BasicStroke(2.0f));
		g.drawRect(CELL * y - RECTSIZE / 2 + MARGIN, CELL * x - RECTSIZE / 2 + MARGIN, RECTSIZE, RECTSIZE);
	}

	/**
	 * 为所有棋子添加步数标记
	 * @param g 画笔
	 * @param r 记录
	 */
	private static void showall(Graphics2D g, ArrayList<Record> r) {
		int i = 1;
		g.setFont(getFont(fontwei, Font.BOLD, FONTSIZE));
		int x;
		int y;
		for (Record item : r) {
			Color c = item.getColor().equals(Color.WHITE) ? Color.DARK_GRAY : Color.LIGHT_GRAY;
			g.setColor(c);
			if (i > 99) 	// 三位数
				x = CELL * item.getJ() + MARGIN + NUMOFFSETX * 3;
			else if (i > 9) // 两位数
				x = CELL * item.getJ() + MARGIN + NUMOFFSETX * 2;
			else 			// 一位数
				x = CELL * item.getJ() + MARGIN + NUMOFFSETX * 1;
			
			y = CELL * item.getI() + MARGIN + NUMOFFSETY;
			g.drawString(String.valueOf(i++), x, y);
		}
	}
	
	/**
	 * 显示标记
	 * @param g 画笔
	 */
	static void show (Graphics g) {
		if (game.getListener() == null)
			return ;
		ArrayList<Record> r = game.getListener().getRecord();
		if (r == null || r.size() == 0)
			return ;
		
		Graphics2D g2 = (Graphics2D) g;
		if (show == 1 || reviewflag)  // 当选项显示所有被选中或游戏结束复盘时
			showall(g2, r);
		else
			showlast(g2, r);
		// 立即更新UI
		game.getBoard().updateUI(); 
	}

	/**
	 * 通过点的横坐标计算对应棋子的列数
	 * @param x 横坐标
	 * @return 列
	 */
	static int getj (int x) {
		return (int) Math.round(1.0 * (x - MARGIN) / CELL);
	}

	/**
	 * 通过点点纵坐标计算对应棋子的行数
	 * @param y 纵坐标
	 * @return 行
	 */
	static int geti (int y) {
		return (int) Math.round(1.0 * (y - MARGIN * 2) / CELL);
	}
}
