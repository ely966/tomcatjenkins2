package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

@Service("productoServiceMemory")
public class ProductoServiceMemory implements ProductoService{
	@Autowired
	private ProductoRepository repositorioProducto;
	
	@Override
	public Producto add (Producto prod) {
		return repositorioProducto.save(prod);
	}
	@Override
	public List<Producto> findAll(){
		return repositorioProducto.findAll();
	}
	@Override
	public Producto findById(long id) {
		return repositorioProducto.findById(id).orElse(null);
	}
	@Override
	public void init() {
		repositorioProducto.save(new Producto("Madalena",1,0));
		repositorioProducto.save(new Producto("Leche", 2,0));
		repositorioProducto.save(new Producto(2,"Helado", 4,0));
		repositorioProducto.save(new Producto(3,"Patatas", (int) 1.5,0));
					
	}
}
