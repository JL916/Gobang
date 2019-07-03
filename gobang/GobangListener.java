package gobang;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * GobangListener类 
 * 各按钮等监听器的设置
 * 各弹出面板的控制 
 * 游戏状态的检测
 */
public class GobangListener extends MouseAdapter {
	
	private int step;					// 步数
	private boolean enable; 			// 棋盘是否可以下棋
	
	private Gobang gobang;  			// Gobang实例
	private WinFrame winFrame;			// 胜利frame
	private SettingFrame settingFrame;  // 设置frame
	private AI ai;                      // ai实例
	
	private Cursor whiteChessCursor;	// 白棋鼠标
	private Cursor blackChessCursor;    // 黑棋鼠标
	 
	private Record last;				// 玩家上一步
	private ArrayList<Record> record;   // 棋局记录

	/**
	 * 构造器 初始化和设置监听器
	 * @param gobang Gobang类实例
	 */
	public GobangListener (Gobang gobang) {
		// 初始化
		
		this.gobang = gobang;
		winFrame = new WinFrame();
		settingFrame = new SettingFrame();
		ai = new AI(Color.WHITE);
		record = new ArrayList<>();
		
		step = 1;
		enable = true;
		
		Toolkit tk = Toolkit.getDefaultToolkit(); 
		whiteChessCursor = tk.createCustomCursor(Tool.whiteChess, new Point(10, 10), "norm");
		blackChessCursor = tk.createCustomCursor(Tool.blackChess, new Point(10, 10), "norm");
				
		// 设置监听器
		
		winFrame.exit.addActionListener((event) -> {
			// 退出游戏
			System.exit(0);
		});
		
		winFrame.again.addActionListener((event) -> {
			// 再来一盘
			gobang.setEnabled(true);
			restart();
			winFrame.setVisible(false);
		});
		
		winFrame.addWindowListener(new WindowAdapter() {
			// 窗口关闭后复盘 显示下棋顺序
			@Override
			public void windowClosing(WindowEvent e) {  
	              gobang.setEnabled(true);
	              gobang.getPanel().withdraw.setEnabled(false);  // 此时不能悔棋
	              enable = false;			// 此时不能下棋
	              Tool.reviewflag = true;   // 复盘 显示下棋顺序      
	         }            
		});
		
		gobang.getPanel().exit.addActionListener((event) -> {
			// 退出游戏
			System.exit(0);
		});
		
		gobang.getPanel().start.addActionListener((event) -> {
			// 开始游戏
			restart();
		});
		
		gobang.getPanel().setting.addActionListener((event) -> {
			// 设置 弹出设置frame
			gobang.setEnabled(false);
			settingFrame.setVisible(true);
		});
		
		gobang.getPanel().withdraw.addActionListener((event) -> {
			// 悔棋
			if (Tool.model == Tool.PVP) { // 人人模式移除last
				step--;
				record.remove(last);
			}
			else {						  // 人机模式移除last和ai的last
				step = step - 2; 
				record.remove(ai.getLast());
				record.remove(last);
				gobang.getBoard().getState()[ai.getLast().getI()][ai.getLast().getJ()] = null;
			}
			int i = last.getI();
			int j = last.getJ();
			// 更新上一步
			if (record.size() == 0)
				last = null;
			else		
			last = record.get(record.size() - 1);      
			gobang.getBoard().getState()[i][j] = null;
			updateUI();	
			
			gobang.getPanel().withdraw.setEnabled(false); // 设置悔棋按钮不可用
			gobang.getPanel().withdraw.setIcon(null);
			gobang.getPanel().withdraw.setForeground(Color.BLACK);
		});
		
		gobang.getPanel().defeat.addActionListener((event) -> {
			// 认输 弹出胜利frame
			gobang.setEnabled(false);
			winFrame.setWinner(step);
			winFrame.setVisible(true);
		});
		
		settingFrame.certain.addActionListener((event) -> {
			// 确认设置 
			gobang.setEnabled(true);
			saveSetting();  // 保存设置
			settingFrame.setVisible(false);
		});
		
		settingFrame.cancel.addActionListener((event) -> {
			// 取消设置
			gobang.setEnabled(true);
			// 恢复设置前的状态
			settingFrame.setModel(Tool.model);
			settingFrame.setFirst(Tool.first);
			settingFrame.setShow(Tool.show);
			settingFrame.setVisible(false);
			if (Tool.model == Tool.PVE) {  // 若为人机模式 则可改变先手
				settingFrame.firstP.setEnabled(true); 
				settingFrame.firstE.setEnabled(true); 
			}
			else {						   // 若为人人模式 则不能改变先手
				settingFrame.firstP.setEnabled(false); 
				settingFrame.firstE.setEnabled(false); 
			}
		});
		
		settingFrame.addWindowListener(new WindowAdapter() {
			// 设置窗口关闭 与确认设置相同
			public void windowClosing(WindowEvent e) {  
	              gobang.setEnabled(true);
	              saveSetting();
	              settingFrame.setVisible(false);
	         }            
		});
		
		// 更新UI
		updateUI();
	}

	/**
	 * 保存设置
	 */
	private void saveSetting() {
		int m = Tool.model;
		Tool.model = settingFrame.getModel();
		int f = Tool.first;
		Tool.first = settingFrame.getFirst();
		Tool.show = settingFrame.getShow();
		// 如果游戏模式或人机模式下先手改变 则重开游戏
		if (Tool.model != m || (Tool.model == Tool.PVE && Tool.first != f)) {
			restart();
		}
	}
	
