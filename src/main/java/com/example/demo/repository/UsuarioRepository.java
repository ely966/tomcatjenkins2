package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

	Optional<Usuario> findByUserName(String user);

	//Cuando no existe el metodo, hay que añadir aqui
	

}
