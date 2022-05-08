package com.udemy.spring.batch.writer;

import com.udemy.spring.batch.model.StudentJdbc;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcItemWriter implements ItemWriter<StudentJdbc> {

    @Override
    public void write(List<? extends StudentJdbc> items) throws Exception {
        System.out.println("inside jdbcItemWriter");
        items.stream().forEach(System.out::println);
    }
}
