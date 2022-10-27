package com.demo.excel.demoexcel.Controller;

import com.demo.excel.demoexcel.entity.Usuario;
import com.demo.excel.demoexcel.excel.UserExcelExporter;
import com.demo.excel.demoexcel.service.UploadService;
import com.demo.excel.demoexcel.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UploadService uploadService;

    @GetMapping("/usuarios")
    public ResponseEntity<Map<String,Object>> index(){
        List<Usuario> usuarios = usuarioService.findAll();
        Map<String, Object> response = new HashMap<>();

        if(usuarios.isEmpty()){
            response.put("mensaje","No existen elementos en la base de datos");
            return  new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje","Si existen elementos en la base de datos");
        response.put("usuarios", usuarios);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }


    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportAllData() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=usuarios.xlsx");

        List<Usuario> usuarios = usuarioService.findAll();
        UserExcelExporter exp = new UserExcelExporter(usuarios);
        ByteArrayInputStream stream = exp.exportExcel();
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Usuario>> upload(@RequestParam("file")MultipartFile file) throws Exception {
        List<Usuario> listUsuarios = uploadService.upload(file);

        List<Usuario> usuarios = usuarioService.saveAll(listUsuarios);

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
