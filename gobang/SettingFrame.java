package gobang;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * SettingFrame�� ����frame
 * �����Ϸ����ʱ����
 */
public class SettingFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel model;			// ��Ϸģʽ
	private JRadioButton PVE;   	// �˻�
	private JRadioButton PVP;  	 	// ����
	
	private JLabel first;      	 	// ����
	JRadioButton firstP;       	 	// �������
	JRadioButton firstE;      		// ��������
	
	private JLabel show;			// ��ʾ��ʽ
	private JRadioButton showlast;  // ֻ�����һ��
	private JRadioButton showall;   // �����������(1, 2, 3...)
	
	JButton certain;				// ȷ�ϼ�
	JButton cancel;                 // ȡ����
	
	/**
	 * ������ ��ʼ��
	 */
	public SettingFrame () {
		// ��������
		setTitle("setting");
		setSize(Tool.SETTINWIDTH, Tool.SETTINGHEIGHT);
		setLocation(Tool.SETTINGLOCATION);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// ���ñ���
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {   
		        g.drawImage(Tool.settingframe, 0, 0, getSize().width, getSize().height, this);  
			}	
	    } ;
		panel.setOpaque(false);
	    
		// ģʽѡ��
		model = new JLabel("ģʽ");
		Tool.initComponent(model, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		PVE = new JRadioButton("�˻���ս");
		Tool.initComponent(PVE, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		PVP = new JRadioButton("���˶�ս");
		Tool.initComponent(PVP, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		ButtonGroup bgmodel = new ButtonGroup();
		PVE.setSelected(true);
		bgmodel.add(PVE);
		bgmodel.add(PVP);
		
		// ����ѡ��
		first = new JLabel("����");
		Tool.initComponent(first, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		firstP = new JRadioButton("�������");
		Tool.initComponent(firstP, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		firstE = new JRadioButton("��������");
		Tool.initComponent(firstE, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		ButtonGroup bgfirst = new ButtonGroup();
		firstP.setSelected(true);
		bgfirst.add(firstP);
		bgfirst.add(firstE);
		
		// ��ʾѡ��
		show = new JLabel("��ʾ");
		Tool.initComponent(show, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		showlast = new JRadioButton("���һ��");
		Tool.initComponent(showlast, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		showall = new JRadioButton("�������");
		Tool.initComponent(showall, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		ButtonGroup bgshow = new ButtonGroup();
		showlast.setSelected(true);
		bgshow.add(showlast);
		bgshow.add(showall);
		
		// ȷ�ϰ�ť
		certain = new JButton("ȷ��");
		Tool.initComponent(certain, Tool.getFont(Tool.fontkai, Font.PLAIN, 20));
		certain.setIcon(new ImageIcon(Tool.blackChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT)));
		
		// ȡ����ť
		cancel = new JButton("ȡ��");
		Tool.initComponent(cancel, Tool.getFont(Tool.fontkai, Font.PLAIN, 20));
		cancel.setIcon(new ImageIcon(Tool.whiteChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT)));
		
		// ���ò���
		
		add(panel);
		
		panel.setLayout(new GridBagLayout());
		panel.add(model, new GBC(0, 0).setInsets(5));
		panel.add(PVE, new GBC(1, 0).setInsets(5));
		panel.add(PVP, new GBC(2, 0).setInsets(5));
		panel.add(first, new GBC(0, 1).setInsets(5));
		panel.add(firstP, new GBC(1, 1).setInsets(5));
		panel.add(firstE, new GBC(2, 1).setInsets(5));
		panel.add(show, new GBC(0, 2).setInsets(5));
		panel.add(showlast, new GBC(1, 2).setInsets(5));
		panel.add(showall, new GBC(2, 2).setInsets(5));
		panel.add(certain, new GBC(1, 3).setInsets(5));
		panel.add(cancel, new GBC(2, 3).setInsets(5));
		
		// ѡ��PVPʱ��������ѡ��
		PVP.addActionListener((event) -> {
			firstP.setEnabled(false);
			firstE.setEnabled(false);
		});
		// ѡ��PVEʱ��������ѡ��
		PVE.addActionListener((event) -> {
			firstP.setEnabled(true);
			firstE.setEnabled(true);
		});
	}

	/**
	 * ��ȡģʽ
	 */
	public int getModel() {
		return PVP.isSelected() ? Tool.PVP : Tool.PVE;
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public int getFirst() {
		return firstP.isSelected() ? Tool.FIRSTP : Tool.FIRSTE;
	}
	
	/**
	 * ��ȡ��ʾ
	 * @return
	 */
	public int getShow() {
		return showall.isSelected() ? Tool.SHOWALL : Tool.SHOWLAST;
	}
	
	/**
	 * ����ģʽ
	 * @param i ģʽ
	 */
	public void setModel(int i) {
		switch (i) {
			case Tool.PVE : PVE.setSelected(true); break;
			case Tool.PVP : PVP.setSelected(true); break;
		}
	}
	
	/**
	 * ��������
	 * @param i ����
	 */
	public void setFirst(int i) {
		switch (i) {
			case Tool.FIRSTP : firstP.setSelected(true); break;
			case Tool.FIRSTE : firstE.setSelected(true); break;
		}
	}
	
	/**
	 * ������ʾ
	 * @param i ��ʾ
	 */
	public void setShow(int i) {
		switch (i) {
			case Tool.SHOWLAST : showlast.setSelected(true); break;
			case Tool.SHOWALL  : showall.setSelected(true);  break;
		}
	}
}
