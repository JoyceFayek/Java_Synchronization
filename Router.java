//package synchronizationPackage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

public class Router implements Runnable {

    public static ArrayList<String> cNum = new ArrayList<String>();

    Network network = new Network();
    Semaphore semClass;

    public Router(int n)  {
        semClass = new Semaphore(network.n);

        for (int i=0; i<n; i++)
        {
            cNum.add(null);
        }
    }

    public void connect() throws InterruptedException {

        for (int i = 0; i < network.devices.size(); i++) {
            Thread th = new Thread(this, network.devices.get(i).getName());
            th.start();
        }
    }

    public void run() {
        Random random = new Random();
        try {
            String name = Thread.currentThread().getName();
            semClass.P(name);
            int a=number(name);

            Network.print.println("Connection " + a + ": " + name + " Occupied");
            Thread.sleep(1000);

            Network.print.println("Connection " + a + ": " + name + " Performs online activity");
            Thread.sleep((random.nextInt(5) + 1) * 1000);

            semClass.V(name,a);
            cNum.set(a-1,null);

            Thread.currentThread().stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int number(String n) {
        for (int i=0; i<cNum.size(); i++)
        {
            if (cNum.get(i)==null)
            {
                cNum.set(i,n);
                return i+1;
            }
        }
        return -1;
    }

}

class Semaphore {
    public int value = 0;
    int counter=0;

    public Semaphore(int value) {
        this.value = value ;
    }

    public synchronized void P(String name) throws InterruptedException, IOException {
        value--;
        if (value < 0) {
            for (int i = 0; i < Network.devices.size(); i++) {
                if (Network.devices.get(i).getName().equals(name)) {
                    Network.print.println(name + " (" + Network.devices.get(i).getType() + ")" + " Arrived and waiting");
                    break;
                }
            }
            wait();

        } else {
            for (int i = 0; i < Network.devices.size(); i++) {
                if (Network.devices.get(i).getName().equals(name)) {
                    Network. print.println(name + " (" + Network.devices.get(i).getType() + ")" + " Arrived");
                    break;
                }
            }
        }

    }

    public synchronized void V(String name, int n) throws IOException {
        value++;
        if (value <= 0)
            notify();
        Network.print.println("Connection " + n +  ": " + name + " Logged out");
        counter++;
        if(counter==Network.devices.size()) {
            Network.print.close();
            System.out.println("Successfully Wrote the Output to the File..");
        }
    }
}

class Device {
    String name;
    String type;

    public Device(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
}

class Network {
    public static ArrayList<Device> devices = new ArrayList<Device>();
    public static File file = new File("output.txt");
    public static FileWriter myWriter;
    public static int n;

    static {
        try {
            myWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static PrintWriter print = new PrintWriter(myWriter);

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner input = new Scanner(System.in);

        String name;
        String type;

        System.out.println("JAVA SYNCHRONIZATION");
        System.out.println("--------------------------------------------------");
        System.out.println("What is the number of WI-FI Connections ?");
        n = input.nextInt();

        System.out.println("What is the number of devices Clients want to connect?");
        int tc = input.nextInt();

        for (int i = 0; i < tc; i++) {

            name = input.next();
            type = input.next();

            devices.add(new Device(name, type));
        }
        Router MyRouter = new Router(n);
        MyRouter.connect();
    }
}