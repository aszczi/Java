import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelFinder implements Finder {
    private int size;
    private Array globalArray;
    private Thread globalLeft;
    private Thread globalRight;
    private Thread globalWrite;
    private int searchedValue;
    private final List<Integer> indexes = new ArrayList<>();
    private final ConcurrentLinkedQueue<Integer> writeQueue = new ConcurrentLinkedQueue<>();
    private boolean[] accessed;
    private boolean running = false;
    private final Object startLock = new Object();
    private final Object accessLock = new Object();

      public void leftInstructions() {
        synchronized (startLock) {
            while (!running) {
                try {
                    startLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            if (!running) break;

            boolean shouldProcess = false;
            synchronized (accessLock) {
                if (!accessed[i]) {
                    accessed[i] = true;
                    shouldProcess = true;
                }
            }

            if (shouldProcess) {
                int value = globalArray.get(i);
                if (value == searchedValue) {
                    writeQueue.offer(i);
                    synchronized(indexes) {
                        indexes.add(i);
                    }
                }
            }
        }
    }

    public void rightInstructions() {
        synchronized (startLock) {
            while (!running) {
                try {
                    startLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        for (int i = size - 1; i >= 0; i--) {
            if (!running) break;

            boolean shouldProcess = false;
            synchronized (accessLock) {
                if (!accessed[i]) {
                    accessed[i] = true;
                    shouldProcess = true;
                }
            }

            if (shouldProcess) {
                int value = globalArray.get(i);
                if (value == searchedValue) {
                    writeQueue.offer(i);
                    synchronized(indexes) {
                        indexes.add(i);
                    }
                }
            }
        }
    }

    public void writeInstructions() {
        synchronized (startLock) {
            while (!running) {
                try {
                    startLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        while (running || !writeQueue.isEmpty()) {
            Integer pos = writeQueue.poll();
            if (pos != null) {
                globalArray.set0(pos);
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    @Override
    public void setThreadsFactory(ThreadsFactory factory) {
        globalLeft = factory.leftReadOnlyThread(this::leftInstructions);
        globalRight = factory.rightReadOnlyThread(this::rightInstructions);
        globalWrite = factory.writeOnlyThread(this::writeInstructions);
    }

    @Override
    public void setArray(Array array) {
        this.globalArray = array;
        this.size = array.size();
        this.accessed = new boolean[size];
    }

    @Override
    public List<Integer> start(int value) {
        this.searchedValue = value;

        globalWrite.start();
        globalLeft.start();
        globalRight.start();

        synchronized (startLock) {
            running = true;
            startLock.notifyAll();
        }

        try {
            globalLeft.join();
            globalRight.join();
            running = false;
            globalWrite.join();
        } catch (InterruptedException e) {
            running = false;
            Thread.currentThread().interrupt();
        }

        return new ArrayList<>(indexes);
    }
}