package gobang;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Chess�� ��������
 * �����¼��ɫ�ͻ����Լ�
 */
public class Chess {
	
	private Color color; // ������ɫ
	
	/**
	 * ������
	 * @param color ������ɫ
	 */
	public Chess (Color color) {
		this.color = color;
	}

	/**
	 * ��ȡ��������ɫ
	 */
	public Color getColor () {
		return color;
	}
	
	/**
	 * ���ø�������ɫ
	 * @param color ��ɫ
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * �����Լ�
	 * @param g ����
	 * @param i ������
	 * @param j ������
	 */
	public void draw(Graphics g, int i, int j) {
		if (color.equals(Color.BLACK))
			g.drawImage(Tool.blackChess, Tool.CELL * j - Tool.CHESSSIZE / 2 + Tool.MARGIN, Tool.CELL * i - Tool.CELL / 2 + Tool.MARGIN, Tool.CHESSSIZE, Tool.CHESSSIZE, null);
		else
			g.drawImage(Tool.whiteChess, Tool.CELL * j - Tool.CHESSSIZE / 2 + Tool.MARGIN, Tool.CELL * i - Tool.CELL / 2 + Tool.MARGIN, Tool.CHESSSIZE, Tool.CHESSSIZE, null);
	}
}
