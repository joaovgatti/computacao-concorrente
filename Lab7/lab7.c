#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

#define NTHREADS 4

sem_t sem1, sem2;


void *thread1(void *arg){
	sem_wait(&sem2);
	sem_wait(&sem2);
	printf("Volte sempre!\n");
	pthread_exit(NULL);
}


void *thread2(void *arg){
	sem_wait(&sem1);
	printf("Fique a vontade.\n");
	sem_post(&sem2);
	pthread_exit(NULL);

}

void *thread3(void *arg){
	sem_wait(&sem1);
	printf("Sente-se por favor.\n");
	sem_post(&sem2);
	pthread_exit(NULL);
}

void *thread4(void *arg){
	printf("Seja bem vindo\n");
	sem_post(&sem1);
	sem_post(&sem1);
	pthread_exit(NULL);
}

int main(void){
	
	pthread_t tid[NTHREADS];

	sem_init(&sem1, 0,0);
	sem_init(&sem2, 0,0);

	pthread_create(&tid[0], NULL, thread1,NULL);
	pthread_create(&tid[1], NULL, thread2,NULL);
	pthread_create(&tid[2], NULL, thread3,NULL);
	pthread_create(&tid[3], NULL, thread4,NULL);

	for(int i = 0; i < NTHREADS;i++){
		pthread_join(tid[i], NULL);
	}

	sem_destroy(&sem1);
	sem_destroy(&sem2);


	return 0;


}

