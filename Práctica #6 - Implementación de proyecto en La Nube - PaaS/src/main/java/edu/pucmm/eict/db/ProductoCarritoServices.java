package edu.pucmm.eict.db;

import edu.pucmm.eict.util.ProductoCarrito;

public class ProductoCarritoServices extends GestionDb<ProductoCarrito> {
    private static ProductoCarritoServices instancia;

    private ProductoCarritoServices() {
        super(ProductoCarrito.class);
    }

    public static ProductoCarritoServices getInstancia(){
        if(instancia == null){
            instancia = new ProductoCarritoServices();
        }
        return instancia;
    }

}
