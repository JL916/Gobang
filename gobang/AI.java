package gobang;

import java.awt.Color;
import java.util.HashMap;

/**
 * AI类  权值法实现电脑下棋
 */
public class AI {

	private HashMap<Integer, Integer> weightmap; 	// 权值哈希表
	
	private int[][] weightE;  // 存储电脑权值
	private int[][] weightP;  // 存储玩家权值
		
	private Color colorE;	  // 电脑棋子颜色
	private Color colorP;	  // 玩家棋子颜色
	
	private Chess[][] state;  // 棋盘状态
	private Record last;	  // 记录电脑上一步棋
	
	/**
	 * 构造器
	 * @param c 电脑的棋子颜色
	 */
	public AI (Color c) {
		setColor(c);
		weightmap = new HashMap<>();
		weightE = new int[Tool.ROWNUM][Tool.COLNUM];
		weightP = new int[Tool.ROWNUM][Tool.COLNUM];
			
		// 哈希表
		// key的第一个数字代表某一方向上连子的数量 范围 1 - 5
		// key的第二个数字代表该方向上两端可延伸的方向数量 范围 0 - 2
		// value为所赋权值
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
	 * 电脑先行 第一步下在棋盘中央
	 * @param state 棋局状态 棋子的二维数组
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
	 * 玩家先行
	 * @param state 棋局状态
	 * @param x 玩家上一步的横坐标
	 * @param y 玩家上一步的纵坐标
	 * @return 记录电脑下棋的位置和颜色
	 */
	public Record play (Chess[][] state, int x, int y) {
		this.state = state;
		// 更新权值表
		updateWeight(x, y);
		Record r = new Record(colorE);
		
		int max = 0;		// 记录电脑或玩家的最大权值
		int opposite = 0;   // 记录最大权值所在位置对手的权值
		// 遍历两个权值二维数组 找到最大权值
		// 如果权值相等 选择对手权值较大的点
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
		
		// 记录电脑的上一步棋
		last = r;
		// 更新权值表
		updateWeight(r.getI(), r.getJ());
		
		return r;
	}
	
	/**
	 * 更新权值表
	 * @param i 上一步棋的横坐标
	 * @param j 上一步棋的纵坐标
	 */
	private void updateWeight (int i, int j) {	
		// 遍历棋盘的每一个位置 探测该点权值
		for (int p = 0; p < Tool.ROWNUM; p++) {
			for (int q = 0; q < Tool.COLNUM; q++) {
				if (state[p][q] != null)
					continue;
				weightE[p][q] = getWeight(p, q, colorE);
				weightP[p][q] = getWeight(p, q, colorP);
			}
		}
		
		// 有棋子的点权值置为0
		weightE[i][j] = 0;
		weightP[i][j] = 0;
	}
	
	/**
	 * 探测(i,j)位置的权值大小
	 * @param i 横坐标
	 * @param j 纵坐标
	 * @param c 该点颜色
	 * @return 返回4个方向权值大小之和作为该点的权值
	 */
	private int getWeight (int i, int j, Color c) {
		int weight = 0;
		int count; // 某一方向上连子数量 关键字的第一位
		int p;
		int q;
		int flag;  // 某一方向上可延伸的方向数 关键字第二位
		
		// 竖向
		flag = 0;
		count = 1;
		p = i - 1;
		q = j;
		while (p >= 0 && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			p--;
		}
		// 如果上方没有棋子 则上方可以延伸 flag+1
		if (p >= 0 && state[p][q] == null)
			flag++;
		p = i + 1;
		q = j;
		while (p < Tool.ROWNUM && state[p][q] != null && state[p][q].getColor().equals(c)) {
			count++;
			p++;
		}
		// 如果下方没有棋子 则下方可以延伸 flag+1
		if (p < Tool.ROWNUM && state[p][q] == null)
			flag++;
		
		// 连子数超过5 按5计算
		if (count > 5)
			count = 5;
		// 从哈希表中取出权值
		weight += weightmap.get(count * 10 + flag);
		
		// 其他方向以此类推
		
		// 横向
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
		
		// 右上-左下
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
	
		// 左上-右下
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
	 * 返回电脑的上一步
	 */
	public Record getLast() {
		return last;
	}

	/**
	 * 设置电脑棋子颜色与玩家棋子颜色
	 * @param c 电脑棋子颜色
	 */
	public void setColor(Color c) {
		colorE = c;
		if (c.equals(Color.WHITE))
			colorP = Color.BLACK;
		else
			colorP = Color.WHITE;
	}
}