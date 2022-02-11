package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;

@Service("UsuarioServiceMemory")
public class UsuarioServiceMemory implements UsuarioService{

private List<Usuario> repositorio = new ArrayList<>();
	
@Override
public Usuario add (Usuario user) {
	repositorio.add(user);
	return user;
}
@Override
public List<Usuario> findAll(){
	return repositorio;
}
//*ES option, es decir que si no pones orElse que queire decir que sino hay id de null, pues te fallara.**//

@Override
public Usuario findById(long id) {
	Usuario result = null;
	boolean encontrado = false;
	int i = 0;
	while (!encontrado && i<repositorio.size()) {
		if (repositorio.get(i).getId() == id) {
			encontrado = true;
			result = repositorio.get(i);
		} else {
			i++;
		}
	}
	
	return result;
}
@Override
public Usuario edit(Usuario user) {
	boolean encontrado = false;
	int i = 0;
	while (!encontrado && i < repositorio.size()) {
		if (repositorio.get(i).getId() == user.getId()) {
			encontrado = true;
			repositorio.remove(i);
			repositorio.add(i, user);
		} else {
			i++;
		}
	}
	
	if (!encontrado)
		repositorio.add(user);
	
	return user;
}
@Override
//**Reconozca el user par aver si existe**//
public Usuario findByUserName(String userName) {
	boolean encontrado = false;
	int i = 0;
	Usuario userencontrado=null;
	while (!encontrado && i < repositorio.size()) {
		if (repositorio.get(i).getUserName().equals(userName)) {
			encontrado = true;
			userencontrado=repositorio.get(i);
		} else {
			i++;
		}
	}
	
	return userencontrado;
}

//**Falta ahcer que compruebe tabien la contraseña**//
@Override
public Usuario findByUsernameAndPass(String userName, String pass) {
	boolean encontrado = false;
	int i = 0;
	Usuario userencontrado=null;
	while (!encontrado && i < repositorio.size()) {
		if (repositorio.get(i).getUserName().equals(userName)) {
			if(repositorio.get(i).getPass().equals(pass)) {
				encontrado = true;
				userencontrado=repositorio.get(i);
				}
		} else {
			i++;
		}
	}
	
	return userencontrado;
}



//**Iniciacion**//
//**Creamos dos usuarios**//
@Override
	@PostConstruct
	public void init() {
	//	//UsuarioService servicioUser = null;
		Usuario user1= new Usuario("luis","luis","Luis Morales","Calle Mares n.6 piso:2A",654234187);
		
		Usuario user2=new Usuario("maria","maria","Maria Rodriguez", "Avenida MariaFlores n.2 piso:4B",663123563);
		repositorio.add(user2);
		repositorio.add(user1);
		
	
	}
}

