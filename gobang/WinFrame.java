package gobang;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * WinFrame�� ����frame
 * ��ʤ������ʱ����
 */
public class WinFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JButton again;          // ����һ��
	JButton exit;           // �˳���Ϸ
	private JLabel winner;  // ��ʤ��
	
	private static String whitewin = "�����ʤ��";
	private static String blackwin = "�����ʤ��";
	private static String draw = "ƽ�֣�";
	
	/**
	 * ������ ��ʼ��
	 */
	public WinFrame () {
		// ��������
		setTitle("win");
		setSize(Tool.WINWIDTH, Tool.WINHEIGHT);
		setLocation(Tool.WINLOCATION);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// ���ñ���
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {   
		        g.drawImage(Tool.winframe, 0, 0, getSize().width, getSize().height, this);  
			}	
	    } ;
		panel.setOpaque(false);
	    
		// ʤ����
		winner = new JLabel();
		winner.setFont(Tool.getFont(Tool.fontkai, Font.BOLD, 36));
		
		// ����һ��
		again = new JButton("����һ��");	
		Tool.initComponent(again, Tool.getFont(Tool.fontkai, Font.PLAIN, 20));
		again.setIcon(new ImageIcon(Tool.blackChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT)));
		
		// �˳���Ϸ
		exit = new JButton("�˳���Ϸ");
		Tool.initComponent(exit, Tool.getFont(Tool.fontkai, Font.PLAIN, 20));
		exit.setIcon(new ImageIcon(Tool.whiteChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT)));
		
		// ���ò���
		
		add(panel);
		
		panel.setLayout(new GridBagLayout());
		panel.add(winner, new GBC(0, 0, 2, 1).setInsets(15));
		panel.add(again, new GBC(0, 1).setInsets(10));
		panel.add(exit, new GBC(1, 1).setInsets(10));
	}
	
	/**
	 * ����ʤ����
	 * @param step ����
	 */
	public void setWinner (int step) {
		// ���ݲ�����ż���ж�ʤ����
		if (step % 2 == 1)
			winner.setText(whitewin);
		else
			winner.setText(blackwin);
	}
	
	/**
	 * ƽ��ʱ
	 */
	public void setDraw() {
		winner.setText(draw);
	}
}
