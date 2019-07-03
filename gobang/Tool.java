package gobang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

/**
 * Tool�� ��̬������
 * ֻ������̬��;�̬����
 */
public class Tool {
	
	static Gobang game;  	// Gobangʵ��
	
	static final int ROWNUM = 15;			// ����
	static final int COLNUM = 15;   		// ����
	static final int BOARDSIZE = 750;  		// ���̴�С
	static final int MARGIN = 25; 			// ���̱߾�
	static final int CELL = 50;				// ÿ�����Ӵ�С
	static final int CHESSSIZE = 45;	    // ����ͼƬ��С
	static final int BOARDPOINTSIZE = 10;   // ���̵��С(4����+�����)
	static final int ICONSIZE = 15;			// ͼ���С(��ť�Աߵĺڰ�����)
	static final int LARGEBUTTONWIDTH = 155;// ��ť�Ŀ�(��ʼ��Ϸ...)
	static final int LARGEBUTTONHEIGHT = 36;// ��ť�ĸ�
	static final int SMALLBUTTONWIDTH = 75; // С��ť�Ŀ�(����...)
	static final int SMALLBUTTONHEIGHT = 24;// С��ť�ĸ�
	static final int GAMEWIDTH = 950;		// �����ڵĿ�
	static final int GAMEHEIGHT = 800;      // �����ڵĸ�
	static final Dimension PANELDIMENSION = new Dimension(180, 0); 	// ��Ϸ�����ʾλ��
	static final Point GAMELOCATION = new Point(500, 100);          // ��������ʾλ��
	static final Point WINLOCATION = new Point(710, 420);			// ʤ��������ʾλ��
	static final Point SETTINGLOCATION = new Point(710, 420);		// ���ô�����ʾλ��
	static final int SETTINWIDTH = 400;		// ���ô��ڿ�
	static final int SETTINGHEIGHT = 200;   // ���ô��ڸ�
	static final int WINWIDTH = 300;		// ʤ�����ڿ�
	static final int WINHEIGHT = 180;       // ʤ�����ڸ�
	static final int TITLEWIDTH = 100;      // ����ͼƬ��
	static final int TTITLEHEIGHT = 300;	// ����ͼƬ��
	static final int RECTSIZE = 10;			// ���α�ǳߴ�
	static final int NUMOFFSETX = -5;		// ���ֱ��x��ƫ����
	static final int NUMOFFSETY = 5;		// ���ֱ��y��ƫ����
	static final int FONTSIZE = 20;         // ���ֱ���ֺ�
	
	static final int PVE = 0;		// PVE����Ϊ0
	static final int PVP = 1;       // PVP����Ϊ1
	static int model;				// ģʽ����
	static final int FIRSTP = 0;	// ������ֶ���Ϊ0
	static final int FIRSTE = 1;    // �������ֶ���Ϊ1
	static int first;               // ���ֱ���
	static final int SHOWLAST = 0;  // ֻ��ʾ��һ������Ϊ0
	static final int SHOWALL = 1;   // ��ʾ���ж���Ϊ1
	static int show;				// ��ʾ����
	static boolean reviewflag;		// ���̱�־
	
	static String fontwei = "res\\STXINWEI.TTF";  // ������κ����·��
	static String fontkai = "res\\STXINGKA.TTF";  // �����п�����·��
	
	static Image blackChess = new ImageIcon("res\\black.png").getImage();  			// ����ͼƬ
	static Image whiteChess = new ImageIcon("res\\white.png").getImage();  			// ����ͼƬ
	static Image select = new ImageIcon("res\\select.png").getImage();	   			// ��ť��ѡ��ͼƬ
	static Image board = new ImageIcon("res\\board.jpg").getImage();	  			// ���̱���
	static Image settingframe = new ImageIcon("res\\settingframe.png").getImage();  // ���ñ���
	static Image winframe = new ImageIcon("res\\winframe.png").getImage();			// ��ʤ����
	static Image background = new ImageIcon("res\\background.png").getImage();		// ��屳��
	static Image gobang = new ImageIcon("res\\gobang.png").getImage();				// ���������ͼƬ
	
	/**
	 * ������
	 * @param gobang Gobang��ʵ��
	 */
	public Tool(Gobang gobang) {
		game = gobang;
	}
	
