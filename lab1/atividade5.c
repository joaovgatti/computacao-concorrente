#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define NTHREADS 2
#define N 1000

typedef struct{
	int intervalo[NTHREADS];
	int *vet;
}arg_struct;



void *incrementar_vetor(void *args){
	arg_struct *arg = (arg_struct*) args;
	for(int i = arg->intervalo[0]; i < arg->intervalo[1]; i++){
		arg->vet[i]++;
	}
	free(args);
	pthread_exit(NULL);
}

int* gerarArrayZerado(int *vetor){
	for(int i=0; i< N;i++){
		vetor[i] = i - i;
	}
	return vetor;
}



int main (){

	pthread_t tid[NTHREADS];
	int vetor0[N];

	int* vetor = gerarArrayZerado(vetor0);
	printf("vetor inicial:\n");
	for(int i=0; i<N;i++){
		printf("%d",vetor[i]);
	}
	printf("\n");



	for(int i=0; i< NTHREADS;i++){
		arg_struct* args = malloc(sizeof(arg_struct));
		args->intervalo[0] = N*i/NTHREADS;
		args->intervalo[1] = N*(i+1)/NTHREADS;
		args->vet = vetor;
		if(pthread_create(&tid[i],NULL,incrementar_vetor,(void*) args)){
			printf("Erro ao criar a thread: %li\n",tid[i]);
		}

	}

	for(int i=0; i <NTHREADS;i++){
		if(pthread_join(tid[i],NULL)){
			printf("Erro ao esperar o termino da thread: %li\n",tid[i]);
		}
	}

	printf("vetor final:\n");
	for(int i=0; i < N;i++){
		printf("%d",vetor[i]);
	}
	printf("\n");


	return 0;


}