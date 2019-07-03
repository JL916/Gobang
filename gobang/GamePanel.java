package gobang;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GamePanel�� ������Ϸ���
 * ���������Ϸ����ʾ״̬
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel title; // ��Ϸ����
	
	JButton start;	  // ��ʼ��Ϸ��ť
	JButton setting;  // ��Ϸ���ð�ť
	JButton exit;     // �˳���Ϸ��ť
	JButton withdraw; // ���尴ť
	JButton defeat;   // ���䰴ť
	
	private JLabel step;       // ������ǩ
	private JLabel image;      // ͼ��
	private JLabel statusText; // ״̬��ǩ
	
	private JPanel controlPanel; // ������� ����5����ť
	private JPanel statusPanel;  // ״̬��� ����3����ǩ
	
	private ImageIcon blackIcon; // ����ͼ��
	private ImageIcon whiteIcon; // ����ͼ��
	
	/**
	 * ������
	 * ������ʾλ�� ����͸�� ��ʼ�������
	 */
	public GamePanel () {
		setPreferredSize(Tool.PANELDIMENSION); // ��ʾλ��
		setOpaque(false);					   // ����͸��
		
		// ��ʼ��������� �������� ���� �������� ͼ���
		
		title = new JLabel();
		title.setIcon(new ImageIcon(Tool.gobang.getScaledInstance(Tool.TITLEWIDTH, Tool.TTITLEHEIGHT, Image.SCALE_AREA_AVERAGING)));
		
		start = new JButton("��ʼ��Ϸ");
		Tool.initComponent(start, Tool.getFont(Tool.fontkai, Font.PLAIN, 32));
		Tool.addMListener(start, Tool.LARGEBUTTONWIDTH, Tool.LARGEBUTTONHEIGHT);
		
		setting = new JButton("��Ϸ����");
		Tool.initComponent(setting, Tool.getFont(Tool.fontkai, Font.PLAIN, 32));
		Tool.addMListener(setting, Tool.LARGEBUTTONWIDTH,Tool.LARGEBUTTONHEIGHT);
		
		exit = new JButton("�˳���Ϸ");
		Tool.initComponent(exit, Tool.getFont(Tool.fontkai, Font.PLAIN, 32));
		Tool.addMListener(exit, Tool.LARGEBUTTONWIDTH, Tool.LARGEBUTTONHEIGHT);
		
		withdraw = new JButton("����");
		Tool.initComponent(withdraw, Tool.getFont(Tool.fontkai, Font.PLAIN, 24));
		withdraw.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {	
				if (withdraw.isEnabled())
					Tool.setEntered(withdraw, Tool.SMALLBUTTONWIDTH, Tool.SMALLBUTTONHEIGHT);
				super.mouseEntered(arg0);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {	
				if (withdraw.isEnabled()) {
					Tool.setExited(withdraw);
				}
				super.mouseExited(arg0);
			}
		});
		
		defeat = new JButton("����");
		Tool.initComponent(defeat, Tool.getFont(Tool.fontkai, Font.PLAIN, 24));
		Tool.addMListener(defeat, Tool.SMALLBUTTONWIDTH, Tool.SMALLBUTTONHEIGHT);
		
		step = new JLabel();
		step.setFont(Tool.getFont(Tool.fontwei, Font.PLAIN, 16));
		
		image = new JLabel();
		
		statusText = new JLabel();
		statusText.setFont(Tool.getFont(Tool.fontwei, Font.PLAIN, 16));
		
		controlPanel = new JPanel();
		statusPanel = new JPanel();
		
		blackIcon = new ImageIcon(Tool.blackChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT));
		whiteIcon = new ImageIcon(Tool.whiteChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT));
		
		// ���ñ߽粼��
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
		
		// controlPanel�������������
		controlPanel.setLayout(new GridBagLayout());	
		JPanel empty = new JPanel();
		empty.setOpaque(false);
		controlPanel.add(empty, new GBC(0, 0).setInsetsUpandDown(15));
		controlPanel.add(title, new GBC(0, 1, 1, 6).setInsetsUpandDown(30));
		controlPanel.add(start, new GBC(0, 8).setInsetsUpandDown(30));
		controlPanel.add(setting, new GBC(0, 9).setInsetsUpandDown(30));
		controlPanel.add(exit, new GBC(0, 10).setInsetsUpandDown(30));
		controlPanel.add(withdraw, new GBC(0, 11).setInsetsUpandDown(5));
		controlPanel.add(defeat, new GBC(0, 12).setInsetsUpandDown(15));
		controlPanel.add(empty, new GBC(0, 13).setInsetsUpandDown(5));
		controlPanel.setOpaque(false);
		
		// statusPanel����
		statusPanel.setPreferredSize(new Dimension(0, 30));
		statusPanel.add(image);
		statusPanel.add(statusText);
		statusPanel.add(step);
		statusPanel.setOpaque(false);
	}
	
	/**
	 * ����״̬��UI�ͻ���״̬
	 * @param i ����
	 */
	public void updateUI(int i) {
		switch (i % 2) {
			case 1 : statusText.setText("����"); image.setIcon(blackIcon); break;
			case 0 : statusText.setText("����"); image.setIcon(whiteIcon); break;
		}
		step.setText("�� " + i + " ��");
		// ��һ��δ�� ���ܻ���
		if (i == 1 || i == 2)
			withdraw.setEnabled(false);
		else
			withdraw.setEnabled(true);
	}
	
	/**
	 * ��Ϸ��屳��
	 */
	@Override
	protected void paintComponent(Graphics g) {   
        g.drawImage(Tool.background, 0, 0, getSize().width, getSize().height, this);  
    } 
}
