
public class Rat {
	
	private int m_Energy;					// 에너지
	private Point p;
	
	public Rat()							// 초기화
	{
		p = new Point(0, 1);
		m_Energy = 0;
	}
	public void setEnergy(int r, int c)		// 초기 에너지 n+m-1 셋팅
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
	//변수를 얻는 함수
	public int getEnergy ()
	{
		return m_Energy;
	}
	public Point getP() {
		return p;
	}
}
