
public class MapState {
	private states 	state;				//	map ����
	private int 	Priority;			//	�켱���� ����

	// MapState Default ������
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
	//	���� ���� ��ȯ
	public states getState()	{	return state;	}
	// ���� ���� �Է°����� ����
	public void setState(states a) {
		state = a;
		// ���� ���¿� �°� �켱������ ������ �ش�.
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
	// ���� �켱������ ��ȯ
	public int getPrio() 		{	return Priority;	}
}
