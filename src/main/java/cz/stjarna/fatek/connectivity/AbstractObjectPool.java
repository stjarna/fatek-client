package cz.stjarna.fatek.connectivity;

import cz.stjarna.fatek.exception.FatekRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class AbstractObjectPool<CONNECTION_TYPE extends IConnection> {
    private final BlockingQueue<CONNECTION_TYPE> pool;
    private final AtomicInteger poolSize = new AtomicInteger();

    private final ScheduledExecutorService executorService;
    private final ConnectionParams params;

    public AbstractObjectPool(final ConnectionParams connectionParams) throws IOException {
        this.params = connectionParams;

        final int initialSize = params.getInitialSize();
        final int maxSize = params.getMaxSize();
        final long checkInterval = params.getCheckInterval();

        pool = new LinkedBlockingQueue<CONNECTION_TYPE>();
        initialize(params);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.debug("Maintaining proper pool size");
                final int size = poolSize.get();
                if (size < initialSize) {
                    log.debug("Going to increase pool size to initial size");
                    final int sizeToBeAdded = initialSize - size;
                    for (int i = 0; i < sizeToBeAdded; i++) {
                        try {
                            pool.offer(createObject(params));
                        } catch (IOException e) {
                            throw new FatekRuntimeException("Unable create a new connection", e);
                        }
                        poolSize.incrementAndGet();
                    }
                } else if (maxSize > initialSize) {
                    log.debug("Going to decrease pool size to initial size");
                    final int sizeToBeRemoved = maxSize - initialSize;
                    for (int i = 0; i < sizeToBeRemoved; i++) {
                        final CONNECTION_TYPE object = pool.poll();
                        if (object != null) {
                            poolSize.decrementAndGet();
                            try {
                                destroyObject(object);
                            } catch (IOException e) {
                                throw new FatekRuntimeException("Unable destroy a connection", e);
                            }
                        }
                    }
                }
            }
        }, checkInterval, checkInterval, TimeUnit.MILLISECONDS);
    }

    protected CONNECTION_TYPE borrowObject() throws IOException {
        log.debug("Borrowing object from pool");
        CONNECTION_TYPE object;
        if ((object = pool.poll()) == null && poolSize.intValue() < params.getMaxSize() ) {
            object = createObject(params);
            poolSize.incrementAndGet();
        } else if (poolSize.intValue() >= params.getMaxSize()){
            log.warn("Max pool size reached, unable to borrow object from object pool");
        }
        return object;
    }

    protected void returnObject(final CONNECTION_TYPE object) {
        if (object == null) {
            log.debug("Nothing to return to object pool");
            return;
        }
        log.debug("Returning object to object pool");
        this.pool.offer(object);
    }

    protected void shutdown() {
        log.info("Shutting down the object pool");
        if (executorService != null) {
            executorService.shutdown();
        }

        for (final CONNECTION_TYPE object : pool) {
            try {
                destroyObject(object);
            } catch (IOException e) {
                throw new FatekRuntimeException("Unable destroy an object", e);
            }
        }
    }

    private void initialize(final ConnectionParams params) throws IOException {
        log.info("Initializing the object pool");
        for (int i = 0; i < params.getInitialSize(); i++) {
            pool.add(createObject(params));
            poolSize.incrementAndGet();
        }
    }

    protected abstract CONNECTION_TYPE createObject(ConnectionParams params) throws IOException;
    protected abstract void destroyObject(CONNECTION_TYPE object) throws IOException;

}