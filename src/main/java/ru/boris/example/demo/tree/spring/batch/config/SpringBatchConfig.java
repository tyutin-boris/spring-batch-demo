package ru.boris.example.demo.tree.spring.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import ru.boris.example.demo.tree.spring.batch.listener.DialogChunkListener;
import ru.boris.example.demo.tree.spring.batch.listener.DialogReadListener;
import ru.boris.example.demo.tree.spring.batch.listener.DialogWriteListener;
import ru.boris.example.demo.tree.spring.batch.model.DialogueEntity;
import ru.boris.example.demo.tree.spring.batch.repository.DialogueRepository;

@Configuration
public class SpringBatchConfig {

    @Bean
    public Job dialoguesLoaderJob(Step dialoguesLoaderStep, JobRepository jobRepository) {
        return new JobBuilder("dialoguesLoaderJob", jobRepository)
                .start(dialoguesLoaderStep)
                .build();
    }

    @Bean
    public Step dialoguesLoaderStep(JobRepository jobRepository,
                                    PlatformTransactionManager platformTransactionManager,
                                    ItemReader<DialogueEntity> dialogReader,
                                    ItemWriter<DialogueEntity> dialogWriter,
                                    DialogChunkListener dialogChunkListener,
                                    DialogReadListener dialogReadListener,
                                    DialogWriteListener dialogWriteListener
    ) {
        return new StepBuilder("dialoguesLoaderStep", jobRepository)
                .<DialogueEntity, DialogueEntity>chunk(100, platformTransactionManager)
                .reader(dialogReader)
                .writer(dialogWriter)
                .listener(dialogChunkListener)
                .listener(dialogReadListener)
                .listener(dialogWriteListener)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<DialogueEntity> dialogReader(FieldSetMapper<DialogueEntity> dialogueEntityMapper) {
        Resource resource = new PathResource("src/main/resources/RickAndMortyScripts.csv");

        FlatFileItemReader<DialogueEntity> reader = new FlatFileItemReader<>();
        reader.setResource(resource);

        DefaultLineMapper<DialogueEntity> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(dialogueEntityMapper);
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());

        reader.setLineMapper(lineMapper);
        reader.open(new ExecutionContext());

        return reader;
    }

    @Bean
    public ItemWriter<DialogueEntity> dialogWriter(DialogueRepository dialogueRepository) {
        RepositoryItemWriter<DialogueEntity> writer = new RepositoryItemWriter<>();

        writer.setRepository(dialogueRepository);
        writer.setMethodName("save");

        return writer;
    }
}
