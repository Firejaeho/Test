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
	// 중복되는 Point 값이 아니라면 Point 배열에 Point를 저장해 둔다.
	public void counting(Point ratP) {		
		if(!isInPoint(ratP)){
			pArray[count].setP(ratP);
			count++;						
		}
	}
	// 현재 인터섹션으로 부터 이동한 길의 정보를 저장해 두고, 저장한 길의 모든 상태를 지나갈 수 없는 상태로 저장한다.
	public void setLoadPrio(Map map) {
		for(int i=0; i<count; i++)
			map.setCurrent(pArray[i], states.DontGo);
	}
	// 중복되는 Point위치인지 검사한다.
	public boolean isInPoint(Point ratP){
		for(int i=0; i<count; i++){
			if((pArray[i].getX() == ratP.getX()) && (pArray[i].getY() == ratP.getY()))
				return true;
		}
		return false;
	}
}
