//
// Created by joaovgatti on 4/8/21.
//

#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include<pthread.h>
#include "timer.h"

int numberOfThreads;
long long int numberOfElements;

/*
 * As três funções abaixo estão relacionadas com a função
 * que cada thread irá executar. Mais especificamente, elas
 * determinam o intervalo de atuação de cada thread.
 * */

long int getStartInterval(long int id, int blockSize){
    return id * blockSize;
}

int getBlockSize(){
    return numberOfElements/numberOfThreads;
}

long int getEndInterval(long int id, long int start, int blockSize){
    if(id == numberOfThreads){
        return numberOfElements;
    }
    return (start + blockSize);
}

/*
 * Função que cada thread irá executar. Retorna a soma de seus
 * respectivos intervalos.
 * */
void* getApproximatePiValue(void *arg){
    long int id = (long int) arg;

    int blockSize = getBlockSize();

    long int start = getStartInterval(id, blockSize);

    long int end = getEndInterval(id,start, blockSize);

    long double *localSum = (long double*)malloc(sizeof(long double));
    if(localSum == NULL){
        fprintf(stderr, "Malloc error");
    }
    *localSum = 0;

    for(long int i = start; i <=  end ; i++){
        *localSum += (4 * pow(-1, i) *  (1.0/(2 * i + 1)));
    }

    pthread_exit((void*) localSum);
}

/*
 * Função sequencial para aproximar PI.
 * */
double getApproximatePiValueSequential(long long int elems){
    double approximatePiValue = 0;

    for(int i = 0; i < elems; i++){
        approximatePiValue += (4 * pow(-1, i) / (2 * i + 1));
    }

    return approximatePiValue;
}


int main(int argc, char *argv[]){

    double start,finish, deltaConcurrent, deltaSequential;

    if(argc < 3){
        printf("Entre com o numero de de elementos para calcular a serie e o numero de threads.\n");
        return -1;
    }

    numberOfElements = atoll(argv[1]);
    
    numberOfThreads = atoll(argv[2]);

    GET_TIME(start);

    //Aloca espaço para as threadsç
    pthread_t *tid = (pthread_t*)malloc(numberOfThreads * sizeof(pthread_t));
    if(tid == NULL){
        fprintf(stderr, "malloc error");
        return -1;
    }

    //Inicializa as threads.
    for(long int i = 0; i < numberOfThreads; i++){
        if(pthread_create(tid+i,NULL, getApproximatePiValue,(void*)i)){
            fprintf(stderr, "thread error");
            return -2;
        }
    }

    long double *threadSum;

    long double totalSum = 0.0;

    //Aguarda as threads finalizarem, acumula os resultados.
    for(long int i = 0; i < numberOfThreads; i++){
        if(pthread_join(*(tid+i),(void**) &threadSum)){
            fprintf(stderr, "thread join error");
            return -3;
        }

        totalSum += *threadSum;
    }

    long double concurrentPiApproximate = totalSum;

    GET_TIME(finish);

    deltaConcurrent = finish - start;

    GET_TIME(start);

    double sequentialPiApproximate = getApproximatePiValueSequential(numberOfElements);

    GET_TIME(finish);

    deltaSequential = finish - start;

    long double approximationErrorConcurrent = fabsl(totalSum - M_PI);

    long double approximatonErrorSequential = fabsl(sequentialPiApproximate - M_PI);

    printf("O valor de Pi aproximado de forma concorrente é: %1.16Lf\n", concurrentPiApproximate);

    printf("O valor de Pi aproximado de forma sequencial é: %.15f\n", sequentialPiApproximate);

    printf("O tempo de execucao sequencial de %lld elementos eh de %f segundos\n",numberOfElements, deltaSequential);

    printf("O tempo de execucao concorrente com %lld elementos usando %d threads eh de %f segundos\n", numberOfElements, numberOfThreads, deltaConcurrent);

    printf("O Valor de erro de forma concorrente é: %.15Lf\n", approximationErrorConcurrent);

    printf("O Valor de erro de forma sequencial  é: %.15Lf\n", approximatonErrorSequential);

    free(tid);
    free(threadSum);

    return 0;
}