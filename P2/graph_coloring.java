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

	//stores the edges in an adjacency matrix
	static int adjMatrix[][];
	//used in DFS to keep track of which vertices have been visisted
	static boolean marked[];
	//variable to determine if is two colorable
	static boolean twoColor = true;
	//special counter to keep track of how long the cycle is
	static int count;

	//gets the adjacent nodes of a vertex from adjacency list
	public static int[] getAdjVertices(int v){
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

	//determines if the graph is two colorable
	public static void twoColorable(int v, int u,int count){
		//marks the vertex as visited
		marked[v] = true;
		//runs through each vertex of the adjacency list
		for(int w : getAdjVertices(v)){
			//if the vertex is not 0 and it is not the predecessor of this vertex(since is is undirected)
			if(w != 0 && w != u){
				//if it is not marked then mark it and run DFS on that vertex
				if(!marked[w]){
					marked[w] = true;
					count++;
					twoColorable(w,v,count);
				//if it is marked then check to see if it is an even cycle since it wont count 
				//the last edge making it an odd cycle back to the source vertex.
				} else if(v != u && count >= 2 && count %2 ==0){
					twoColor = false;
					return;
				}
			}
		}
	}


	public static void main(String[] args){
		 try{
		 	//used to read in from file
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			//keeps track of the current line in the file
			String line;
			//the current vertex to be added
			int vertex = 0;
			//the current edge to be added
			int edge = 0;
			//used to get the numbers from the file
			String output = "";
			//char array of the line so it can be processed into int
			char[] ar;
			//gets the number of vertices from the first line of the file
			int n = Integer.parseInt(reader.readLine());
			//sets the size of the adjacency matrix
			adjMatrix = new int[n+1][n+1];
			//counter to keep track of the very first vertex
			int count = 0;
			//keeps track of what the first vertex was. 
			int firstVertex = 0;
			//makes a marked array based on the number of vertices
			marked = new boolean[n];

			//reads the file line by line
			while((line = reader.readLine()) != null){

				count++;
				//converts the line to a char array to be processed
				ar = line.toCharArray();
				//runs through the char arry to get the vertex and edge
				for(int i = 0; i < ar.length;i++){
					if(ar[i] == ' ' || ar[i]== '\n'){
						vertex = Integer.parseInt(output);
						output = "";
					}
					else
						output+=ar[i];
				}
				edge = Integer.parseInt(output);
				//adds the edge to the adjacency matrix
				adjMatrix[vertex][edge] =1;
				adjMatrix[edge][vertex] =1;
				//keep track of the source vertex based on the first one
				if(count == 1)
					firstVertex = vertex;

				output = "";		
			}

			//Determine if the graph is two colorable or not
			twoColorable(firstVertex,0,0);
			if(twoColor == true){
				System.out.println("Graph is two-colorable");
			}
			else if(twoColor == false){
				System.out.println("graph is not two-colorable");
			}

			}catch(Exception e){
				e.printStackTrace();
		}
	}
}