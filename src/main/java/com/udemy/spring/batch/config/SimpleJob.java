package com.udemy.spring.batch.config;

import com.udemy.spring.batch.listener.FirstJobListener;
import com.udemy.spring.batch.listener.FirstStepListener;
import com.udemy.spring.batch.model.StudentCsv;
import com.udemy.spring.batch.model.StudentJson;
import com.udemy.spring.batch.model.StudentXml;
import com.udemy.spring.batch.processor.FirstItemProcessor;
import com.udemy.spring.batch.reader.FirstItemReader;
import com.udemy.spring.batch.service.SecondTaskletService;
import com.udemy.spring.batch.writer.CsvItemWriter;
import com.udemy.spring.batch.writer.FirstItemWriter;
import com.udemy.spring.batch.writer.JsonItemWriter;
import com.udemy.spring.batch.writer.XmlItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.File;

@Configuration
public class SimpleJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SecondTaskletService secondTaskletService;

    @Autowired
    private FirstJobListener firstJobListener;

    @Autowired
    private FirstStepListener firstStepListener;

    @Autowired
    FirstItemReader firstItemReader;

    @Autowired
    FirstItemProcessor firstItemProcessor;

    @Autowired
    FirstItemWriter firstItemWriter;

    @Autowired
    CsvItemWriter csvItemWriter;

    @Autowired
    JsonItemWriter jsonItemWriter;

    @Autowired
    XmlItemWriter xmlItemWriter;

    @Bean
    public Job firstJob() {
        return jobBuilderFactory
                .get("First Job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    private Step firstStep() {
        return stepBuilderFactory
                .get("First Step")
                .tasklet(firstTask())
                .listener(firstStepListener)
                .build();
    }

    private Tasklet firstTask() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("first tasklet kuku");
                System.out.println("FFFFF = " + chunkContext.getStepContext().getStepExecutionContext());
                return RepeatStatus.FINISHED;
            }
        };
    }


    private Step secondStep() {
        return stepBuilderFactory
                .get("second Step")
                .tasklet(secondTaskletService)
                .build();
    }

    @Bean
    public Job secondJob() {
        return jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .next(secondStep())
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("first chunk step")
                .<Integer, Long>chunk(3)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    @Bean
    public Job csvJob() {
        return jobBuilderFactory.get("Csv Job")
                .incrementer(new RunIdIncrementer())
                .start(csvChunkStep())
                .build();
    }

    private Step csvChunkStep() {
        return stepBuilderFactory.get("csv step")
                .<StudentCsv, StudentCsv>chunk(3)
                .reader(flatFileItemReader())
//                .processor(firstItemProcessor)
                .writer(csvItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<StudentCsv> flatFileItemReader() {
        FlatFileItemReader<StudentCsv> flatFileItemReader =
                new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(new File("src/main/resources/inputFiles/students.csv")));
//            flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
//                {
//                    setLineTokenizer(new DelimitedLineTokenizer(","){
//                        {
//                            setNames("ID", "First Name", "Last Name", "Email");
//                        }
//                    });
//                    setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
//                        {
//                            setTargetType(StudentCsv.class);
//                        }
//                    });
//                }
//            });

        DefaultLineMapper<StudentCsv> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("ID", "First Name", "Last Name", "Email");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper<StudentCsv> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(StudentCsv.class);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        flatFileItemReader.setLineMapper(defaultLineMapper);

        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    @Bean
    public Job jsonJob() {
        return jobBuilderFactory.get("json Job")
                .incrementer(new RunIdIncrementer())
                .start(jsonChunkStep())
                .build();
    }

    private Step jsonChunkStep() {
        return stepBuilderFactory.get("json step")
                .<StudentJson, StudentJson>chunk(3)
                .reader(jsonJsonItemReader())
//                .processor(firstItemProcessor)
                .writer(jsonItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public JsonItemReader<StudentJson> jsonJsonItemReader() {
        JsonItemReader<StudentJson> jsonJsonItemReader = new JsonItemReader<>();
        jsonJsonItemReader.setResource(new FileSystemResource(new File("src/main/resources/inputFiles/student.json")));
        jsonJsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));
//        jsonJsonItemReader.setMaxItemCount(2);
//        jsonJsonItemReader.setCurrentItemCount(2);
        return jsonJsonItemReader;
    }

    @Bean
    public Job xmlJob() {
        return jobBuilderFactory.get("xml Job")
                .incrementer(new RunIdIncrementer())
                .start(xmlChunkStep())
                .build();
    }

    private Step xmlChunkStep() {
        return stepBuilderFactory.get("xml step")
                .<StudentXml, StudentXml>chunk(3)
                .reader(xmlItemReader())
//                .processor(firstItemProcessor)
                .writer(xmlItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public StaxEventItemReader<StudentXml> xmlItemReader() {
        StaxEventItemReader<StudentXml> staxEventItemReader = new StaxEventItemReader<>();
        staxEventItemReader.setResource(new FileSystemResource(new File("src/main/resources/inputFiles/students.xml")));
        staxEventItemReader.setFragmentRootElementName("student");
        staxEventItemReader.setUnmarshaller(new Jaxb2Marshaller(){
            {
                setClassesToBeBound(StudentXml.class);
            }
        });
        return staxEventItemReader;
    }
}
