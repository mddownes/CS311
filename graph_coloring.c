#include<stdio.h>

int main(int argc, char *argv[]){
	if(argc != 2)
	{
		printf("Not Proper amount of Arguments");
	}
	else{
		FILE *input;
		input = fopen(argv[1], "r");
		
		int in;
		while(in !=EOF){
			in = fgetc(input);
			printf("%c",in);
		}
		
	}
	
}