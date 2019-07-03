package gobang;

import java.awt.Color;
import java.util.HashMap;

/**
 * AI��  Ȩֵ��ʵ�ֵ�������
 */
public class AI {

	private HashMap<Integer, Integer> weightmap; 	// Ȩֵ��ϣ��
	
	private int[][] weightE;  // �洢����Ȩֵ
	private int[][] weightP;  // �洢���Ȩֵ
		
	private Color colorE;	  // ����������ɫ
	private Color colorP;	  // ���������ɫ
	
	private Chess[][] state;  // ����״̬
	private Record last;	  // ��¼������һ����
	
	/**
	 * ������
	 * @param c ���Ե�������ɫ
	 */
	public AI (Color c) {
		setColor(c);
		weightmap = new HashMap<>();
		weightE = new int[Tool.ROWNUM][Tool.COLNUM];
		weightP = new int[Tool.ROWNUM][Tool.COLNUM];
			
		// ��ϣ��
		// key�ĵ�һ�����ִ���ĳһ���������ӵ����� ��Χ 1 - 5
		// key�ĵڶ������ִ���÷��������˿�����ķ������� ��Χ 0 - 2
		// valueΪ����Ȩֵ
		weightmap.put(10, 0);
		weightmap.put(11, 5);
		weightmap.put(12, 8);
		weightmap.put(20, 0);
		weightmap.put(21, 15);
        weightmap.put(22, 18);
        weightmap.put(30, 0);
        weightmap.put(31, 30);
        weightmap.put(32, 100);
        weightmap.put(40, 0);
        weightmap.put(41, 100);
        weightmap.put(42, 200);
        weightmap.put(50, 1000);
        weightmap.put(51, 1000);
        weightmap.put(52, 1000);
	}
	
	/**
	 * �������� ��һ��������������
	 * @param state ���״̬ ���ӵĶ�ά����
	 */
	public void setFirst (Chess[][] state) {
		this.state = state;
		int i = Tool.ROWNUM / 2;
		int j = Tool.COLNUM / 2;
		updateWeight(i, j);
		Record r = new Record(colorE);
		r.setI(i);
		r.setJ(j);
		last = r;		
	}
	
	/**
	 * �������
	 * @param state ���״̬
	 * @param x �����һ���ĺ�����
	 * @param y �����һ����������
	 * @return ��¼���������λ�ú���ɫ
	 */
	public Record play (Chess[][] state, int x, int y) {
		this.state = state;
		// ����Ȩֵ��
		updateWeight(x, y);
		Record r = new Record(colorE);
		
		int max = 0;		// ��¼���Ի���ҵ����Ȩֵ
		int opposite = 0;   // ��¼���Ȩֵ����λ�ö��ֵ�Ȩֵ
		// ��������Ȩֵ��ά���� �ҵ����Ȩֵ
		// ���Ȩֵ��� ѡ�����Ȩֵ�ϴ�ĵ�
		for (int i = 0; i < Tool.ROWNUM; i++) {
			for (int j = 0; j < Tool.COLNUM; j++) {
				if (weightP[i][j] > max) {
					max = weightP[i][j];
					r.setI(i);
					r.setJ(j);
					opposite = weightE[i][j];
				} else if (weightP[i][j] == max && weightE[i][j] > opposite) {
					max = weightP[i][j];
					r.setI(i);
					r.setJ(j);
					opposite = weightE[i][j];
				}
				if (weightE[i][j] > max) {
					max = weightE[i][j];
					r.setI(i);
					r.setJ(j);
					opposite = weightP[i][j];
				} else if (weightE[i][j] == max && weightP[i][j] > opposite) {
					max = weightE[i][j];
					r.setI(i);
					r.setJ(j);
					opposite = weightP[i][j];
				}
			}
		}
		
		// ��¼���Ե���һ����
		last = r;
		// ����Ȩֵ��
		updateWeight(r.getI(), r.getJ());
		
		return r;
	}
	
