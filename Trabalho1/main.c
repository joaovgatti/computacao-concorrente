#include <stdio.h>
#include <stdlib.h>


void swap(int *numero1, int *numero2){
    int temp = *numero1;
    *numero1 = *numero2;
    *numero2 = temp;
}


void bubbleSort(int arr[], int n){
    int i, j;
    for(i = 0; i < n-1; i++){
        for(j = 0;j < n-i-1;j++){
            if(arr[j] > arr[j+1]){
                swap(&arr[j], &arr[j+1]);
            }
        }
    }
}

void printArray(int arr[], int size){
    for(int i =0; i < size; i++){
        printf("%d - ",arr[i]);
    }
    printf("\n");
}





int main() {

    int arr[] = {293,123,122,120,344,4,12,102,0};
    bubbleSort(arr,9);
    printArray(arr,9);







    return 0;
}
