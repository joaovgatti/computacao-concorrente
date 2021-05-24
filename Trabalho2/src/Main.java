import java.util.Scanner;

public class Main {


    public static void main(String args[]){

        Scanner scanner = new Scanner(System.in);
        int numberOfsensors, numberOfActuators;
        numberOfsensors = scanner.nextInt();
        numberOfActuators = numberOfsensors;


        Sensor[] sensors = new Sensor[numberOfsensors];
        Atuador[] actuators = new Atuador[numberOfActuators];


        LE monitor = new LE();

        for(int i =0; i < numberOfsensors; i++){

            Sensor sensor = new Sensor(i, monitor);

            sensors[i] = sensor;

            actuators[i] = new Atuador(i,sensor, monitor);

            actuators[i].start();
            sensors[i].start();

        }
    }
}
