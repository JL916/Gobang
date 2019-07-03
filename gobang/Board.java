package gobang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Board类 控制棋盘
 * 继承自JPanel
 * 负责棋盘的绘制和棋局的记录
 */
public class Board extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Chess[][] state;	// 记录棋局

	/**
	 * 构造器 初始化state 设置尺寸大小
	 */
	public Board () {
		state = new Chess[Tool.ROWNUM][Tool.COLNUM];
		setSize(Tool.BOARDSIZE, Tool.BOARDSIZE);
	}
	
	/**
	 * 绘制棋盘背景
	 */
	@Override
	protected void paintComponent(Graphics g) {   
        g.drawImage(Tool.board, 0, 0, getSize().width, getSize().height, this);  
    } 
	
	/**
	 * 绘制棋盘
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// 绘制棋盘
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2.0f));
		// 绘制中心和四角五个点
		int size = Tool.BOARDPOINTSIZE;
		g2.fillOval(Tool.BOARDSIZE / 2 - 5, Tool.BOARDSIZE / 2 - 5, 10, 10);
		g2.fillOval(Tool.MARGIN + Tool.CELL * 3 - size / 2, Tool.MARGIN + Tool.CELL * 3 - size / 2, size, size);
		g2.fillOval(Tool.MARGIN + Tool.CELL * 3 - size / 2, Tool.BOARDSIZE - Tool.MARGIN - Tool.CELL * 3 - size / 2, size, size);
		g2.fillOval(Tool.BOARDSIZE - Tool.MARGIN - Tool.CELL * 3 - size / 2, Tool.MARGIN + Tool.CELL * 3 - size / 2, size, size);
		g2.fillOval(Tool.BOARDSIZE - Tool.MARGIN - Tool.CELL * 3 - size / 2, Tool.BOARDSIZE - Tool.MARGIN - Tool.CELL * 3 - size / 2, size, size);
		// 绘制横竖15条线
		for (int i = 0; i < Tool.ROWNUM; i++) 
			g2.drawLine(Tool.MARGIN, Tool.MARGIN + i * Tool.CELL, Tool.BOARDSIZE - Tool.MARGIN, Tool.MARGIN + i * Tool.CELL);
		for (int i = 0; i < Tool.COLNUM; i++)
			g2.drawLine(Tool.MARGIN + i * Tool.CELL, Tool.MARGIN, Tool.MARGIN + i * Tool.CELL, Tool.BOARDSIZE - Tool.MARGIN);
			
		// 绘制棋子
		for (int i = 0; i < Tool.ROWNUM; i++) {
			for (int j = 0; j < Tool.COLNUM; j++) {
				if (state[i][j] != null) {
					state[i][j].draw(g, i, j);
				}
			}
		}
		
		// 显示标记
		Tool.show(g);
	}
	
	/**
	 * 初始化状态数组 重新绘制棋盘
	 */
	public void restart () {
		state = new Chess[Tool.ROWNUM][Tool.COLNUM];
		repaint();
	}
	
	/**
	 * 落子后更新状态数组 重新绘制棋盘
	 * @param i 棋子横坐标
	 * @param j 棋子纵坐标
	 * @param color 棋子颜色
	 * @return 返回是否成功
	 */
	public Boolean setChess (int i, int j, Color color) {
		// 如果当前位置不为空 落子失败
		if (state[i][j] != null)
			return false;
		state[i][j] = new Chess(color);
		repaint();
		return true;
	}

	/**
	 * 获取状态数组
	 */
	public Chess[][] getState() {
		return state;
	}
}
