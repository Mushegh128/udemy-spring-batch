package com.udemy.spring.batch.writer;

import com.udemy.spring.batch.model.StudentCsv;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvItemWriter implements ItemWriter<StudentCsv> {

    @Override
    public void write(List<? extends StudentCsv> items) throws Exception {
        System.out.println("inside CsvItemWriter");
        items.stream().forEach(System.out::println);
    }
}
