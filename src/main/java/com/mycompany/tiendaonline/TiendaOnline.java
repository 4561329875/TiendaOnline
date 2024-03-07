/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tiendaonline;

import baseDatos.DB;
import baseDatos.Guardar;
import productos.Producto;

/**
 *
 * @author labctr
 */
public class TiendaOnline {

    public static void main(String[] args) {
        
        DB db =new DB();
        db.iniciar();
        Guardar guardar = new Guardar(db);
        Producto pro = new Producto();
        pro.setCodigo("132");
        pro.setCantidad(3);
        pro.setDescripcion("no se");
        pro.setIva(true);
        pro.setNombre("ni idea");
        pro.setPrecio(100);
        guardar.guardar(pro);
        System.out.println("Hello World!");
        db.cerrar();
    }
}
