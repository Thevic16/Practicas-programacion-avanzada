package edu.pucmm.eict.db;

import edu.pucmm.eict.util.ProductoMostrador;

public class ProductoMostradorServices extends GestionDb<ProductoMostrador>{
    private static ProductoMostradorServices instancia;

    private ProductoMostradorServices() {
        super(ProductoMostrador.class);
    }

    public static ProductoMostradorServices getInstancia(){
        if(instancia == null){
            instancia = new ProductoMostradorServices();
        }
        return instancia;
    }
}
