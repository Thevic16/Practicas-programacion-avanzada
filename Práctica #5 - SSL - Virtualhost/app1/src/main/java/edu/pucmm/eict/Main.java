package edu.pucmm.eict;

import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create().start(5000);
        //app.config.addStaticFiles("/publico");

        app.get("/", ctx -> {
            // some code
            ctx.render("pagina/app1.html");
        });

    }

}
