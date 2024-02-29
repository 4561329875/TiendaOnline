/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturacion;

import pedidos.Pedido;
import productos.Producto;

/**
 *
 * @author david
 */
public class Factura {
    
    private int codigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    private Pedido pedido;

    public Pedido getPedido() {
        return pedido;
    }

        private double subTotal;

    public double getSubTotal() {
        return subTotal;
    }


        private double total;

    public double getTotal() {
        return total;
    }

   
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Factura(Pedido pedido) {
        this.pedido = pedido;
        double subtot=0;
        double tot=0;
        for (Producto pro : pedido.getProductos()) {
            subtot+=pro.getPrecioVenta();
            if (pro.getIva()) {
                tot+=pro.getPrecioVenta()*1.12;
            } else {
                tot+=pro.getPrecioVenta();
            }
        }
        
    }
    
}
