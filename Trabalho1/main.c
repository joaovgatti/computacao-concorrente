#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>

float func(float x){
    return logf(x);
}

float simpson(float lowerLimit, float upperLimit, int parts){

    float partition = (upperLimit - lowerLimit) / parts;

    float result = 0;
    for(int i = 1; i <= parts; i++){
        if(i % 2 != 0){
            result += 2 * func(i * partition + lowerLimit);
        }else {
            result +=  4 * func(i * partition + lowerLimit);
        }
    }
    result = result * (partition / 3);
    return result;


}




int main(int argc, char* argv[]) {

    float integral = simpson(4,5.2, 6);
    printf("%f", integral);

    return 0;
}
