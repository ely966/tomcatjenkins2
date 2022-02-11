package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Producto;

public interface ProductoService {
	public Producto add (Producto prod);
	public List<Producto> findAll();
	public Producto findById(long id);
	public void init();
}
