package com.eddie.controller;

import com.eddie.ecommerce.model.*;
import com.eddie.ecommerce.service.*;
import com.eddie.ecommerce.service.impl.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InicioController {

    private static JuegoService juegoService;
    private static CategoriaService categoriaService;
    private static CreadorService creadorService;
    private static PlataformaService plataformaService;
    private static IdiomaService idiomaService;
    private static FormatoService formatoService;
    private static TipoEdicionService tipoEdicionService;

    private static Logger logger = LogManager.getLogger(InicioController.class);

    public InicioController() {
        juegoService = new JuegoServiceImpl();
        categoriaService = new CategoriaServiceImpl();
        creadorService = new CreadorServiceImpl();
        plataformaService = new PlataformaServiceImpl();
        idiomaService = new IdiomaServiceImpl();
        formatoService = new FormatoServiceImpl();
        tipoEdicionService = new TipoEdicionServiceImpl();
    }
    public static Response procesarPeticion(JsonElement entrada, String action, String idiomaWeb){
        Response respuesta = new Response();
        try {
            List<Juego> todos = juegoService.findAllByDate(idiomaWeb);
            List<Juego> valoracion=juegoService.findAllByValoracion(idiomaWeb);

            //Precarga de todos los datos en cache
            List<Categoria> categorias= categoriaService.findAll(idiomaWeb);
            List<Creador> creador= creadorService.findAll();
            List<Plataforma> plataformas=plataformaService.findAll();
            List<Idioma> idioma=idiomaService.findAll(idiomaWeb);
            List<Formato> formatos= formatoService.findAll(idiomaWeb);
            List<TipoEdicion> tipoEdicion=tipoEdicionService.findAll(idiomaWeb);

            Map<String, List> datosPrecarga = new HashMap<>();
            datosPrecarga.put("Todos", Collections.singletonList(todos));
            datosPrecarga.put("Valoracion", Collections.singletonList(valoracion));
            datosPrecarga.put("Categorias", Collections.singletonList(categorias));
            datosPrecarga.put("Creador", Collections.singletonList(creador));
            datosPrecarga.put("Idiomas", Collections.singletonList(idioma));
            datosPrecarga.put("Plataformas", Collections.singletonList(plataformas));
            datosPrecarga.put("Formatos", Collections.singletonList(formatos));
            datosPrecarga.put("TipoEdiciones", Collections.singletonList(tipoEdicion));

            Gson gson = new Gson();
            respuesta.setSalida(gson.toJson(datosPrecarga));
            respuesta.setStatus("OK");
        } catch (com.eddie.ecommerce.exceptions.DataException e) {
            logger.debug(e);
        }
        return respuesta;
    }
}
