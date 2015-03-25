//Matthew Downes
//CS311
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class Anagrams{

	public static class HashObject{
		int value;
		String word;
		public HashObject(int value, String word){
			this.value = value;
			this.word = word;
		}
		public int getValue(){
			return value;
		}
		public String getWord(){
			return word;
		}
	}
	public static int hash(char[] word){
		int primes[] = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
		int result = 1;
		for(int i = 0; i < word.length;i++){
			result*= primes[Character.getNumericValue(word[i])-10];
		}
		return result;

	}

	public static HashObject[] mergesort(HashObject[] obj){
		if(obj.length <=1){
			return obj;
		}

		HashObject[] right = new HashObject[obj.length/2];
		HashObject[] left = new HashObject[obj.length-obj.length/2];

		System.arraycopy(obj,0,right,0,right.length);
		System.arraycopy(obj,right.length,left,0,left.length);

		mergesort(right);
		mergesort(left);

		merge(right,left,obj);
		return obj;
	}

	public static void merge(HashObject[] right, HashObject[] left, HashObject[] obj){
		int rightCount = 0;
		int leftCount = 0;

		int i = 0;

		while (rightCount < right.length && leftCount < left.length){
			if(right[rightCount].getValue() < left[leftCount].getValue()){
				obj[i] = right[rightCount];
				rightCount++;
			}
			else{
				obj[i] = left[leftCount];
				leftCount++;
			}
			i++;
		}

		System.arraycopy(right,rightCount,obj,i,right.length-rightCount);
		System.arraycopy(left,leftCount,obj,i,left.length-leftCount);

	}

	public static void main(String[] args){
		ArrayList<HashObject> hashArray = new ArrayList<HashObject>();
		String line;
		int result;
		 try{
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			while((line = reader.readLine()) != null){
				result = hash(line.toCharArray());
				
				HashObject obj = new HashObject(result,line);
				hashArray.add(obj);
			}
			HashObject objArray[] = new HashObject[hashArray.size()];
			objArray = hashArray.toArray(objArray);
			mergesort(objArray);

			File file = new File(args[1]);
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(objArray[0].getWord());
			for(int i = 1; i < objArray.length;i++){
				if(objArray[i].getValue() == objArray[i-1].getValue()){
					bw.write("  -  ");
					bw.write(objArray[i].getWord());

				}
				else{
					bw.write("\n");
					bw.write(objArray[i].getWord());
				}
			}
			bw.close();

		}catch(Exception e){
			e.printStackTrace();
		}



	}

}