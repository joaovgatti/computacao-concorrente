//
// Created by joaovgatti on 4/8/21.
//
#include<stdio.h>
#include<stdlib.h>
#include<math.h>



double getApproximatePiValue(long long int numberOfElements){
    double approximatePiValue = 0;
    for(int i = 0; i < numberOfElements; i++){
        approximatePiValue += (4 * pow(-1, i) / (2 * i + 1));
    }
    return approximatePiValue;
}


int main(int argc, char *argv[]){

    long long int numberOfElements;

    if(argc < 2){
        printf("Entre com o numero %s de elementos",argv[0]);
        return -1;
    }

    numberOfElements = atoll(argv[1]);

    double approximatePiValue = getApproximatePiValue(numberOfElements);

    printf("O valor do Pi aproximado eh: %.15f\n",approximatePiValue);
    printf( "Valor de PI na lib oficial do C: %.15f\n", M_PI);

    return 0;
}
