#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>
#include "timer.h"


int numberOfThreads;
int partitions;
double upperLimit;
double lowerLimit;
double partitionSize;


double func(double x){
    return exp(exp(x));
}

int getBlockSize(){
    return partitions / numberOfThreads;
}

long int getStartInterval(long int id, int blockSize){
    return id * blockSize;
}

long int getEndInterval(long int id, long int start, int blockSize){
    if(id == numberOfThreads){
        return partitions;
    }else{
        return (start + blockSize);
    }
}

void* simpsonIntegration(void *arg){

    long int id = (long int) arg;

    int blockSize = getBlockSize();

    long int start = getStartInterval(id, blockSize);

    long int end = getEndInterval(id, start, blockSize);

    double *result = (double *)malloc(sizeof(double));
    *result = 0;

    for(long int i = start; i <= end; i++){
        if(i % 2 != 0){
            *result += 2 * func(i * partitionSize + lowerLimit);
        }else {
            *result +=  4 * func(i * partitionSize + lowerLimit);
        }
    }
    *result = *result * (partitionSize / 3);
    pthread_exit((void*) result);


}

void testResults(){

}


int main(int argc, char* argv[]) {

    if(argc < 5){
        printf("Digite: %s <Limite Inferior de Integracao> <Limite Superior de Integracao> <Numero de particionamento>"
               " <Numero de Threads>\n", argv[0]);
        return -1;
    }

    double start, end, delta;

    lowerLimit = atoi(argv[1]);
    upperLimit = atoi(argv[2]);
    partitions = atoi(argv[3]);
    numberOfThreads = atoi(argv[4]);

    partitionSize = (upperLimit - lowerLimit)/ partitions;

    GET_TIME(start);

    pthread_t *tid = (pthread_t*)malloc(sizeof(pthread_t) * numberOfThreads);

    for(long int i =0; i < numberOfThreads; i++){
        if(pthread_create(tid + i, NULL, simpsonIntegration, (void *) i)){

        }
    }
    double *threadReturn;
    double sum = 0;
    for(int i =0; i < numberOfThreads; i++){
        if(pthread_join(*(tid + i), (void **) &threadReturn)){}
        sum += *threadReturn;
    }
    free(threadReturn);

    GET_TIME(end);

    delta = end - start;

    printf("O valor da integracao numerica da funcao escolhida com limite superior igual a %f, limite inferior igual a %f, com %d particionamentos eh: %f\n\n",
           upperLimit, lowerLimit, partitions, sum);

    printf("O tempo de execucao concorrente com %d particionamentos e utilizando %d thread eh: %f\n\n",
           partitions, numberOfThreads, delta);

    return 0;
}