//
// Created by joaovgatti on 5/3/21.
//

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

/*
 * Variaveis globais:
 * Vetor usado, numero de threads, e numero
 * de threads cujo estao bloqueadas.
 * */
int* vector, nthread;
int blocked = 0;
pthread_mutex_t lock;
pthread_cond_t cond;


//Barreira
void barrier(int numberOfThreads){
    pthread_mutex_lock(&lock);

    if(blocked == (numberOfThreads - 1)){
        blocked = 0;
        pthread_cond_broadcast(&cond);
    }else {
        blocked++;
        pthread_cond_wait(&cond, &lock);
    }
    pthread_mutex_unlock(&lock);
}


void * task(void *arg){
    long int id = (long int) arg;

    int aux;

    for(int i =1; i < nthread;i *=2){
        if((id - i) >= 0) {
            //busca a aux
            aux = vector[id - i];
        }
        barrier(nthread);
        //atualiza
        if((id - i) >= 0){
            vector[id] += aux;
        }

        barrier(nthread);
    }
    pthread_exit(NULL);
}



void printVector(int* v, int size){
    for(int i = 0; i < size; i++){
        printf("%d ", v [i]);
    }
    printf("\n");
}

void assert(){
    int aux[nthread];
    //cria um vetor aux igual ao original
    for(int i =0; i < nthread; i++){
        aux[i]  = i;
    }
    for(int i =1; i < nthread; i++){
        int sum = 0;
        for(int j = 0; j <= i; j++){
            //faz a soma de todos elementos ate o atual para dps comparar com o vetor original.
            sum += aux[j];
        }
        if(vector[i] != sum){
            //nao eh igual, erro!
            puts("Assertion fail");
            return;
        }
    }
    puts("Assertion succeeds");
}




int main(int argc, char* argv[]) {

    if(argc < 2){
        printf("Digite: %s <numero de threads> potencia de 2/\n", argv[0]);
        return -1;
    }

    nthread = atoi(argv[1]);

    vector = (int*)malloc(sizeof(int) * nthread);
    if(vector == NULL){
        fprintf(stderr,"malloc error");
        return -1;
    }

    for(int i = 0; i < nthread; i++){
        vector[i] = i;
    }

    pthread_t *tid = (pthread_t*)malloc(sizeof(pthread_t)* nthread);
    if(*tid == NULL){
        fprintf(stderr,"malloc error");
        return -1;
    }

    pthread_mutex_init(&lock, NULL);

    pthread_cond_init(&cond, NULL);

    for(long int i = 0; i < nthread; i++){
        if(pthread_create((tid + i), NULL, task, (void *) i)){
            fprintf(stderr, "thread create error");
        }
    }

    for(int i =0; i < nthread; i++){
        if(pthread_join(*(tid+i),NULL)){
            fprintf(stderr, "thread join error");
        }
    }

    printf("O vector atualizado eh: \n");

    printVector(vector, nthread);

    printf("Assertion:\n");

    assert();

    return 0;
}
