import java.io.IOException;
import java.util.Random;
import java.util.Stack;

public class FindMaze {
	private MapState[] storeRatsRoad = new MapState[4];											// �ֺ� �ε� ������ ������ �ʵ�
	private final Random randomNumbers = new Random();											// random number�� ����� �ʵ�
	private Stack<Intersection> intersection = new Stack<Intersection>();						// �����θ� ������ Stack
	
	// �̷�ã�� ����
	public void start( String Mazefile ) throws IOException {
		Map map = new Map();										// �̷θ� �����
		map.readFile( Mazefile );									// �̷θ� �д´�.
		Rat ratrat = new Rat();										// �㸦 �����
		ratrat.setEnergy(map.getRow(), map.getCol());				// �� ������ ����

		while (!isEnd(map, ratrat))
		{
			mapping(map, ratrat);									// �㸦 �ʿ� �����ϰ� �ֺ� �� ������ ��´�.
			moveRat(map, ratrat, storeRatsRoad);					// �㸦 �����δ�.
			map.setCurrent(ratrat.getP(), states.RAT);				// �㰡 �ӹ��� �ִ� ��ġ�� ���¸� RAT���� ����
			map.printRatOfMap(ratrat);								// �����̴� �㸦 ����Ѵ�.
			// ������ ������� ������, ī��Ʈ�� ���
			if(!intersection.isEmpty())		System.out.println(intersection.peek().getCount()); 
			// ���� �Լ�
			try {	Thread.sleep(10);	} catch(InterruptedException ex) {	Thread.currentThread().interrupt();	}
		}
	}
	//map�� �㸦 mapping
	private void mapping(Map map, Rat rat) {
 		//������ �ʱ�ȭ �Ѵ��� 0:up 1:down 2: left 3:right
 		for(int i=0; i<4; i++)	storeRatsRoad[i]=null;	
		// �ֺ� �ε��� ������ ����
		if(rat.getP().getX() != 0)
			storeRatsRoad[0] = map.getRoadState()[rat.getP().getX()-1][rat.getP().getY()];
		if(rat.getP().getX() != (map.getRow()-1))
			storeRatsRoad[1] = map.getRoadState()[rat.getP().getX()+1][rat.getP().getY()];
		if(rat.getP().getY() != 0)
			storeRatsRoad[2] = map.getRoadState()[rat.getP().getX()][rat.getP().getY()-1];
		if(rat.getP().getY() != (map.getCol()-1))
			storeRatsRoad[3] = map.getRoadState()[rat.getP().getX()][rat.getP().getY()+1];
	}

