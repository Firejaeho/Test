
public class MapState {
	private states 	state;				//	map 상태
	private int 	Priority;			//	우선순위 변수

	// MapState Default 생성자
	public MapState(int a) {
		switch (a) {
			case 0 :
				this.state = states.CanGo;
				Priority = 0;
				break;
			case 1 :
				this.state = states.Wall;
				Priority = 9999;
				break;
		}
	}
	//	현재 상태 반환
	public states getState()	{	return state;	}
	// 현재 상태 입력값으로 변경
	public void setState(states a) {
		state = a;
		// 현재 상태에 맞게 우선순위도 변경해 준다.
		switch (a) {
			case Wall:
				Priority = 9999;
				break;
			case DontGo:
				Priority = 9999;
				break;
			case RAT:
				break;
			default:
				Priority++;
				break;
		}
	}
	// 현재 우선순위값 반환
	public int getPrio() 		{	return Priority;	}
}
