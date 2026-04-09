package com.tarea7.catalogo_mongo.controller;

import com.tarea7.catalogo_mongo.model.Libro;
import com.tarea7.catalogo_mongo.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/libros")
public class LibroWebController {

    private final LibroService libroService;

    public LibroWebController(LibroService libroService) {
        this.libroService = libroService;
    }

    // Página principal: lista todos los libros
    @GetMapping
    public String listarLibros(Model model) {
        model.addAttribute("libros", libroService.findAll());
        return "lista-libros";
    }

    // Mostrar formulario para crear nuevo libro
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("libro", new Libro());
        return "formulario-libro";
    }

    // Guardar libro (crear)
    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro) {
        libroService.create(libro);
        return "redirect:/web/libros";
    }

    // Mostrar formulario para editar libro
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable String id, Model model) {
        Libro libro = libroService.findById(id);
        model.addAttribute("libro", libro);
        return "formulario-libro";
    }

    // Actualizar libro
    @PostMapping("/actualizar/{id}")
    public String actualizarLibro(@PathVariable String id, @ModelAttribute Libro libro) {
        libroService.update(id, libro);
        return "redirect:/web/libros";
    }

    // Eliminar libro
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable String id) {
        libroService.delete(id);
        return "redirect:/web/libros";
    }

    // Ver detalles de un libro
    @GetMapping("/ver/{id}")
    public String verDetalles(@PathVariable String id, Model model) {
        Libro libro = libroService.findById(id);
        model.addAttribute("libro", libro);
        return "detalles-libro";
    }

    // Buscar libros por criterios
    @GetMapping("/buscar")
    public String buscarLibros(@RequestParam(required = false) String titulo,
                               @RequestParam(required = false) String autor,
                               @RequestParam(required = false) String genero,
                               Model model) {
        if (titulo != null && !titulo.isEmpty()) {
            model.addAttribute("libros", libroService.findByTitulo(titulo));
            model.addAttribute("criterioBusqueda", "Título: " + titulo);
        } else if (autor != null && !autor.isEmpty()) {
            model.addAttribute("libros", libroService.findByAutor(autor));
            model.addAttribute("criterioBusqueda", "Autor: " + autor);
        } else if (genero != null && !genero.isEmpty()) {
            model.addAttribute("libros", libroService.findByGenero(genero));
            model.addAttribute("criterioBusqueda", "Género: " + genero);
        } else {
            model.addAttribute("libros", libroService.findAll());
            model.addAttribute("criterioBusqueda", "Todos los libros");
        }
        return "lista-libros";
    }
}