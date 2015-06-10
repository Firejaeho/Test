import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MazeGui extends JFrame{
	private int row;
	private int col;
	JTextField txtField1 = new JTextField("");
	JTextField txtField2 = new JTextField("");
	JPanel jp = new JPanel();
	JButton[][] jbtn;

	public void setColor(int a,int b,int c)
	{
		if(c==0)
			jbtn[a][b].setBackground(new Color(255,0,0));
		else if(c==1)
			jbtn[a][b].setBackground(new Color(255,255,0));
	}
	public void setMaprc(int a,int b){
		 this.row = a;
		 this.col = b;
		 jbtn = new JButton[row][col];
		 txtField1.setText(row + "," + col);
	}
	public void getlife(int a){
		txtField2.setText("현재 남은 파워는 " + a + " 이다.");
	}
	public MazeGui() {
		super("Maze");
		add(txtField1, "North");
		add(txtField2, "South"); 
		add(jp, "Center");
		jp.setLayout(new GridLayout(200,1));
	}
	public MazeGui(int a,int b) {
		super("Maze");
		add(txtField1, "North"); 
		add(txtField2, "South"); 
		add(jp, "Center");
		jp.setLayout(new GridLayout(a,1));
		setMaprc(a,b);
		for (int i = 0; i < row; i++) {
			 for (int j=0;j<col;j++){
				 jp.add(jbtn[i][j] = new JButton()); 
				 jbtn[i][j].setBackground(new Color(0,0,0));
			 }
		 }
		 setSize(800,800);
		 super.setVisible(true);
		 super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}	
}