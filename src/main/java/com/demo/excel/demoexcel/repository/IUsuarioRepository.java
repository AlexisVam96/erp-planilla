package com.demo.excel.demoexcel.repository;

import com.demo.excel.demoexcel.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
