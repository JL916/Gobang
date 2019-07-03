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
 * SettingFrame类 设置frame
 * 点击游戏设置时弹出
 */
public class SettingFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel model;			// 游戏模式
	private JRadioButton PVE;   	// 人机
	private JRadioButton PVP;  	 	// 人人
	
	private JLabel first;      	 	// 先手
	JRadioButton firstP;       	 	// 玩家先手
	JRadioButton firstE;      		// 电脑先手
	
	private JLabel show;			// 显示方式
	private JRadioButton showlast;  // 只标记上一步
	private JRadioButton showall;   // 标记所有棋子(1, 2, 3...)
	
	JButton certain;				// 确认键
	JButton cancel;                 // 取消键
	
	/**
	 * 构造器 初始化
	 */
	public SettingFrame () {
		// 设置属性
		setTitle("setting");
		setSize(Tool.SETTINWIDTH, Tool.SETTINGHEIGHT);
		setLocation(Tool.SETTINGLOCATION);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// 设置背景
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {   
		        g.drawImage(Tool.settingframe, 0, 0, getSize().width, getSize().height, this);  
			}	
	    } ;
		panel.setOpaque(false);
	    
		// 模式选项
		model = new JLabel("模式");
		Tool.initComponent(model, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		PVE = new JRadioButton("人机对战");
		Tool.initComponent(PVE, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		PVP = new JRadioButton("人人对战");
		Tool.initComponent(PVP, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		ButtonGroup bgmodel = new ButtonGroup();
		PVE.setSelected(true);
		bgmodel.add(PVE);
		bgmodel.add(PVP);
		
		// 先手选项
		first = new JLabel("先手");
		Tool.initComponent(first, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		firstP = new JRadioButton("玩家先行");
		Tool.initComponent(firstP, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		firstE = new JRadioButton("电脑先行");
		Tool.initComponent(firstE, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		ButtonGroup bgfirst = new ButtonGroup();
		firstP.setSelected(true);
		bgfirst.add(firstP);
		bgfirst.add(firstE);
		
		// 显示选项
		show = new JLabel("显示");
		Tool.initComponent(show, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		showlast = new JRadioButton("标记一步");
		Tool.initComponent(showlast, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		showall = new JRadioButton("标记所有");
		Tool.initComponent(showall, Tool.getFont(Tool.fontwei, Font.PLAIN, 24));
		ButtonGroup bgshow = new ButtonGroup();
		showlast.setSelected(true);
		bgshow.add(showlast);
		bgshow.add(showall);
		
		// 确认按钮
		certain = new JButton("确定");
		Tool.initComponent(certain, Tool.getFont(Tool.fontkai, Font.PLAIN, 20));
		certain.setIcon(new ImageIcon(Tool.blackChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT)));
		
		// 取消按钮
		cancel = new JButton("取消");
		Tool.initComponent(cancel, Tool.getFont(Tool.fontkai, Font.PLAIN, 20));
		cancel.setIcon(new ImageIcon(Tool.whiteChess.getScaledInstance(Tool.ICONSIZE, Tool.ICONSIZE, Image.SCALE_DEFAULT)));
		
		// 设置布局
		
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
		
		// 选择PVP时禁用先手选项
		PVP.addActionListener((event) -> {
			firstP.setEnabled(false);
			firstE.setEnabled(false);
		});
		// 选择PVE时启用先手选项
		PVE.addActionListener((event) -> {
			firstP.setEnabled(true);
			firstE.setEnabled(true);
		});
	}

	/**
	 * 获取模式
	 */
	public int getModel() {
		return PVP.isSelected() ? Tool.PVP : Tool.PVE;
	}
	
	/**
	 * 获取先手
	 * @return
	 */
	public int getFirst() {
		return firstP.isSelected() ? Tool.FIRSTP : Tool.FIRSTE;
	}
	
	/**
	 * 获取显示
	 * @return
	 */
	public int getShow() {
		return showall.isSelected() ? Tool.SHOWALL : Tool.SHOWLAST;
	}
	
	/**
	 * 设置模式
	 * @param i 模式
	 */
	public void setModel(int i) {
		switch (i) {
			case Tool.PVE : PVE.setSelected(true); break;
			case Tool.PVP : PVP.setSelected(true); break;
		}
	}
	
	/**
	 * 设置先手
	 * @param i 先手
	 */
	public void setFirst(int i) {
		switch (i) {
			case Tool.FIRSTP : firstP.setSelected(true); break;
			case Tool.FIRSTE : firstE.setSelected(true); break;
		}
	}
	
	/**
	 * 设置显示
	 * @param i 显示
	 */
	public void setShow(int i) {
		switch (i) {
			case Tool.SHOWLAST : showlast.setSelected(true); break;
			case Tool.SHOWALL  : showall.setSelected(true);  break;
		}
	}
}