	/**
	 * ai应对玩家下在(i,j)位置的棋
	 * @param i 横坐标
	 * @param j 纵坐标
	 */
	private void playwithAI (int i, int j) {
		Record r = ai.play(gobang.getBoard().getState(), i, j);
		record.add(r); 	// 记录这一步棋
		gobang.getBoard().setChess(r.getI(), r.getJ(), r.getColor());
		step++;
		updateUI();
		check(r.getI(), r.getJ()); // 检查是否5子相连
	}
	
	/**
	 * 重写mouseReleased方法
	 */
	@Override
	public void mouseReleased (MouseEvent e) { 
		// 不能控制棋盘 直接返回
		if (!enable)
			return ;
		
		// 获取当前点位置对应棋盘的横纵坐标
		int i = Tool.geti(e.getY());
		int j = Tool.getj(e.getX());
		// 如不在棋盘范围内 则直接返回
		if (i < 0 || j < 0 || i >= Tool.ROWNUM || j>= Tool.COLNUM)
			return ;
		
		Color c = step % 2 == 1 ? Color.BLACK : Color.WHITE;	// 棋子颜色通过步数奇偶判断	
		boolean flag = gobang.getBoard().setChess(i, j, c);     // 落子是否有效
		
		if (flag) {
			last = new Record(i, j); // 玩家上一步
			last.setColor(c);
			record.add(last);
			step++;
			updateUI();
			
			if (!check(i, j) && Tool.model == Tool.PVE) // 如果游戏继续并且是PVE模式 
				playwithAI(i, j);
		}
	}

	/**
	 * 检查游戏是否结束
	 * @param i 横坐标
	 * @param j 纵坐标
	 * @return 若胜利或平局 则返回true 游戏继续则返回false
	 */
	private boolean check(int i, int j) {
		if(checkWin(i, j) || step == Tool.ROWNUM * Tool.COLNUM + 1) {
			// 胜利或者棋盘已满(平局) 则弹出胜利frame
			gobang.setEnabled(false);
			winFrame.setWinner(step);
			winFrame.setVisible(true);
			return true;
		} 
		
		return false;
	}
	
	/**
	 * 更新UI 设置鼠标图标和游戏面板状态
	 */
	private void updateUI () {
		if (step % 2 == 1) 
			gobang.getBoard().setCursor(blackChessCursor); 
		else 
			gobang.getBoard().setCursor(whiteChessCursor); 
		
		gobang.getPanel().updateUI(step);
	}

	/**
	 * 重新开始
	 */
	private void restart() {
		// 复位
		step = 1;
		enable = true;
		Tool.reviewflag = false;
		gobang.getBoard().restart();
		record.clear();
		
		if (Tool.model == Tool.PVE && Tool.first == Tool.FIRSTE) {	
			// PVE并且ai先手 
			ai.setColor(Color.BLACK);
			// 第一步正中央
			Record r = new Record(Tool.ROWNUM / 2, Tool.COLNUM / 2);
			r.setColor(Color.BLACK);
			record.add(r);
			step++;		
			gobang.getBoard().setChess(Tool.ROWNUM / 2, Tool.COLNUM / 2, Color.BLACK);
			ai.setFirst(gobang.getBoard().getState());
		} else if (Tool.model == Tool.PVE && Tool.first == Tool.FIRSTP) {
			// PVE并且玩家先手
			ai.setColor(Color.WHITE);
		}
		updateUI();
	}
	
	/**
	 * 返回棋局记录
	 */
	public ArrayList<Record> getRecord() {
		return record;
	}
	
	/**
	 * 检查是否获胜 判断(i,j)四个方向上是否有五子相连
	 * @param i 横坐标
	 * @param j 纵坐标
	 * @return 若胜利返回true 否则返回false
	 */
	private Boolean checkWin (int i, int j) {
		int x;
		int y;
		int count;
		Chess[][] state = gobang.getBoard().getState();
		Color c = state[i][j].getColor();
		
		// 竖向
		count = 1;
		x = i - 1;
		y = j;
		while (x >= 0 && state[x][y] != null && state[x][y].getColor().equals(c)) {
			count++;
			x--;
		}
		x = i + 1;
		y = j;
		while (x < Tool.ROWNUM && state[x][y] != null && state[x][y].getColor().equals(c)) {
			count++;
			x++;
		}
		if (count >= 5)
			return true;
		
		// 横向
		count = 1;
		x = i;
		y = j - 1;
		while (y >= 0 && state[x][y] != null && state[x][y].getColor().equals(c)) {
			count++;
			y--;
		}
		x = i;
		y = j + 1;
		while (y < Tool.COLNUM && state[x][y] != null && state[x][y].getColor().equals(c)) {
			count++;
			y++;
		}
		if (count >= 5)
			return true;
		
		// 右上-左下
		count = 1;
		x = i - 1;
		y = j + 1;
		while (x >= 0 && y < Tool.COLNUM && state[x][y] != null && state[x][y].getColor().equals(c)) {
			count++;
			x--;
			y++;
		}
		x = i + 1;
		y = j - 1;
		while (x < Tool.ROWNUM && y >= 0 && state[x][y] != null && state[x][y].getColor().equals(c)) {
			count++;
			x++;
			y--;
		}
		if (count >= 5)
			return true;
		
		// 左上-右下
		count = 1;
		x = i - 1;
		y = j - 1;
		while (x >= 0 && y >= 0 && state[x][y] != null && state[x][y].getColor().equals(c)) {
			count++;
			x--;
			y--;
		}
		x = i + 1;
		y = j + 1;
		while (x < Tool.ROWNUM && y < Tool.COLNUM && state[x][y] != null && state[x][y].getColor().equals(c)) {
			count++;
			x++;
			y++;
		}
		if (count >= 5)
			return true;
		return false;
	}
}
