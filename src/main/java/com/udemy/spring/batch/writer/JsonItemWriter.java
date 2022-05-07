package com.udemy.spring.batch.writer;

import com.udemy.spring.batch.model.StudentCsv;
import com.udemy.spring.batch.model.StudentJson;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonItemWriter implements ItemWriter<StudentJson> {

    @Override
    public void write(List<? extends StudentJson> items) throws Exception {
        System.out.println("inside JsonItemWriter");
        items.stream().forEach(System.out::println);
    }
}
