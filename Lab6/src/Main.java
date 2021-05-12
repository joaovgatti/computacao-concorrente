import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        int numberOfElements;
        int nthreads;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the number of elements in the array");
        numberOfElements = scanner.nextInt();

        System.out.println("Type the number of threads to execute");
        nthreads = scanner.nextInt();

        Thread[] threads = new Thread[nthreads];

        SharedResource resource = new SharedResource(numberOfElements);

        resource.showVector();

        for(int i = 0; i < threads.length; i++ ){
            threads[i] = new ThreadObject(i, nthreads,resource);
        }

        for(Thread thread : threads){
            thread.start();
        }

        for(Thread thread : threads){
            try{
                thread.join();
            }catch (InterruptedException e){
                return;
            }
        }

        System.out.println("Final State of the Shared Resource");
        resource.showVector();

    }
}
