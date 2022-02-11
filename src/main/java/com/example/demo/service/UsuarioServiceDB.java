package com.example.demo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Primary
@Service("UsuarioServiceDB")
public class UsuarioServiceDB implements UsuarioService {
	@Autowired
	private UsuarioRepository repositorio;
	@Autowired
	private UsuarioService userServi;
	
	
	@Override
	public Usuario add (Usuario user) {
		return repositorio.save(user);
	}
	@Override
	public List<Usuario> findAll(){
		return repositorio.findAll();
	}
	//*ES option, es decir que si no pones orElse que queire decir que sino hay id de null, pues te fallara.**//

	@Override
	public Usuario findById(long id) {
		return repositorio.findById(id).orElse(null);
	}
	@Override
	public Usuario edit(Usuario user) {
		return repositorio.save(user);
	}
	//**Reconozca el user par aver si existe**//
	@Override
	public Usuario findByUserName(String userName) {
		return repositorio.findByUserName(userName).orElse(null);
	}
	
	
	//**Falta ahcer que compruebe tabien la contraseña**//
	@Override
	public Usuario findByUsernameAndPass(String user, String pass) {
		
			
		return repositorio.findByUserName(user).orElse(null);
	}
	
	
	
	//**Iniciacion**//
	//**Creamos dos usuarios**//
	@Override
		@PostConstruct
		public void init() {
			Usuario user1= new Usuario("luis","luis","Luis Morales","Calle Mares n.6 piso:2A",654234187);
			
			Usuario user2=new Usuario("maria","maria","Maria Rodriguez", "Avenida MariaFlores n.2 piso:4B",663123563);
			repositorio.save(user1);
			repositorio.save(user2);
			//userServi.add(user2);
			//userServi.add(user1);
		
		}
		
		
}
