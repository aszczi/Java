public class SimpleThreadFactory implements ThreadsFactory {
    @Override
    public Thread leftReadOnlyThread(Runnable run) {
        return new Thread(run);
    }

    @Override
    public Thread rightReadOnlyThread(Runnable run) {
        return new Thread(run);
    }

    @Override
    public Thread writeOnlyThread(Runnable run) {
        return new Thread(run);
    }
}
