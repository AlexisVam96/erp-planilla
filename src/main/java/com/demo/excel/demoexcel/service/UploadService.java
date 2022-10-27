package com.demo.excel.demoexcel.service;

import com.demo.excel.demoexcel.Utils.UploadUtil;
import com.demo.excel.demoexcel.entity.Usuario;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Log4j2
public class UploadService {

    String nombre, apellido, email, fecha;


    List<Usuario> listUsuarios = new ArrayList<>();

    @Autowired
    private UsuarioService usuarioService;

    private final UploadUtil uploadUtil;

    public UploadService(UploadUtil uploadUtil) {
        this.uploadUtil = uploadUtil;
    }


    public List<Usuario> upload(MultipartFile file) throws Exception{

        Path tempDirectory = Files.createTempDirectory("");

        File tempFile = tempDirectory.resolve(file.getOriginalFilename()).toFile();

        file.transferTo(tempFile);

        log.info("Ruta de excel -> {}", tempFile.toString());

        Workbook workbook = WorkbookFactory.create(tempFile);

        Sheet sheet = workbook.getSheetAt(0);

        Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowsStreamSupplier(sheet);

        Row headerRow = rowStreamSupplier.get().findFirst().get();

        List<String> headerCells =  StreamSupport.stream(headerRow.spliterator(), false)
                .map(Cell::getStringCellValue)
                .collect(Collectors.toList());

        log.info("headerCells: {}", headerCells);


        rowStreamSupplier.get().forEach(row ->{

            if(!row.equals(headerRow)){
                List<String> cellValues = StreamSupport.stream(row.spliterator(), false)
                        .map(cell ->{
                            String cellVal = cell.getStringCellValue();
                            return cellVal;
                        })
                        .collect(Collectors.toList());

                for (int i = 0; i < cellValues.size(); i++) {
                    nombre = cellValues.get(0);
                    apellido = cellValues.get(1);
                    email = cellValues.get(2);
                    fecha = cellValues.get(3);
                }
                listUsuarios.add(new Usuario(null, nombre, apellido, email, fecha));
                //usuarioService.save(new Usuario(null,nombre ,apellido,email,fecha));
                //log.info("usuario: {}", usuario.toString());


                log.info("cellValues: {}", cellValues);

            }



        });
        return listUsuarios;
    }
}
