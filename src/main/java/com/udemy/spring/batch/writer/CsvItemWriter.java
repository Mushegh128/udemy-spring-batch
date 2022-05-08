package com.udemy.spring.batch.writer;

import com.udemy.spring.batch.model.StudentCsv;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;

@Component
public class CsvItemWriter implements ItemWriter<StudentCsv> {

    @Override
    public void write(List<? extends StudentCsv> items) throws Exception {
        System.out.println("inside CsvItemWriter");
        items.stream().forEach(System.out::println);
    }

//    @Bean
//    public FlatFileItemWriter<StudentCsv> flatFileItemWriter(){
//        FlatFileItemWriter<StudentCsv> flatFileItemWriter = new FlatFileItemWriter<>();
//        flatFileItemWriter.setResource(new FileSystemResource("src/main/resources/outputFiles/wroteStudent.csv"));
//        flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
//            @Override
//            public void writeHeader(Writer writer) throws IOException {
//                writer.write("ID,First Name,Last Name,Email");
//            }
//        });
//        flatFileItemWriter.setLineAggregator(new DelimitedLineAggregator<StudentCsv>(){
//            {
//                setFieldExtractor(new BeanWrapperFieldExtractor<StudentCsv>(){
//                    {
//                        setNames(new String[] {"id", "firstName", "lastName", "email"});
////                        setDelimiter("|");
//                    }
//                });
//            }
//        });
//        flatFileItemWriter.setFooterCallback(new FlatFileFooterCallback() {
//            @Override
//            public void writeFooter(Writer writer) throws IOException {
//                writer.write("Created " + new Date());
//            }
//        });
//        return flatFileItemWriter;
//    }
}
