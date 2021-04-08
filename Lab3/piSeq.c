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

    if(argc < 2)
    {
        printf("Entre com o numero de de elementos para calcular a serie\n");
        return -1;
    }

    long long int numberOfElements = atoll(argv[1]);

    double approximatePiValue = getApproximatePiValue(numberOfElements);

    printf("O valor de Pi aproximado é: %.15f\n",approximatePiValue);
    printf("O Valor de Pi na lib do C é: %.15f\n", M_PI);

    return 0;
}
