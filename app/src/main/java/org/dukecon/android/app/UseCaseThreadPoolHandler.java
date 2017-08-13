package org.dukecon.android.app;

import org.dukecon.android.domain.usecase.UseCaseHandler;
import org.dukecon.android.domain.usecase.UseCaseScheduler;

public class UseCaseThreadPoolHandler extends UseCaseHandler {
    private static UseCaseHandler INSTANCE;

    private UseCaseThreadPoolHandler(UseCaseScheduler useCaseScheduler) {
        super(useCaseScheduler);
    }

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return INSTANCE;
    }
}
