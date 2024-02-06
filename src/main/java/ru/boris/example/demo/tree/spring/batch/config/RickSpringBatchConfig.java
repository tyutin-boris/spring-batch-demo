package ru.boris.example.demo.tree.spring.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import ru.boris.example.demo.tree.spring.batch.mapper.RickMapProcessor;
import ru.boris.example.demo.tree.spring.batch.model.DialogueEntity;
import ru.boris.example.demo.tree.spring.batch.model.RickDialogueEntity;
import ru.boris.example.demo.tree.spring.batch.repository.DialogueRepository;
import ru.boris.example.demo.tree.spring.batch.repository.RickDialogueRepository;

import java.util.HashMap;

@Configuration
public class RickSpringBatchConfig {

    @Bean
    public JobLauncher asyncLauncher(JobRepository jobRepository) {
        TaskExecutorJobLauncher launcher = new TaskExecutorJobLauncher();
        launcher.setJobRepository(jobRepository);
        launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());

        try {
            launcher.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return launcher;
    }

    @Bean
    public Job rickWriteDialog(JobRepository jobRepository, Step stepRickWriteDialog) {
        return new JobBuilder("rickWriteDialog", jobRepository)
                .start(stepRickWriteDialog)
                .build();
    }

    @Bean
    public Step stepRickWriteDialog(JobRepository jobRepository,
                                    PlatformTransactionManager platformTransactionalManager,
                                    ItemReader<? extends DialogueEntity> rickDialogReader,
                                    ItemWriter<? super RickDialogueEntity> rickDialogWriter,
                                    RickMapProcessor rickMapProcessor) {
        return new StepBuilder("stepRickWriteDialog", jobRepository)
                .<DialogueEntity, RickDialogueEntity>chunk(10, platformTransactionalManager)
                .reader(rickDialogReader)
                .processor(rickMapProcessor)
                .writer(rickDialogWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public RepositoryItemReader<DialogueEntity> rickDialogReader(DialogueRepository dialogRepository) {
        RepositoryItemReader<DialogueEntity> reader = new RepositoryItemReader<>();

        HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("name", Sort.Direction.DESC);

        reader.setMethodName("findAll");
        reader.setRepository(dialogRepository);
        reader.setSort(sorts);

        return reader;
    }

    @Bean
    public RepositoryItemWriter<RickDialogueEntity> rickDialogWriter(RickDialogueRepository rickDialogueRepository) {
        RepositoryItemWriter<RickDialogueEntity> writer = new RepositoryItemWriter<>();

        writer.setMethodName("save");
        writer.setRepository(rickDialogueRepository);
        return writer;
    }
}
