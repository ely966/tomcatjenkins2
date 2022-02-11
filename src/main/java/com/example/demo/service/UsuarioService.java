package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Usuario;

public interface UsuarioService {
	
	public Usuario add (Usuario user);
	public List<Usuario> findAll();
	public Usuario findById(long id);
	public Usuario edit(Usuario user);
	//public Usuario comprobar(String user, String pass);
    public Usuario findByUsernameAndPass(String user, String pass);
    public Usuario findByUserName(String userName);
   public void init();

}
