import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class FindMaze {
	private MapState[] storeRatsRoad = new MapState[4];											// 주변 로드 정보를 저장할 필드
	private final Random randomNumbers = new Random();											// random number를 사용할 필드
	private Stack<Intersection> intersection = new Stack<Intersection>();						// 교차로를 저장할 Stack
	
	// 미로찾기 시작
	public void start( String Mazefile ) throws IOException {
		Map map = new Map();										// 미로를 만들고
		map.readFile( Mazefile );									// 미로를 읽는다.
		Rat ratrat = new Rat();										// 쥐를 만들고
		ratrat.setEnergy(map.getRow(), map.getCol());				// 쥐 에너지 생성

		while (!isEnd(map, ratrat))
		{
			mapping(map, ratrat);									// 쥐를 맵에 맵핑하고 주변 길 정보를 얻는다.
			moveRat(map, ratrat, storeRatsRoad);					// 쥐를 움직인다.
			map.setCurrent(ratrat.getP(), states.RAT);				// 쥐가 머물고 있는 위치의 상태를 RAT으로 변경
			map.printRatOfMap(ratrat);								// 움직이는 쥐를 출력한다.
			// 스택이 비어있지 않으면, 카운트를 출력
			if(!intersection.isEmpty())		System.out.println(intersection.peek().getCount()); 
			// 지연 함수
			try {	Thread.sleep(10);	} catch(InterruptedException ex) {	Thread.currentThread().interrupt();	}
		}
	}
	//map에 쥐를 mapping
	private void mapping(Map map, Rat rat) {
 		//벽으로 초기화 한다음 0:up 1:down 2: left 3:right
 		for(int i=0; i<4; i++)	storeRatsRoad[i]=null;	
		// 주변 로드의 정보를 저장
		if(rat.getP().getX() != 0)
			storeRatsRoad[0] = map.getRoadState()[rat.getP().getX()-1][rat.getP().getY()];
		if(rat.getP().getX() != (map.getRow()-1))
			storeRatsRoad[1] = map.getRoadState()[rat.getP().getX()+1][rat.getP().getY()];
		if(rat.getP().getY() != 0)
			storeRatsRoad[2] = map.getRoadState()[rat.getP().getX()][rat.getP().getY()-1];
		if(rat.getP().getY() != (map.getCol()-1))
			storeRatsRoad[3] = map.getRoadState()[rat.getP().getX()][rat.getP().getY()+1];
	}

	//Rat을 움직이는 함수
	private void moveRat(Map map, Rat rat, MapState[] sRRoad) {	
		// 쥐를 움직일 때 필요한 다양한 변수들의 셋팅
		int[] index = new int[4];																								// 우선순위 저장 배열
		int mini_Prio_Sort = 9999;																								// 우선순위 최솟값 저장 변수
		int j=0;																												// 우선순위 저장 배열 인덱스
		int interSection=0;																										// 갈래길 판단 변수
		int isPushInterSection=0;																								// Stack에 쌓일 수 있는 교차로인지 판단하는 변수
		
		// 현재 위치가 Stack에 쌓여있는 위치와 같은지 확인하고 같으면 스택의 교차로를 제거한다.
		if(!intersection.isEmpty())
			if(rat.getP().getX() == intersection.peek().getP().getX() &&
				rat.getP().getY() == intersection.peek().getP().getY())		intersection.pop();
		// 우선순위의 최솟값을 찾는다.
		for(int i=0; i<4; i++)
			if(sRRoad[i] != null)
				if(sRRoad[i].getPrio() <= mini_Prio_Sort)
					mini_Prio_Sort = sRRoad[i].getPrio();
		// 우선순위의 최솟값과 같은 값을 가진 길을 찾는다.
		for(int i=0; i<4; i++)
			if(sRRoad[i] != null)
				if(sRRoad[i].getPrio() == mini_Prio_Sort)
					{
						index[j] = i;
						j++;
					}
		// 현재 위치가 몇 갈래길 인지 확인한다.
		for(int i=0; i<4; i++) {
			if(sRRoad[i] != null) {
				if(sRRoad[i].getState() == states.CanGo || sRRoad[i].getState() == states.Pass)	interSection++;
				if(sRRoad[i].getState() == states.CanGo)										isPushInterSection++;																						// CanGo의 길은 몇개인지 카운트하는 변수
			}
		}
		// 갈수 있는 길이 한군데 뿐이면
		if(interSection == 1) {
			map.setCurrent(rat.getP(), states.DontGo);																			// 현재 위치를 DontGo 상태로 변경한다.
			for(int i=0; i<4; i++) {
				if(sRRoad[i] != null){	
					// CanGo면
					if(sRRoad[i].getState() == states.CanGo){
						// 찾은 길들 중 랜덤으로 한곳으로 이동한다.
						rat.move(index[randomNumbers.nextInt(j)]);
						// 교차로 정보 저장 Stack 필드가 비어있지 않으면, 최근 교차로 클래스의 카운트를 ++시켜준다.
						if(!intersection.isEmpty()) intersection.peek().counting(rat.getP());		
						break;
					}
					// Pass면 
					else if(sRRoad[i].getState() == states.Pass){
						//텔레포트 가능 여부를 검사하고 텔레포트를 수행한다.
						if(!intersection.isEmpty() && intersection.peek().getCount() >= 25){
							intersection.peek().setLoadPrio(map); 																// 스택에 저장된 최근 교차로에서 부터 이동한 길을 모두 Priority 조정해준다.
							rat.telePort(intersection.peek().getP());															// Stack에 저장된 최근 교차로로 텔레포트 한다.
							intersection.pop();																					// 그리고 최근 교차로를 꺼내서 버린다.
							break;					
						}
						// 텔레포트가 가능하지 않고 Pass면 쥐를 한칸 이동시킨다.
						else {
							// 찾은 길들 중 랜덤으로 한곳으로 이동한다.
							rat.move(index[randomNumbers.nextInt(j)]);
							// 교차로 정보 저장 Stack 필드가 비어있지 않으면, 최근 교차로 클래스의 카운트를 ++시켜준다.
							if(!intersection.isEmpty()) intersection.peek().counting(rat.getP());		
							break;
						}
					}
				}
			}
		}
		// 갈수 있는 길이 여러군데이면
		else {
			// CanGo가 2길 이상으로 나 있는 교차로 라면
			if(isPushInterSection >= 2) {																
				Intersection newInter = new Intersection(rat.getP());															// 새로운 교차로 정보를 저장할 교차로 클래스 생성
				intersection.push(newInter);																					// intersection Stack에 교차로 정보를 저장한다.	
			}
			map.setCurrent(rat.getP(), states.Pass);																			// 현재 위치를 Pass 상태로 변경한다.

			// 찾은 길들 중 랜덤으로 한곳으로 이동한다.
			rat.move(index[randomNumbers.nextInt(j)]);
			// 교차로 정보 저장 Stack 필드가 비어있지 않으면, 최근 교차로 클래스의 카운트를 ++시켜준다.
			if(!intersection.isEmpty()) intersection.peek().counting(rat.getP());		
		}
	}
	// 종료가 가능한지 확인
	private boolean isEnd(Map map, Rat rat) {
		//에너지를 모두 소진하거나, 쥐의 현재 위치가 출구이면 종료
		return ((((map.getRow()-1) == rat.getP().getX()) && ((map.getCol()-2) == rat.getP().getY())) || (rat.getEnergy() == 0));
	}
}
