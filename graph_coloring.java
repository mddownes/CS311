//Matthew Downes
//CS311

//import statements to read in file
import java.io.FileReader; 
import java.io.BufferedReader;
//import statements to wrtie output to file
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
//import statement to use array lists as one of my data structures
import java.util.ArrayList;



public class graph_coloring{

	static int adjMatrix[][];
	// public class Graph{
	// 	public int[][] nodes = adjMatrix;

	// 	public int[] getAdjNodes(int v){
	// 		return nodes[v];
	// 	}
	// 	public int vertices(){
	// 		return nodes.length;
	// 	}
	// }

	static boolean marked[] = new boolean[10];
	static int s;
	static boolean hasCycle;
	static int count;

	public static int[] getAdjNodes(int v){
		int[] tmp = new int[10];
		int index = 0;
		for(int i = 0; i<10;i++){
			if(adjMatrix[v][i] == 1){
				tmp[index] = i;
				index++;
			}
		}
		return tmp;
	}

	public static void findCycle(int v, int u,int count){
		marked[v] = true;
		for(int w : getAdjNodes(v)){
			if(w != 0 && w != u){
				if(!marked[w]){
					marked[w] = true;
					count++;
					findCycle(w,v,count);
				} else if(v != u && count >= 3 && count %2 ==0){
					System.out.println(v);
					System.out.println(u);
					System.out.println(count);
					hasCycle = true;
					return;
					
				}
			}
		}
	}




	// static int visited[];
	
	// public static void DFS(int i){
	// 	int j;
	// 	System.out.println(i);
	// 	visited[i] = 1;
	// 	for(j = 1; j< 10;j++){
	// 		if(visited[j] == 0 && adjMatrix[i][j] ==1)
	// 			DFS(j);
	// 	}
	// }

	public static void main(String[] args){
		 try{
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String line;
			int vertex = 0;
			int edge = 0;
			String output = "";




	

			char[] ar;
			int n = Integer.parseInt(reader.readLine());
			adjMatrix = new int[n+1][n+1];
			int count = 0;
			int firstVertex = 0;


			while((line = reader.readLine()) != null){
				count++;
				ar = line.toCharArray();
				for(int i = 0; i < ar.length;i++){
					if(ar[i] == ' ' || ar[i]== '\n'){
						vertex = Integer.parseInt(output);
						output = "";
					}
					else
						output+=ar[i];
				}
				edge = Integer.parseInt(output);
				adjMatrix[vertex][edge] =1;
				adjMatrix[edge][vertex] =1;
				if(count == 1)
					firstVertex = vertex;

				output = "";		
			}

			findCycle(firstVertex,0,0);

			System.out.println(hasCycle);
			}catch(Exception e){
				e.printStackTrace();
		}
	}
}