
public class Rat {
	
	private int m_Energy;					// ������
	private Point p;
	
	public Rat()							// �ʱ�ȭ
	{
		p = new Point(0, 1);
		m_Energy = 0;
	}
	public void setEnergy(int r, int c)		// �ʱ� ������ n+m-1 ����
	{
		m_Energy = r*c*2;//r*c*2;
	}
	public void move(int n)
	{
		p.setPoint(n);
		m_Energy--;
	}
	public void telePort(Point p)
	{
		this.p.setP(p);
		m_Energy -= 25;		
	}
	//������ ��� �Լ�
	public int getEnergy ()
	{
		return m_Energy;
	}
	public Point getP() {
		return p;
	}
}
