package com.udemy.spring.batch.writer;

import com.udemy.spring.batch.model.StudentXml;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XmlItemWriter implements ItemWriter<StudentXml> {

    @Override
    public void write(List<? extends StudentXml> items) throws Exception {
        System.out.println("inside XmlItemWriter");
        items.stream().forEach(System.out::println);
    }
}
