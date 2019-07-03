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
 * GobangListener�� 
 * ����ť�ȼ�����������
 * ���������Ŀ��� 
 * ��Ϸ״̬�ļ��
 */
public class GobangListener extends MouseAdapter {
	
	private int step;					// ����
	private boolean enable; 			// �����Ƿ��������
	
	private Gobang gobang;  			// Gobangʵ��
	private WinFrame winFrame;			// ʤ��frame
	private SettingFrame settingFrame;  // ����frame
	private AI ai;                      // aiʵ��
	
	private Cursor whiteChessCursor;	// �������
	private Cursor blackChessCursor;    // �������
	 
	private Record last;				// �����һ��
	private ArrayList<Record> record;   // ��ּ�¼

	/**
	 * ������ ��ʼ�������ü�����
	 * @param gobang Gobang��ʵ��
	 */
	public GobangListener (Gobang gobang) {
		// ��ʼ��
		
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
				
		// ���ü�����
		
		winFrame.exit.addActionListener((event) -> {
			// �˳���Ϸ
			System.exit(0);
		});
		
		winFrame.again.addActionListener((event) -> {
			// ����һ��
			gobang.setEnabled(true);
			restart();
			winFrame.setVisible(false);
		});
		
		winFrame.addWindowListener(new WindowAdapter() {
			// ���ڹرպ��� ��ʾ����˳��
			@Override
			public void windowClosing(WindowEvent e) {  
	              gobang.setEnabled(true);
	              gobang.getPanel().withdraw.setEnabled(false);  // ��ʱ���ܻ���
	              enable = false;			// ��ʱ��������
	              Tool.reviewflag = true;   // ���� ��ʾ����˳��      
	         }            
		});
		
		gobang.getPanel().exit.addActionListener((event) -> {
			// �˳���Ϸ
			System.exit(0);
		});
		
		gobang.getPanel().start.addActionListener((event) -> {
			// ��ʼ��Ϸ
			restart();
		});
		
		gobang.getPanel().setting.addActionListener((event) -> {
			// ���� ��������frame
			gobang.setEnabled(false);
			settingFrame.setVisible(true);
		});
		
		gobang.getPanel().withdraw.addActionListener((event) -> {
			// ����
			if (Tool.model == Tool.PVP) { // ����ģʽ�Ƴ�last
				step--;
				record.remove(last);
			}
			else {						  // �˻�ģʽ�Ƴ�last��ai��last
				step = step - 2; 
				record.remove(ai.getLast());
				record.remove(last);
				gobang.getBoard().getState()[ai.getLast().getI()][ai.getLast().getJ()] = null;
			}
			int i = last.getI();
			int j = last.getJ();
			// ������һ��
			if (record.size() == 0)
				last = null;
			else		
			last = record.get(record.size() - 1);      
			gobang.getBoard().getState()[i][j] = null;
			updateUI();	
			
			gobang.getPanel().withdraw.setEnabled(false); // ���û��尴ť������
			gobang.getPanel().withdraw.setIcon(null);
			gobang.getPanel().withdraw.setForeground(Color.BLACK);
		});
		
		gobang.getPanel().defeat.addActionListener((event) -> {
			// ���� ����ʤ��frame
			gobang.setEnabled(false);
			winFrame.setWinner(step);
			winFrame.setVisible(true);
		});
		
		settingFrame.certain.addActionListener((event) -> {
			// ȷ������ 
			gobang.setEnabled(true);
			saveSetting();  // ��������
			settingFrame.setVisible(false);
		});
		
		settingFrame.cancel.addActionListener((event) -> {
			// ȡ������
			gobang.setEnabled(true);
			// �ָ�����ǰ��״̬
			settingFrame.setModel(Tool.model);
			settingFrame.setFirst(Tool.first);
			settingFrame.setShow(Tool.show);
			settingFrame.setVisible(false);
			if (Tool.model == Tool.PVE) {  // ��Ϊ�˻�ģʽ ��ɸı�����
				settingFrame.firstP.setEnabled(true); 
				settingFrame.firstE.setEnabled(true); 
			}
			else {						   // ��Ϊ����ģʽ ���ܸı�����
				settingFrame.firstP.setEnabled(false); 
				settingFrame.firstE.setEnabled(false); 
			}
		});
		
		settingFrame.addWindowListener(new WindowAdapter() {
			// ���ô��ڹر� ��ȷ��������ͬ
			public void windowClosing(WindowEvent e) {  
	              gobang.setEnabled(true);
	              saveSetting();
	              settingFrame.setVisible(false);
	         }            
		});
		
		// ����UI
		updateUI();
	}

