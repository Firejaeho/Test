public class Intersection{
	private Point p;
	private Point[] pArray = new Point[1000];
	private int count;
	
	public Intersection(Point p){
		this.p = new Point();
		this.p.setP(p);
		for(int i=0; i<1000; i++) pArray[i] = new Point();
		count = 0;
	}
	public int getCount() {	return count;	}
	public Point getP() {	return p;	}
	public void setCount(int count) {	this.count = count;	}
	// �ߺ��Ǵ� Point ���� �ƴ϶�� Point �迭�� Point�� ������ �д�.
	public void counting(Point ratP) {		
		if(!isInPoint(ratP)){
			pArray[count].setP(ratP);
			count++;						
		}
	}
	// ���� ���ͼ������� ���� �̵��� ���� ������ ������ �ΰ�, ������ ���� ��� ���¸� ������ �� ���� ���·� �����Ѵ�.
	public void setLoadPrio(Map map) {
		for(int i=0; i<count; i++)
			map.setCurrent(pArray[i], states.DontGo);
	}
	// �ߺ��Ǵ� Point��ġ���� �˻��Ѵ�.
	public boolean isInPoint(Point ratP){
		for(int i=0; i<count; i++){
			if((pArray[i].getX() == ratP.getX()) && (pArray[i].getY() == ratP.getY()))
				return true;
		}
		return false;
	}
}
