package com.example.batchwebmain.job;

import com.example.batchwebmain.domain.model.DailyTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.beans.Statement;

@Slf4j
@RequiredArgsConstructor
public class StatementProcessor implements ItemProcessor<DailyTransaction, Statement> {

    @Override
    public Statement process(final DailyTransaction item) {
        return Statement.of(item.cardNumber(), item.date(), item.amount());
    }
}