	/**
	 * ��������
	 */
	private void saveSetting() {
		int m = Tool.model;
		Tool.model = settingFrame.getModel();
		int f = Tool.first;
		Tool.first = settingFrame.getFirst();
		Tool.show = settingFrame.getShow();
		// �����Ϸģʽ���˻�ģʽ�����ָı� ���ؿ���Ϸ
		if (Tool.model != m || (Tool.model == Tool.PVE && Tool.first != f)) {
			restart();
		}
	}
	
	/**
	 * aiӦ���������(i,j)λ�õ���
	 * @param i ������
	 * @param j ������
	 */
	private void playwithAI (int i, int j) {
		Record r = ai.play(gobang.getBoard().getState(), i, j);
		record.add(r); 	// ��¼��һ����
		gobang.getBoard().setChess(r.getI(), r.getJ(), r.getColor());
		step++;
		updateUI();
		check(r.getI(), r.getJ()); // ����Ƿ�5������
	}
	
	/**
	 * ��дmouseReleased����
	 */
	@Override
	public void mouseReleased (MouseEvent e) { 
		// ���ܿ������� ֱ�ӷ���
		if (!enable)
			return ;
		
		// ��ȡ��ǰ��λ�ö�Ӧ���̵ĺ�������
		int i = Tool.geti(e.getY());
		int j = Tool.getj(e.getX());
		// �粻�����̷�Χ�� ��ֱ�ӷ���
		if (i < 0 || j < 0 || i >= Tool.ROWNUM || j>= Tool.COLNUM)
			return ;
		
		Color c = step % 2 == 1 ? Color.BLACK : Color.WHITE;	// ������ɫͨ��������ż�ж�	
		boolean flag = gobang.getBoard().setChess(i, j, c);     // �����Ƿ���Ч
		
		if (flag) {
			last = new Record(i, j); // �����һ��
			last.setColor(c);
			record.add(last);
			step++;
			updateUI();
			
			if (!check(i, j) && Tool.model == Tool.PVE) // �����Ϸ����������PVEģʽ 
				playwithAI(i, j);
		}
	}

	/**
	 * �����Ϸ�Ƿ����
	 * @param i ������
	 * @param j ������
	 * @return ��ʤ����ƽ�� �򷵻�true ��Ϸ�����򷵻�false
	 */
	private boolean check(int i, int j) {
		if(checkWin(i, j) || step == Tool.ROWNUM * Tool.COLNUM + 1) {
			// ʤ��������������(ƽ��) �򵯳�ʤ��frame
			gobang.setEnabled(false);
			winFrame.setWinner(step);
			winFrame.setVisible(true);
			return true;
		} 
		
		return false;
	}
	
	/**
	 * ����UI �������ͼ�����Ϸ���״̬
	 */
	private void updateUI () {
		if (step % 2 == 1) 
			gobang.getBoard().setCursor(blackChessCursor); 
		else 
			gobang.getBoard().setCursor(whiteChessCursor); 
		
		gobang.getPanel().updateUI(step);
	}

	/**
	 * ���¿�ʼ
	 */
	private void restart() {
		// ��λ
		step = 1;
		enable = true;
		Tool.reviewflag = false;
		gobang.getBoard().restart();
		record.clear();
		
		if (Tool.model == Tool.PVE && Tool.first == Tool.FIRSTE) {	
			// PVE����ai���� 
			ai.setColor(Color.BLACK);
			// ��һ��������
			Record r = new Record(Tool.ROWNUM / 2, Tool.COLNUM / 2);
			r.setColor(Color.BLACK);
			record.add(r);
			step++;		
			gobang.getBoard().setChess(Tool.ROWNUM / 2, Tool.COLNUM / 2, Color.BLACK);
			ai.setFirst(gobang.getBoard().getState());
		} else if (Tool.model == Tool.PVE && Tool.first == Tool.FIRSTP) {
			// PVE�����������
			ai.setColor(Color.WHITE);
		}
		updateUI();
	}
	
	/**
	 * ������ּ�¼
	 */
	public ArrayList<Record> getRecord() {
		return record;
	}
	
	/**
	 * ����Ƿ��ʤ �ж�(i,j)�ĸ��������Ƿ�����������
	 * @param i ������
	 * @param j ������
	 * @return ��ʤ������true ���򷵻�false
	 */
	private Boolean checkWin (int i, int j) {
		int x;
		int y;
		int count;
		Chess[][] state = gobang.getBoard().getState();
		Color c = state[i][j].getColor();
		
		// ����
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
		
		// ����
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
		
		// ����-����
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
		
		// ����-����
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
