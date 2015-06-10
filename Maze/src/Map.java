import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Map {
	private int[][] m_road;				//��ü Map ����
	private MapState[][] m_road_state;	//���� ǥ�� Map
	private int rows;					//map�� ��
	private int cols;					//map�� ��
	MazeGui mzmp;						// GUI�� Mazemap Class ����
	
	// ������ ��´�. 
	public int getRow ()				{	return rows;	}
	public int getCol ()				{	return cols;	}
	public int[][] getRoad ()			{	return m_road;	}
	public MapState[][] getRoadState ()	{	return m_road_state;	}
	// ������ ���� ǥ���� �ش�.
	public void setCurrent (Point p, states state)	{	m_road_state[p.getX()][p.getY()].setState(state);	}
	
	//map�� ���
	public void printRatOfMap(Rat rat)
	{
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++)
				{
					if(m_road_state[i][j].getState()==states.RAT) {				// �㰡 �ִ� ������ġ��
						System.out.print("*");
						mzmp.setColor(i, j, 0);
						mzmp.getlife(rat.getEnergy());
					}
					else if(m_road_state[i][j].getState()==states.Pass || m_road_state[i][j].getState()==states.DontGo){
						System.out.print(m_road_state[i][j].getPrio()+"");
						mzmp.setColor(i, j, 1);
					}
					else						System.out.print(" ");
				}
			System.out.println();
		}
		System.out.println();
		System.out.println(rat.getEnergy());
	}		
	
	// File Read �Լ�
	public void readFile(String file) throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(file);
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));

		   rows=0;														// ���� �ʱ�ȭ
		   m_road = null;												// ������ �迭 �ʱ�ȭ
		   
		   while ((bf.readLine()) != null)	{	rows++;	}													// ������ ���� �о���δ�.
		    
		   m_road = new int[rows][];									// 2���� �迭 �� �����Ҵ�
		   
		   InputStream is2 = getClass().getClassLoader().getResourceAsStream(file);
		   BufferedReader bf2 = new BufferedReader(new InputStreamReader(is2));
		   String row;

		   int i=0;
		   while ((row = bf2.readLine()) != null) {						// ������ �о����� 0�� �ƴҰ��
    		  String[] arr = row.split(" ");							// �о���� File�� �� ����  Space �������� �߶� String[]�迭�� �����Ѵ�.
    		  cols = arr.length;
 
    		  m_road[i] = new int[cols];								// �о���� ������ �߶� String ũ�⸸ŭ int�� �迭 ����

    		  for (int j = 0; j < cols; j++) {
		    	    try {
		    	        m_road[i][j] = Integer.parseInt(arr[j]);		//�߶� String�� Int������ ��ȯ�Ͽ� ����
		    	    } catch (NumberFormatException nfe) {};
		      }
    		  
		      i++;														// 2���� �迭 ��++
		   }

		   m_road_state = null;														
		   m_road_state = new MapState[rows][cols];						// 2���� �迭 �� �����Ҵ�
		   mzmp = new MazeGui(rows, cols);								// Mazemap Ŭ���� ����
		   for(int k=0; k<rows; k++)
			   for(int j=0; j<cols; j++)	m_road_state[k][j] = new MapState(m_road[k][j]);		// 2���� �迭 �� �����Ҵ�
		 }	
}

