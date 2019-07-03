package gobang;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Gobang�� ��Ŀ�����
 * ���Ƹ�����
 */
public class Gobang extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Board board;			 // ����
	private GamePanel panel;    	 // ��Ϸ���
	private GobangListener listener; // ������

	/**
	 * ������ ��ʼ�������� ���ü�����
	 */
	public Gobang () {
		new Tool(this);			  // ��ʼ����̬������
		board = new Board();	  // ��ʼ������
		panel = new GamePanel();  // ��ʼ�����
		
		// ����״̬
		setTitle("gobang");
		setSize(Tool.GAMEWIDTH, Tool.GAMEHEIGHT);
		setResizable(false);
		setLocation(Tool.GAMELOCATION);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ���ò���
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		add(panel, BorderLayout.EAST);

		// ���ü�����
		listener = new GobangListener(this);
		addMouseListener(listener);
	}	
	
	/**
	 * ��������
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * �������
	 */
	public GamePanel getPanel() {
		return panel;
	}
	
	/**
	 * ���ؼ�����
	 */
	public GobangListener getListener() {
		return listener;
	}
	
	/**
	 * ������ ��Ŀ���
	 */
	public static void main(String[] args) {	
		new Gobang();
	}
}