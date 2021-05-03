#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>


int* vetor, nthread;
int blockeds = 0;
pthread_mutex_t lock;
pthread_cond_t cond;



void wall(int numberOfThreads){

    pthread_mutex_lock(&lock);

    if(blockeds == (numberOfThreads - 1)){
        blockeds = 0;
        pthread_cond_broadcast(&cond);
    }else {
        blockeds++;

        pthread_cond_wait(&cond, &lock);
    }
    pthread_mutex_unlock(&lock);
}


/*void* task(void *arg) {
    long int id = (long int) arg;
    int aux;
    for (int i = 1; i <= nthread; i = i * 2) {
        if (id - i >= 0) {
            aux = vetor[(id - i)];
            aux += vetor[id];
        }
        wall(nthread);
        if (id - i >= 0) {
            vetor[id] = aux;
        }
        wall(nthread);
    }
        pthread_exit(NULL);

}*/

void * task2(void *arg){
    long int id = (long int) arg;
    int aux;
    for(int i =1; i < nthread;i *=2){
        if((id - i) >= 0) {
            aux = vetor[id - i];
        }
        wall(nthread);
        if((id - i) >= 0){
            vetor[id] += aux;
        }
        wall(nthread);
    }
    pthread_exit(NULL);
}



void printVector(int* vector, int size){
    for(int i = 0; i < size; i++){
        printf("%d ", vector [i]);
    }
    printf("\n");
}

void assert(){
    int aux[nthread];
    for(int i =0; i < nthread; i++){
        aux[i]  = i;
    }
    for(int i =1; i < nthread; i++){
        int sum = 0;
        for(int j = 0; j <= i; j++){
            sum += aux[j];
        }
        if(vetor[i] != sum){
            puts("ruim");
            return;
        }
    }
    puts('b');
}




int main(int argc, char* argv[]) {

    if(argc < 2){
        printf("Digite: %s <numero de threads> potencia de 2/\n", argv[0]);
        return -1;
    }

     nthread = atoi(argv[1]);


    vetor = (int*)malloc(sizeof(int)*nthread);

    for(int i = 0; i < nthread; i++){
        vetor[i] = i;
    }

    printVector(vetor, nthread);

    pthread_t *tid = (pthread_t*)malloc(sizeof(pthread_t)* nthread);

    pthread_mutex_init(&lock, NULL);
    pthread_cond_init(&cond, NULL);


    for(long int i = 0; i < nthread; i++){
        pthread_create((tid + i),NULL, task2, (void *) i);
    }

    for(int i =0; i < nthread; i++){
        pthread_join(*(tid+i),NULL);
    }

    printf("O vetor atualizado eh: \n");

    printVector(vetor, nthread);

    printf("--");
    assert();



    return 0;
}
