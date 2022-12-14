package com.demo.excel.demoexcel.Utils;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component

public class UploadUtil {

    public Supplier<Stream<Row>> getRowsStreamSupplier(Iterable<Row> rows){
        return () -> getStream(rows);
    }

    public <T> Stream<T> getStream(Iterable<T> iterable){
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
