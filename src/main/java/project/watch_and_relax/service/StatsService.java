package project.watch_and_relax.service;

import java.time.Instant;

public interface StatsService {
    void incRequestCount();
    int getRequestCount();
    Instant getStartedOn();
}
