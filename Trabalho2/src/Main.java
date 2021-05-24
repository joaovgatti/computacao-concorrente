import java.util.Scanner;

public class Main {


    public static void main(String args[]){

        Scanner scanner = new Scanner(System.in);
        int numberOfsensors, numberOfActuators;
        System.out.println("Entre com o numero de sensores");
        numberOfsensors = scanner.nextInt();
        numberOfActuators = numberOfsensors;


        Sensor[] sensors = new Sensor[numberOfsensors];
        Atuador[] actuators = new Atuador[numberOfActuators];


        LE monitor = new LE();

        for(int i =0; i < numberOfsensors; i++){

            sensors[i] = new Sensor(i, monitor);

            actuators[i] = new Atuador(i,sensors[i], monitor);

            sensors[i].start();
            actuators[i].start();
        }
    }
}
