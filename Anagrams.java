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

public class Anagrams{

	//object that has a value calculated by a hash function and the word that created the hash value
	public static class HashObject{
		int value;
		String word;
		//constructor for the class
		public HashObject(int value, String word){
			this.value = value;
			this.word = word;
		}
		//get method for the value
		public int getValue(){
			return value;
		}
		//get method for the word
		public String getWord(){
			return word;
		}
	}

	//calculates a has function based on a mapping to the first 26 primes. The associated prime for each letter is then multiplied together 
	//to form a unique hash value
	public static int hash(char[] word){
		int primes[] = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
		int result = 1;
		for(int i = 0; i < word.length;i++){
			result*= primes[Character.getNumericValue(word[i])-10];
		}
		return result;

	}

	//mergesort is used to sort the hash values to find anagrams next to each other
	public static HashObject[] mergesort(HashObject[] obj){
		//base case for one there is only 1 obj
		if(obj.length <=1){
			return obj;
		}

		//divides into a right and left portion of obj
		HashObject[] right = new HashObject[obj.length/2];
		HashObject[] left = new HashObject[obj.length-obj.length/2];

		//fills in the right and left arrays from obj
		System.arraycopy(obj,0,right,0,right.length);
		System.arraycopy(obj,right.length,left,0,left.length);

		//recursive calls to get down to 1 obj each
		mergesort(right);
		mergesort(left);

		//merges the right and left together to get sorted array
		merge(right,left,obj);
		return obj;
	}

	//merges the inputs together in sorted ordered
	public static void merge(HashObject[] right, HashObject[] left, HashObject[] obj){
		//keeps track of the index of each array to process every element in obj right and left in order
		int rightCount = 0;
		int leftCount = 0;

		//counter to keep track of index for obj
		int i = 0;

		//while right and left both have something left it compares the next number for each one and adds it to obj
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

		//adds what is left over after all the comparisons have been made and adds it to obj leaving a sorted array
		System.arraycopy(right,rightCount,obj,i,right.length-rightCount);
		System.arraycopy(left,leftCount,obj,i,left.length-leftCount);

	}

	public static void main(String[] args){
		//arraylist of HashObjects for each word
		ArrayList<HashObject> hashArray = new ArrayList<HashObject>();
		//used to read in lines from file
		String line;
		//the result of the hash function
		int result;
		//counter for number of anagrams in a file
		int anagramCount = 0;

		//tries to read in the file, on success it calculates the hash value of the word and creates an HashObject to be added to the array list
		 try{
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			while((line = reader.readLine()) != null){
				result = hash(line.toCharArray());
				
				HashObject obj = new HashObject(result,line);
				hashArray.add(obj);
			}
			//converts the array list to array so it is easier to use merge sort
			HashObject objArray[] = new HashObject[hashArray.size()];
			objArray = hashArray.toArray(objArray);
			mergesort(objArray);

			//writes the output to the specified file name given in arguments
			File file = new File(args[1]);
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			//automically adds the first word
			bw.write(objArray[0].getWord());
			//runs through all words and checks for anagrams if the one next to it has the same value
			for(int i = 1; i < objArray.length;i++){
				if(objArray[i].getValue() == objArray[i-1].getValue()){
					bw.write(", ");
					bw.write(objArray[i].getWord());

				}
				else{
					bw.write("\n");
					bw.write(objArray[i].getWord());
					anagramCount++;
				}
			}
			//close the file that was being used
			bw.close();
			//prints out number of anagrams in the file
			System.out.println("Number of Anagrams- " + anagramCount);
		//exception if file cant be read
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}