	/**
	 * ��ʼ����� ���ñ���͸�� �ޱ߿� ����
	 * @param x ���
	 * @param f ����
	 */
	static void initComponent (JComponent x, Font f) {
		x.setBackground(new Color(255, 255, 255));
		x.setFont(f);
		x.setOpaque(false);
		x.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
	}
	
	/**
	 * ���ð�ť��ѡ��Ч��
	 * @param b ��ť
	 * @param width ͼƬ��
	 * @param height ͼƬ��
	 */
	static void setEntered (JButton b, int width, int height) {
		b.setIcon(new ImageIcon(Tool.select.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
		b.setHorizontalTextPosition(SwingConstants.CENTER);
		b.setForeground(Color.WHITE);  // ������ɫ
	}
	
	/**
	 * ���ð�ťδѡ��Ч��
	 * @param b ��ť
	 */
	static void setExited (JButton b) {
		b.setIcon(null);
		b.setForeground(Color.BLACK);  // ������ɫ
	}

	/**
	 * Ϊ��ť����������� ʵ��ѡ��Ч��
	 * @param b ��ť
	 * @param width ͼƬ��
	 * @param height ͼƬ��
	 */
	static void addMListener(JButton b, int width, int height) {
		b.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {	
				Tool.setEntered(b, width, height);
				super.mouseEntered(arg0);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {	
				Tool.setExited(b);
				super.mouseExited(arg0);
			}
		});
	}
	
	/**
	 * ��ȡ����
	 * @param path ����·��
	 * @param type ��������(plain,bold...)
	 * @param size �����С
	 * @return ��������
	 */
	static Font getFont(String path, int type, int size) {
		Font font = null;
		File file = new File(path);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, file);
			font = font.deriveFont(type, size);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return font;
	}
	
	/**
	 * ��Ϊ��һ������ӷ��α��
	 * @param g ����
	 * @param r ��¼
	 */
	private static void showlast (Graphics2D g, ArrayList<Record> r) {		
		Record last = r.get(r.size()-1);
		if (last == null)
			return ;
		
		int x = last.getI();
		int y = last.getJ();
		Color color = last.getColor().equals(Color.WHITE) ? Color.DARK_GRAY : Color.LIGHT_GRAY;
		g.setColor(color);
		g.setStroke(new BasicStroke(2.0f));
		g.drawRect(CELL * y - RECTSIZE / 2 + MARGIN, CELL * x - RECTSIZE / 2 + MARGIN, RECTSIZE, RECTSIZE);
	}

	/**
	 * Ϊ����������Ӳ������
	 * @param g ����
	 * @param r ��¼
	 */
	private static void showall(Graphics2D g, ArrayList<Record> r) {
		int i = 1;
		g.setFont(getFont(fontwei, Font.BOLD, FONTSIZE));
		int x;
		int y;
		for (Record item : r) {
			Color c = item.getColor().equals(Color.WHITE) ? Color.DARK_GRAY : Color.LIGHT_GRAY;
			g.setColor(c);
			if (i > 99) 	// ��λ��
				x = CELL * item.getJ() + MARGIN + NUMOFFSETX * 3;
			else if (i > 9) // ��λ��
				x = CELL * item.getJ() + MARGIN + NUMOFFSETX * 2;
			else 			// һλ��
				x = CELL * item.getJ() + MARGIN + NUMOFFSETX * 1;
			
			y = CELL * item.getI() + MARGIN + NUMOFFSETY;
			g.drawString(String.valueOf(i++), x, y);
		}
	}
	
	/**
	 * ��ʾ���
	 * @param g ����
	 */
	static void show (Graphics g) {
		if (game.getListener() == null)
			return ;
		ArrayList<Record> r = game.getListener().getRecord();
		if (r == null || r.size() == 0)
			return ;
		
		Graphics2D g2 = (Graphics2D) g;
		if (show == 1 || reviewflag)  // ��ѡ����ʾ���б�ѡ�л���Ϸ��������ʱ
			showall(g2, r);
		else
			showlast(g2, r);
		// ��������UI
		game.getBoard().updateUI(); 
	}

	/**
	 * ͨ����ĺ���������Ӧ���ӵ�����
	 * @param x ������
	 * @return ��
	 */
	static int getj (int x) {
		return (int) Math.round(1.0 * (x - MARGIN) / CELL);
	}

	/**
	 * ͨ���������������Ӧ���ӵ�����
	 * @param y ������
	 * @return ��
	 */
	static int geti (int y) {
		return (int) Math.round(1.0 * (y - MARGIN * 2) / CELL);
	}
}
