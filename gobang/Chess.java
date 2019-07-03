package gobang;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Chess类 代表棋子
 * 负责记录颜色和绘制自己
 */
public class Chess {
	
	private Color color; // 棋子颜色
	
	/**
	 * 构造器
	 * @param color 棋子颜色
	 */
	public Chess (Color color) {
		this.color = color;
	}

	/**
	 * 获取该棋子颜色
	 */
	public Color getColor () {
		return color;
	}
	
	/**
	 * 设置该棋子颜色
	 * @param color 颜色
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * 绘制自己
	 * @param g 画笔
	 * @param i 横坐标
	 * @param j 纵坐标
	 */
	public void draw(Graphics g, int i, int j) {
		if (color.equals(Color.BLACK))
			g.drawImage(Tool.blackChess, Tool.CELL * j - Tool.CHESSSIZE / 2 + Tool.MARGIN, Tool.CELL * i - Tool.CELL / 2 + Tool.MARGIN, Tool.CHESSSIZE, Tool.CHESSSIZE, null);
		else
			g.drawImage(Tool.whiteChess, Tool.CELL * j - Tool.CHESSSIZE / 2 + Tool.MARGIN, Tool.CELL * i - Tool.CELL / 2 + Tool.MARGIN, Tool.CHESSSIZE, Tool.CHESSSIZE, null);
	}
}
