//
// Created by joaovgatti on 4/4/21.
//

#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include "timer.h"



int *matriz1;
int *matriz2;
int *matrizFinal;
int nthreads;
int dim;

typedef struct {
    int id;
    int dim;
}tArgs;



//funcao que cada thread ira executar
void* task(void *arg){
    tArgs *args = (tArgs*) arg;
    for(int i = args->id; i < args->dim; i+= nthreads){
        for(int j = 0; j < args->dim; j++){
            int innerProduct = 0;
            for(int k = 0; k < args->dim; k++){
                innerProduct += matriz1[i*(args->dim) + k] * matriz2[k * (args->dim) + k];
            }
            matrizFinal[i*(args->dim) + j] = innerProduct;
        }
    }
    pthread_exit(NULL);
}



//inicializa as matrizes
void matrixSetUp(int* matrixA, int* matrixB, int* matrixC){
    for(int i = 0; i < dim; i++){
        for(int j =0; j < dim; j++){
            matrixA[i * dim + j] = 1; //mat[i][j]
            matrixB[i * dim + j] = 1;
            matrixC[i * dim + j] = 0;
        }
    }
}

void printMatrix(int* matrix, int dimension){
    for(int i = 0; i < dimension; i++){
        for(int j = 0; j < dimension; j++){
            printf("%d ", matrix[i*dimension+j]);
        }
        puts("");
    }
}




int main(int argc, char* argv[]){
    double beginning, end;

    GET_TIME(beginning);

    pthread_t *tid;
    tArgs *args;

    if(argc < 3){
        printf("digite: %s <dimensao da matriz1> <numero de threads>\n", argv[0]);
        return 1;
    }

    dim = atoi(argv[1]);
    nthreads = atoi(argv[2]);

    if(nthreads > dim){
        nthreads = dim;
    }

    matriz1 = (int*)malloc(sizeof(int) * dim * dim);
    if(matriz1 == NULL){printf("Memory error"); return -1;}

    matriz2 = (int*)malloc(sizeof (int)* dim*dim);
    if(matriz2 == NULL){printf("Memory Error"); return -1;}

    matrizFinal = (int*)malloc(sizeof (int)* dim*dim);
    if(matrizFinal == NULL){printf("Memory Error"); return -1;}

    tid = (pthread_t*)malloc(sizeof(pthread_t) * nthreads);
    if(tid == NULL){printf("Memory Error"); return -2;}

    args = (tArgs*)malloc(sizeof (tArgs) * nthreads);
    if(args == NULL){printf("Memory Error"); return -2;}

    matrixSetUp(matriz1, matriz2, matrizFinal);

    GET_TIME(end);
    double setUpTime = end - beginning;

    GET_TIME(beginning);


    for(int i = 0; i < nthreads; i++){
        (args+i)-> id = i;
        (args+i)->dim = dim;
        if(pthread_create(tid+i,NULL, task,(void*) (args+i))){
            printf("Thread Create Error");
            return -3;
        }
    }

    for(int i =0; i < nthreads; i++){
        pthread_join(*(tid+i), NULL);
    }

    GET_TIME(end);
    double multiplcationTime = end - beginning;


    GET_TIME(beginning);

    //printf("Final Matrix\n");
    //printMatrix(matrizFinal, dim);


    free(matriz1);
    free(matriz2);
    free(matrizFinal);
    free(tid);
    free(args);

    GET_TIME(end);

    double setOFFTime = end - beginning;

    printf("Init time: %f\n", setUpTime);
    printf("Multiplication time: %f\n", multiplcationTime);
    printf("SetOFF time: %f\n", setOFFTime);

    return 0;
}