	/**
	 * ����Ȩֵ��
	 * @param i ��һ����ĺ�����
	 * @param j ��һ�����������
	 */
	private void updateWeight (int i, int j) {	
		// �������̵�ÿһ��λ�� ̽��õ�Ȩֵ
		for (int p = 0; p < Tool.ROWNUM; p++) {
			for (int q = 0; q < Tool.COLNUM; q++) {
				if (state[p][q] != null)
					continue;
				weightE[p][q] = getWeight(p, q, colorE);
				weightP[p][q] = getWeight(p, q, colorP);
			}
		}
		
		// �����ӵĵ�Ȩֵ��Ϊ0
		weightE[i][j] = 0;
		weightP[i][j] = 0;
	}
	
	/**
	 * ̽��(i,j)λ�õ�Ȩֵ��С
	 * @param i ������
	 * @param j ������
	 * @param c �õ���ɫ
	 * @return ����4������Ȩֵ��С֮����Ϊ�õ��Ȩֵ
	 */
	private int getWeight (int i, int j, Color c) {
		int weight = 0;
		int count; // ĳһ�������������� �ؼ��ֵĵ�һλ
		int p;
		int q;
		int flag;  // ĳһ�����Ͽ�����ķ����� �ؼ��ֵڶ�λ
		
		// ����
		flag = 0;
		count = 1;
		p = i - 1;
		q = j;
		while (p >= 0 && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			p--;
		}
		// ����Ϸ�û������ ���Ϸ��������� flag+1
		if (p >= 0 && state[p][q] == null)
			flag++;
		p = i + 1;
		q = j;
		while (p < Tool.ROWNUM && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			p++;
		}
		// ����·�û������ ���·��������� flag+1
		if (p < Tool.ROWNUM && state[p][q] == null)
			flag++;
		
		// ����������5 ��5����
		if (count > 5)
			count = 5;
		// �ӹ�ϣ����ȡ��Ȩֵ
		weight += weightmap.get(count * 10 + flag);
		
		// ���������Դ�����
		
		// ����
		flag = 0;
		count = 1;
		p = i;
		q = j - 1;
		while (q >= 0 && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			q--;
		}
		if (q >= 0 && state[p][q] == null)
			flag++;
		p = i;
		q = j + 1;
		while (q < Tool.COLNUM && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			q++;
		}
		if (q < Tool.COLNUM && state[p][q] == null)
			flag++;
		if (count > 5)
			count = 5;
		weight += weightmap.get(count * 10 + flag);
		
		// ����-����
		flag = 0;
		count = 1;
		p = i - 1;
		q = j + 1;
		while (p >= 0 && q < Tool.COLNUM && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			p--;
			q++;
		}
		if (p >= 0 && q < Tool.COLNUM && state[p][q] == null)
			flag++;
		p = i + 1;
		q = j - 1;
		while (p < Tool.ROWNUM && q >= 0 && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			p++;
			q--;
		}
		if (p < Tool.ROWNUM && q >= 0 && state[p][q] == null)
			flag++;
		if (count > 5)
			count = 5;
		weight += weightmap.get(count * 10 + flag);
	
		// ����-����
		flag = 0;
		count = 1;
		p = i - 1;
		q = j - 1;
		while (p >= 0 && q >= 0 && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			p--;
			q--;
		}
		if (p >= 0 && q >= 0 && state[p][q] == null)
			flag++;
		p = i + 1;
		q = j + 1;
		while (p < Tool.ROWNUM && q < Tool.COLNUM && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			p++;
			q++;
		}
		if (p < Tool.ROWNUM && q < Tool.COLNUM && state[p][q] == null)
			flag++;
		if (count > 5)
			count = 5;
		weight += weightmap.get(count * 10 + flag);
		
		return weight;
	}
	
	/**
	 * ���ص��Ե���һ��
	 */
	public Record getLast() {
		return last;
	}

	/**
	 * ���õ���������ɫ�����������ɫ
	 * @param c ����������ɫ
	 */
	public void setColor(Color c) {
		colorE = c;
		if (c.equals(Color.WHITE))
			colorP = Color.BLACK;
		else
			colorP = Color.WHITE;
	}
}