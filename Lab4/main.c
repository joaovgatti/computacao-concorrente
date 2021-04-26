#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

int x = 0;

pthread_mutex_t mutex;

pthread_cond_t cond;

void *thread1(void *arg){
    pthread_mutex_lock(&mutex);
    //espera todas as threads executarem
    while(x < 3){
        pthread_cond_wait(&cond, &mutex);
    }
    printf("Volte sempre.\n");
    pthread_mutex_unlock(&mutex);
    pthread_exit(NULL);

}

void *thread2(void *arg){
    pthread_mutex_lock(&mutex);
    //espera pela thread4
    if(x < 1){
        pthread_cond_wait(&cond, &mutex);
    }
    x++;
    printf("Fique a vontade.\n");
    //sinaliza para thread1
    pthread_cond_signal(&cond);
    pthread_mutex_unlock(&mutex);
    pthread_exit(NULL);
}

void *thread3(void *arg){
    pthread_mutex_lock(&mutex);
    //espera pela thread4
    if(x < 1){
        pthread_cond_wait(&cond, &mutex);
    }
    x++;
    printf("Sente-se por favor.\n");
    //sinaliza para thrad1
    pthread_cond_signal(&cond);
    pthread_mutex_unlock(&mutex);
    pthread_exit(NULL);
}

void *thread4(void *arg){
    printf("Seja bem vindo.\n");
    pthread_mutex_lock(&mutex);
    x++;
    //sinaliza para o restante das threads
    pthread_cond_broadcast(&cond);
    pthread_mutex_unlock(&mutex);
    pthread_exit(NULL);
}

int main() {

    pthread_t *tid = (pthread_t*)malloc(sizeof(pthread_t)*4);

    /*
     *  Array de funcoes.
     *  "Two or more, use a for"
     * */
    typedef void (f)(void);
    f* func[4] = {(f *) &thread1, (f *) &thread2, (f *) &thread3, (f *) &thread4};

    pthread_mutex_init(&mutex, NULL);
    pthread_cond_init(&cond, NULL);

    for(int i  = 0; i < 4; i++){
        if(pthread_create(tid + i, NULL, (void *(*)(void *)) func[i], NULL)){
            printf("error");
        }
    }

    for (int i = 0; i < 4; i++) {
        pthread_join(tid[i], NULL);
    }

    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&cond);

}
