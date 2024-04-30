package com.example.batchwebmain.job;


import com.example.batchwebmain.domain.model.DailyTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import java.beans.Statement;

@Slf4j
@Component
class StatementJobSkipListener implements SkipListener<DailyTransaction, Statement> {

    @Override
    public void onSkipInProcess(final DailyTransaction item, final Throwable exception) {
        log.warn("Skipped item while processing: " + item + " due to skippable exception", exception);
    }
}
