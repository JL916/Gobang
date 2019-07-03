package gobang;

import java.awt.Color;

/**
 * Record�� ��¼һ�����ӵ���ɫ��λ��
 */
class Record {
	
	private Color color; // ��ɫ
	private int i;		 // ������
	private int j;       // ������
	
	/**
	 * ������
	 * @param color ��ɫ
	 */
	public Record (Color color) {
		this.color = color;
	}

	/**
	 * ������
	 * @param i ������
	 * @param j ������
	 */
	public Record (int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	/**
	 * ��ȡ������
	 */
	public int getI() {
		return i;
	}

	/**
	 * ���ú�����
	 * @param i ������
	 */
	public void setI(int i) {
		this.i = i;
	}

	/**
	 * ��ȡ������
	 */
	public int getJ() {
		return j;
	}

	/**
	 * ����������
	 * @param j ������
	 */
	public void setJ(int j) {
		this.j = j;
	}

	/**
	 * ��ȡ��ɫ
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * ������ɫ
	 * @param color ��ɫ
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
