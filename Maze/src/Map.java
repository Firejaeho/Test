import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Map {
	private int[][] m_road;				//전체 Map 변수
	private MapState[][] m_road_state;	//상태 표시 Map
	private int rows;					//map의 행
	private int cols;					//map의 열
	MazeGui mzmp;						// GUI용 Mazemap Class 선언
	
	// 변수를 얻는다. 
	public int getRow ()				{	return rows;	}
	public int getCol ()				{	return cols;	}
	public int[][] getRoad ()			{	return m_road;	}
	public MapState[][] getRoadState ()	{	return m_road_state;	}
	// 지나간 길을 표시해 준다.
	public void setCurrent (Point p, states state)	{	m_road_state[p.getX()][p.getY()].setState(state);	}
	
	//map을 출력
	public void printRatOfMap(Rat rat)
	{
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++)
				{
					if(m_road_state[i][j].getState()==states.RAT) {				// 쥐가 있는 현재위치면
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
	
	// File Read 함수
	public void readFile(String file) throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(file);
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));

		   rows=0;														// 열값 초기화
		   m_road = null;												// 이차원 배열 초기화
		   
		   while ((bf.readLine()) != null)	{	rows++;	}													// 파일의 행을 읽어들인다.
		    
		   m_road = new int[rows][];									// 2차원 배열 행 동적할당
		   
		   InputStream is2 = getClass().getClassLoader().getResourceAsStream(file);
		   BufferedReader bf2 = new BufferedReader(new InputStreamReader(is2));
		   String row;

		   int i=0;
		   while ((row = bf2.readLine()) != null) {						// 한줄을 읽었을때 0이 아닐경우
    		  String[] arr = row.split(" ");							// 읽어들인 File의 한 줄을  Space 기준으로 잘라서 String[]배열에 저장한다.
    		  cols = arr.length;
 
    		  m_road[i] = new int[cols];								// 읽어들인 한줄을 잘라낸 String 크기만큼 int형 배열 생성

    		  for (int j = 0; j < cols; j++) {
		    	    try {
		    	        m_road[i][j] = Integer.parseInt(arr[j]);		//잘라낸 String을 Int형으로 변환하여 저장
		    	    } catch (NumberFormatException nfe) {};
		      }
    		  
		      i++;														// 2차원 배열 행++
		   }

		   m_road_state = null;														
		   m_road_state = new MapState[rows][cols];						// 2차원 배열 행 동적할당
		   mzmp = new MazeGui(rows, cols);								// Mazemap 클래스 생성
		   for(int k=0; k<rows; k++)
			   for(int j=0; j<cols; j++)	m_road_state[k][j] = new MapState(m_road[k][j]);		// 2차원 배열 열 동적할당
		 }	
}

