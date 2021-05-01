package project.watch_and_relax.service.impl;

import org.springframework.stereotype.Service;
import project.watch_and_relax.service.StatsService;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatsServiceImpl implements StatsService {
    private AtomicInteger requestCount=new AtomicInteger(0);
    private Instant startedOn=Instant.now();
    @Override
    public void incRequestCount() {
        requestCount.incrementAndGet();
    }

    @Override
    public int getRequestCount() {
        return requestCount.get();
    }

    @Override
    public Instant getStartedOn() {
        return startedOn;
    }
}
