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
 * GamePanel类 代表游戏面板
 * 负责控制游戏及显示状态
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel title; // 游戏标题
	
	JButton start;	  // 开始游戏按钮
	JButton setting;  // 游戏设置按钮
	JButton exit;     // 退出游戏按钮
	JButton withdraw; // 悔棋按钮
	JButton defeat;   // 认输按钮
	
	private JLabel step;       // 步数标签
	private JLabel image;      // 图标
	private JLabel statusText; // 状态标签
	
	private JPanel controlPanel; // 控制面板 负责5个按钮
	private JPanel statusPanel;  // 状态面板 负责3个标签
	
	private ImageIcon blackIcon; // 黑棋图标
	private ImageIcon whiteIcon; // 白棋图标
	
	/**
	 * 构造器
	 * 设置显示位置 背景透明 初始化组件等
	 */
	public GamePanel () {
		setPreferredSize(Tool.PANELDIMENSION); // 显示位置
		setOpaque(false);					   // 背景透明
		
		// 初始化各个组件 设置内容 字体 鼠标监听器 图标等
		
		title = new JLabel();
		title.setIcon(new ImageIcon(Tool.gobang.getScaledInstance(Tool.TITLEWIDTH, Tool.TTITLEHEIGHT, Image.SCALE_AREA_AVERAGING)));
		
		start = new JButton("开始游戏");
		Tool.initComponent(start, Tool.getFont(Tool.fontkai, Font.PLAIN, 32));
		Tool.addMListener(start, Tool.LARGEBUTTONWIDTH, Tool.LARGEBUTTONHEIGHT);
		
		setting = new JButton("游戏设置");
		Tool.initComponent(setting, Tool.getFont(Tool.fontkai, Font.PLAIN, 32));
		Tool.addMListener(setting, Tool.LARGEBUTTONWIDTH,Tool.LARGEBUTTONHEIGHT);
		
		exit = new JButton("退出游戏");
		Tool.initComponent(exit, Tool.getFont(Tool.fontkai, Font.PLAIN, 32));
		Tool.addMListener(exit, Tool.LARGEBUTTONWIDTH, Tool.LARGEBUTTONHEIGHT);
		
		withdraw = new JButton("悔棋");
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
		
		defeat = new JButton("认输");
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
		
		// 设置边界布局
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
		
		// controlPanel设置网格袋布局
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
		
		// statusPanel布局
		statusPanel.setPreferredSize(new Dimension(0, 30));
		statusPanel.add(image);
		statusPanel.add(statusText);
		statusPanel.add(step);
		statusPanel.setOpaque(false);
	}
	
	/**
	 * 更新状态栏UI和悔棋状态
	 * @param i 步数
	 */
	public void updateUI(int i) {
		switch (i % 2) {
			case 1 : statusText.setText("黑棋"); image.setIcon(blackIcon); break;
			case 0 : statusText.setText("白棋"); image.setIcon(whiteIcon); break;
		}
		step.setText("第 " + i + " 步");
		// 若一步未下 则不能悔棋
		if (i == 1 || i == 2)
			withdraw.setEnabled(false);
		else
			withdraw.setEnabled(true);
	}
	
	/**
	 * 游戏面板背景
	 */
	@Override
	protected void paintComponent(Graphics g) {   
        g.drawImage(Tool.background, 0, 0, getSize().width, getSize().height, this);  
    } 
}