	//Rat�� �����̴� �Լ�
	private void moveRat(Map map, Rat rat, MapState[] sRRoad) {	
		// �㸦 ������ �� �ʿ��� �پ��� �������� ����
		int[] index = new int[4];																								// �켱���� ���� �迭
		int mini_Prio_Sort = 9999;																								// �켱���� �ּڰ� ���� ����
		int j=0;																												// �켱���� ���� �迭 �ε���
		int interSection=0;																										// ������ �Ǵ� ����
		int isPushInterSection=0;																								// Stack�� ���� �� �ִ� ���������� �Ǵ��ϴ� ����
		
		// ���� ��ġ�� Stack�� �׿��ִ� ��ġ�� ������ Ȯ���ϰ� ������ ������ �����θ� �����Ѵ�.
		if(!intersection.isEmpty())
			if(rat.getP().getX() == intersection.peek().getP().getX() &&
				rat.getP().getY() == intersection.peek().getP().getY())		intersection.pop();
		// �켱������ �ּڰ��� ã�´�.
		for(int i=0; i<4; i++)
			if(sRRoad[i] != null)
				if(sRRoad[i].getPrio() <= mini_Prio_Sort)
					mini_Prio_Sort = sRRoad[i].getPrio();
		// �켱������ �ּڰ��� ���� ���� ���� ���� ã�´�.
		for(int i=0; i<4; i++)
			if(sRRoad[i] != null)
				if(sRRoad[i].getPrio() == mini_Prio_Sort)
					{
						index[j] = i;
						j++;
					}
		// ���� ��ġ�� �� ������ ���� Ȯ���Ѵ�.
		for(int i=0; i<4; i++) {
			if(sRRoad[i] != null) {
				if(sRRoad[i].getState() == states.CanGo || sRRoad[i].getState() == states.Pass)	interSection++;
				if(sRRoad[i].getState() == states.CanGo)										isPushInterSection++;																						// CanGo�� ���� ����� ī��Ʈ�ϴ� ����
			}
		}
		// ���� �ִ� ���� �ѱ��� ���̸�
		if(interSection == 1) {
			map.setCurrent(rat.getP(), states.DontGo);																			// ���� ��ġ�� DontGo ���·� �����Ѵ�.
			for(int i=0; i<4; i++) {
				if(sRRoad[i] != null){	
					// CanGo��
					if(sRRoad[i].getState() == states.CanGo){
						// ã�� ��� �� �������� �Ѱ����� �̵��Ѵ�.
						rat.move(index[randomNumbers.nextInt(j)]);
						// ������ ���� ���� Stack �ʵ尡 ������� ������, �ֱ� ������ Ŭ������ ī��Ʈ�� ++�����ش�.
						if(!intersection.isEmpty()) intersection.peek().counting(rat.getP());		
						break;
					}
					// Pass�� 
					else if(sRRoad[i].getState() == states.Pass){
						//�ڷ���Ʈ ���� ���θ� �˻��ϰ� �ڷ���Ʈ�� �����Ѵ�.
						if(!intersection.isEmpty() && intersection.peek().getCount() >= 25){
							intersection.peek().setLoadPrio(map); 																// ���ÿ� ����� �ֱ� �����ο��� ���� �̵��� ���� ��� Priority �������ش�.
							rat.telePort(intersection.peek().getP());															// Stack�� ����� �ֱ� �����η� �ڷ���Ʈ �Ѵ�.
							intersection.pop();																					// �׸��� �ֱ� �����θ� ������ ������.
							break;					
						}
						// �ڷ���Ʈ�� �������� �ʰ� Pass�� �㸦 ��ĭ �̵���Ų��.
						else {
							// ã�� ��� �� �������� �Ѱ����� �̵��Ѵ�.
							rat.move(index[randomNumbers.nextInt(j)]);
							// ������ ���� ���� Stack �ʵ尡 ������� ������, �ֱ� ������ Ŭ������ ī��Ʈ�� ++�����ش�.
							if(!intersection.isEmpty()) intersection.peek().counting(rat.getP());		
							break;
						}
					}
				}
			}
		}
		// ���� �ִ� ���� ���������̸�
		else {
			// CanGo�� 2�� �̻����� �� �ִ� ������ ���
			if(isPushInterSection >= 2) {																
				Intersection newInter = new Intersection(rat.getP());															// ���ο� ������ ������ ������ ������ Ŭ���� ����
				intersection.push(newInter);																					// intersection Stack�� ������ ������ �����Ѵ�.	
			}
			map.setCurrent(rat.getP(), states.Pass);																			// ���� ��ġ�� Pass ���·� �����Ѵ�.

			// ã�� ��� �� �������� �Ѱ����� �̵��Ѵ�.
			rat.move(index[randomNumbers.nextInt(j)]);
			// ������ ���� ���� Stack �ʵ尡 ������� ������, �ֱ� ������ Ŭ������ ī��Ʈ�� ++�����ش�.
			if(!intersection.isEmpty()) intersection.peek().counting(rat.getP());		
		}
	}
	// ���ᰡ �������� Ȯ��
	private boolean isEnd(Map map, Rat rat) {
		//�������� ��� �����ϰų�, ���� ���� ��ġ�� �ⱸ�̸� ����
		return ((((map.getRow()-1) == rat.getP().getX()) && ((map.getCol()-2) == rat.getP().getY())) || (rat.getEnergy() == 0));
	}
}
