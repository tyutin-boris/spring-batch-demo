package ru.boris.example.demo.tree.spring.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RickScheduler {

    private final Job rickWriteDialog;
    private final JobLauncher asyncLauncher;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void writeRickDialogue() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("Start writing rick dialogue");

        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

        asyncLauncher.run(rickWriteDialog, jobParameters);
    }
}
