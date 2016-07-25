
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyJFrame extends JFrame {
	JButton start_butt = new JButton("end");//��ʼ��ť
	JPanel p = new JPanel();
	JTextField label = new JTextField();

	public MyJFrame(String sTitle, int iWidth, int iHeight) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�ߴ�
		setTitle(sTitle);//���ô������
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//���õ��رմ���ʱ�˳�����
		setSize(iWidth, iHeight);//���ô����С
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		label.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F12) {
					final Point mousepoint = MouseInfo.getPointerInfo()
							.getLocation();
					label.setText(mousepoint.x + ":" + mousepoint.y + "\n");
					
				}
			}
		});
		this.setAlwaysOnTop(true);
		start_butt.addActionListener(new ActionListener()//���ÿ�ʼ����
				{
					public Thread t;
					public void actionPerformed(ActionEvent e) {
//						Calendar c = Calendar.getInstance();
						t=new Thread(new MyThread());
//						if(c.get(Calendar.DAY_OF_WEEK) - 1  < 6){
//							System.out.println("BOSS:�����ǹ�����");
//							try {
//								//t.sleep(120 * 1000);
//								t.sleep(100);
//							} catch (InterruptedException e1) {
//								System.out.println("�����쳣������");
//								e1.printStackTrace();
//							}
//							t.start();
//						}else{
//							System.out.println("BOSS:����ǹ�����");
//							t.stop();
//							t.destroy();
//							//p.hide();
//							p.removeAll();
//						}
						
//						System.out.print(start_butt.getText());
//						if (start_butt.getText().equals("start")) {
//							if (Configuration.isTerminated()) {
//								Configuration.setTerminated(false);// ���ò�������Ƿ����û���ֹͣ
//								start_butt.setText("end");
//								t=new Thread(new MyThread());
//								t.start();
//							}
//						} else
						if (start_butt.getText().equals("end")) {
							t.stop();
							t.destroy();
						}
					}
				});
		p.setLayout(new GridLayout(2, 1));
		p.add(label);
		p.add(start_butt);
		// setUndecorated(true);//ȥ���߿�1
		this.setResizable(false);//���ò��ɵ����С
		this.add(p);
		setVisible(true);//��ʾ����
		
		
		Thread t;
			Calendar c = Calendar.getInstance();
			t=new Thread(new MyThread());
			if(c.get(Calendar.DAY_OF_WEEK) - 1  < 6){
				//System.out.println("BOSS:�����ǹ�����");
				try {
					//t.sleep(120 * 1000);
					t.sleep(1000);
				} catch (InterruptedException e1) {
					//System.out.println("�����쳣������");
					e1.printStackTrace();
				}
				t.start();
			}else{
				//System.out.println("BOSS:����ǹ�����");
				t.stop();
				t.destroy();
				//p.hide();
				p.removeAll();
			}
	}

	public static void main(String[] args) {
		Configuration.init();
		MyJFrame mF = new MyJFrame("zzw�İ�����", 100, 100);//��ʼ������
	}
}
