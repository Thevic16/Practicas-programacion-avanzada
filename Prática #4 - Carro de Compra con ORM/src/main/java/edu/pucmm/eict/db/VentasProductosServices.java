package edu.pucmm.eict.db;



import edu.pucmm.eict.util.VentasProductos;

public class VentasProductosServices extends GestionDb<VentasProductos>{
    private static VentasProductosServices instancia;

    private VentasProductosServices() {
        super(VentasProductos.class);
    }

    public static VentasProductosServices getInstancia(){
        if(instancia == null){
            instancia = new VentasProductosServices();
        }
        return instancia;
    }

}
