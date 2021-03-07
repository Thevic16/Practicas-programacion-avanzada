package edu.pucmm.eict.db;


import edu.pucmm.eict.util.Usuario;

public class UsuarioServices extends GestionDb<Usuario>{
    private static UsuarioServices instancia;

    private UsuarioServices() {
        super(Usuario.class);
    }

    public static UsuarioServices getInstancia(){
        if(instancia == null){
            instancia = new UsuarioServices();
        }
        return instancia;
    }
}
