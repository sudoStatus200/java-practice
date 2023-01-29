import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

enum Direction {
    UP, DOWN
}

public class Lift {
    public static void main(String[] args) {
        System.out.println("");

        Thread requestListenerThread = new Thread(new RequestListener(), "RequestListenerThread");
        Thread requestProcessorThread = new Thread(new RequestProcessor(), "RequestProcessorThread");

        Elevator.getInstance().setRequestProcessorThread(requestProcessorThread);
        requestListenerThread.start();
        requestProcessorThread.start();

    }

}

class Elevator {
    private static Elevator elevator = null;
    private TreeSet<Integer> requestSet = new TreeSet<Integer>();
    private int currentFloor = 0;
    private Direction direction = Direction.UP;

    private Elevator() {
    };

    private Thread requestProcessorThread;

    static Elevator getInstance() {
        if (elevator == null) {
            elevator = new Elevator();
        }
        return elevator;
    }

    public synchronized void addFloor(int f) {
        requestSet.add(f);

        if (requestProcessorThread.getState() == Thread.State.WAITING) {
            notify();
        } else {
            requestProcessorThread.interrupt();
        }

    }

    public synchronized int nextFloor() {
        Integer floor = null;
        if (direction == Direction.UP) {
            if (requestSet.ceiling(currentFloor) != null) {
                floor = requestSet.ceiling(currentFloor);
            } else {
                floor = requestSet.floor(currentFloor);
            }
        } else {
            if (requestSet.floor(currentFloor) != null) {
                floor = requestSet.floor(currentFloor);
            } else {
                floor = requestSet.ceiling(currentFloor);
            }
        }

        if (floor == null) {
            try {
                System.out.println("Waiting at Floor :" + getCurrentFloor());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            // Remove the request from Set as it is the request in Progress.
            requestSet.remove(floor);
        }
        return (floor == null) ? -1 : floor;

    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) throws InterruptedException {
        if (this.currentFloor > currentFloor) {
            setDirection(Direction.DOWN);
        } else {
            setDirection(Direction.UP);
        }
        this.currentFloor = currentFloor;

        System.out.println("Floor : " + currentFloor);

        Thread.sleep(3000);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Thread getRequestProcessorThread() {
        return requestProcessorThread;
    }

    public void setRequestProcessorThread(Thread requestProcessorThread) {
        this.requestProcessorThread = requestProcessorThread;
    }

    public TreeSet<Integer> getRequestSet() {
        return requestSet;
    }

    public void setRequestSet(TreeSet<Integer> requestSet) {
        this.requestSet = requestSet;
    }

}

class RequestProcessor implements Runnable {
    @Override
    public void run() {
        while (true) {
            Elevator elevator = Elevator.getInstance();
            int floor = elevator.nextFloor();
            int currentFloor = elevator.getCurrentFloor();
            try {
                if (floor >= 0) {
                    if (currentFloor > floor) {
                        while (currentFloor > floor) {
                            elevator.setCurrentFloor(--currentFloor);
                        }
                    } else {
                        while (currentFloor < floor) {
                            elevator.setCurrentFloor(++currentFloor);
                        }
                    }
                    System.out.println("Welcome to Floor : " + elevator.getCurrentFloor());
                }

            } catch (InterruptedException e) {
                // If a new request has interrupted a current request processing then check -
                // -if the current request is already processed
                // -otherwise add it back in request Set
                if (elevator.getCurrentFloor() != floor) {
                    elevator.getRequestSet().add(floor);
                }
            }
        }
    }

}

class RequestListener implements Runnable {
    @Override
    public void run() {
        while (true) {
            String floorNumberStr = null;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                floorNumberStr = bufferedReader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isValidFloorNumber(floorNumberStr)) {
                System.out.println("User Pressed : " + floorNumberStr);
                Elevator elevator = Elevator.getInstance();
                elevator.addFloor(Integer.parseInt(floorNumberStr));
            } else {
                System.out.println("Floor Request Invalid : " + floorNumberStr);
            }
        }

    }

    private boolean isValidFloorNumber(String s) {
        return (s != null) && s.matches("\\d{1,2}");
    }

}
