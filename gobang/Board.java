package gobang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Board�� ��������
 * �̳���JPanel
 * �������̵Ļ��ƺ���ֵļ�¼
 */
public class Board extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Chess[][] state;	// ��¼���

	/**
	 * ������ ��ʼ��state ���óߴ��С
	 */
	public Board () {
		state = new Chess[Tool.ROWNUM][Tool.COLNUM];
		setSize(Tool.BOARDSIZE, Tool.BOARDSIZE);
	}
	
	/**
	 * �������̱���
	 */
	@Override
	protected void paintComponent(Graphics g) {   
        g.drawImage(Tool.board, 0, 0, getSize().width, getSize().height, this);  
    } 
	
	/**
	 * ��������
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// ��������
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2.0f));
		// �������ĺ��Ľ������
		int size = Tool.BOARDPOINTSIZE;
		g2.fillOval(Tool.BOARDSIZE / 2 - 5, Tool.BOARDSIZE / 2 - 5, 10, 10);
		g2.fillOval(Tool.MARGIN + Tool.CELL * 3 - size / 2, Tool.MARGIN + Tool.CELL * 3 - size / 2, size, size);
		g2.fillOval(Tool.MARGIN + Tool.CELL * 3 - size / 2, Tool.BOARDSIZE - Tool.MARGIN - Tool.CELL * 3 - size / 2, size, size);
		g2.fillOval(Tool.BOARDSIZE - Tool.MARGIN - Tool.CELL * 3 - size / 2, Tool.MARGIN + Tool.CELL * 3 - size / 2, size, size);
		g2.fillOval(Tool.BOARDSIZE - Tool.MARGIN - Tool.CELL * 3 - size / 2, Tool.BOARDSIZE - Tool.MARGIN - Tool.CELL * 3 - size / 2, size, size);
		// ���ƺ���15����
		for (int i = 0; i < Tool.ROWNUM; i++) 
			g2.drawLine(Tool.MARGIN, Tool.MARGIN + i * Tool.CELL, Tool.BOARDSIZE - Tool.MARGIN, Tool.MARGIN + i * Tool.CELL);
		for (int i = 0; i < Tool.COLNUM; i++)
			g2.drawLine(Tool.MARGIN + i * Tool.CELL, Tool.MARGIN, Tool.MARGIN + i * Tool.CELL, Tool.BOARDSIZE - Tool.MARGIN);
			
		// ��������
		for (int i = 0; i < Tool.ROWNUM; i++) {
			for (int j = 0; j < Tool.COLNUM; j++) {
				if (state[i][j] != null) {
					state[i][j].draw(g, i, j);
				}
			}
		}
		
		// ��ʾ���
		Tool.show(g);
	}
	
	/**
	 * ��ʼ��״̬���� ���»�������
	 */
	public void restart () {
		state = new Chess[Tool.ROWNUM][Tool.COLNUM];
		repaint();
	}
	
	/**
	 * ���Ӻ����״̬���� ���»�������
	 * @param i ���Ӻ�����
	 * @param j ����������
	 * @param color ������ɫ
	 * @return �����Ƿ�ɹ�
	 */
	public Boolean setChess (int i, int j, Color color) {
		// �����ǰλ�ò�Ϊ�� ����ʧ��
		if (state[i][j] != null)
			return false;
		state[i][j] = new Chess(color);
		repaint();
		return true;
	}

	/**
	 * ��ȡ״̬����
	 */
	public Chess[][] getState() {
		return state;
	}
}
