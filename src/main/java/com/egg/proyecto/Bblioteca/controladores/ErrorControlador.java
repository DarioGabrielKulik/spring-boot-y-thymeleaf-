package com.egg.proyecto.Bblioteca.controladores;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorControlador implements ErrorController {

    @RequestMapping(value = "/error", method =  {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView errorPaginas(HttpServletRequest httpServletRequest){

        ModelAndView errorPagina = new ModelAndView("error");

        String errorMsg = "";

        int httpErrorCodigo = getErrorCode(httpServletRequest) ;

        switch (httpErrorCodigo){
            case 400:{
                errorMsg = "El recurso solicitado no existe.";
                break;
            }
            case 401:{
                errorMsg = "No se encuentra autorizado.";
                break;
            }
            case 403:{
                errorMsg = "No tiene permisos para acceder al recurso.";
                break;
            }
            case 404:{
                errorMsg = "El recurso solicitado no fue encontrado.";
                break;
            }
            case 500:{
                errorMsg = "Ocurrio un error interno.";
                break;
            }

        }

        errorPagina.addObject("codigo",httpErrorCodigo);
        errorPagina.addObject("mensaje",errorMsg);
        return errorPagina;
    }

    private int getErrorCode(HttpServletRequest httpServletRequest){
        return (Integer) httpServletRequest.getAttribute("javax.servlet.error.status_code") ;
    }

    public  String getErrorPath(){
        return "/error.html";
    }
}
