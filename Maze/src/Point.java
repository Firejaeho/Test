public class Point {
	private int x;
	private int y;

	public Point() {
		this.x = 0;
		this.y = 0;
	}
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() 			{	return x;	}
	public void setX(int x) 	{	this.x = x;	}
	public int getY() 			{	return y;	}
	public void setY(int y) 	{	this.y = y;	}
	public void setP(Point p) {
		this.x = p.getX();
		this.y = p.getY();		
	}	
	public void setPoint(int move){
		switch(move) {
			case 0:								//move up
				x--;
				break;
			case 1:								//move down
				x++;		
				break;
			case 2:								//move left
				y--;
				break;
			case 3:								//move right
				y++;
				break;			
		}		
	}
}
