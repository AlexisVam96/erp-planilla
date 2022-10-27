package com.demo.excel.demoexcel.service;

import com.demo.excel.demoexcel.entity.Usuario;
import com.demo.excel.demoexcel.repository.IUsuarioRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Log4j2
@Transactional
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public List<Usuario> findAll(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario){
        log.info("Guardando el usuario {}", usuario);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> saveAll(List<Usuario> usuarios){
        return usuarioRepository.saveAll(usuarios);
    }



}
