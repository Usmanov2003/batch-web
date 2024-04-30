package com.example.batchwebmain.controller;

import com.example.batchwebmain.job.StatementJobExecutor;
import com.example.batchwebmain.utils.APIResponse;
import com.example.batchwebmain.utils.DateTimeUtils;
import com.example.batchwebmain.utils.PaginatedResource;
import com.example.batchwebmain.utils.PaginatedResourceAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Statement;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatementController implements StatementApi {

    private final StatementJobExecutor statementJobExecutor;

    private final StatementRepository statementRepository;

    @Override
    public ResponseEntity<APIResponse<?>> submitStatementJob(
            final YearMonth month, final List<String> cardNumbers, final boolean forceRestart)
            throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException {
        final YearMonth statementMonth =
                Objects.nonNull(month) ? month : DateTimeUtils.previousMonthIST();

        log.info(
                "Starting Statement job task with parameters >> month: "
                        + statementMonth
                        + ", cardNumbers: "
                        + (CollectionUtils.isNotEmpty(cardNumbers) ? String.join(",", cardNumbers) : "All")
                        + ", forceRestart: "
                        + forceRestart);
        this.statementJobExecutor.executeStatementJob(statementMonth, forceRestart, cardNumbers);

        return ResponseEntity.accepted()
                .body(
                        APIResponse.newInstance()
                                .addMessage("Statement job submitted successfully for Month: " + statementMonth));
    }

    @Override
    public PaginatedResource<Statement> getStatements(
            final YearMonth month, final List<String> cardNumbers, final Pageable pageRequest) {
        return PaginatedResourceAssembler.assemble(
                this.statementRepository.getStatements(month, cardNumbers, pageRequest));
    }
}
