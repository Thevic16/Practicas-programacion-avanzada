package edu.pucmm.eict.util;

import io.javalin.Javalin;

public class Ruta {
    private Javalin app;

    public Ruta (Javalin app){
        this.app = app;
    }

    public void ejecutarRutas(){
        app.get("/index", ctx -> {
            // some code
            ctx.result("Pagina principal");
        });

    }


}
