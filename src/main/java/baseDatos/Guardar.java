/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import com.mongodb.client.MongoCollection;
import envios.Envio;
import facturacion.Factura;
import facturacion.Pago;
import org.bson.Document;
import pedidos.Cliente;
import pedidos.Pedido;
import productos.Producto;

/**
 *
 * @author david
 */
public class Guardar {

    private DB db;

    public Guardar(DB db) {
        this.db = db;
    }

    public void guardar(Producto pro) {
        MongoCollection<Document> collection = db.getCollecProdu();
        Document document = new Document("codigo", pro.getCodigo())
                .append("nombre", pro.getNombre())
                .append("descripcion", pro.getDescripcion())
                .append("precio", pro.getPrecio())
                .append("precioVenta", pro.getPrecioVenta())
                .append("iva", pro.getIva())
                .append("cantidad", pro.getCantidad());
        Document query = new Document("codigo", pro.getCodigo());
        Document result = collection.find(query).first();
        if (result == null) {

            collection.insertOne(document);
        } else {
            collection.updateOne(result, document);
        }

    }
    

    public void guardar(Cliente cliente) {
        MongoCollection<Document> collection = db.getCollecClient();
        Document document = new Document("dni_ruc", cliente.getDni_ruc())
                .append("nombre", cliente.getNombre())
                .append("direcion", cliente.getDirecion())
                .append("telefono", cliente.getTelefono())
                .append("email", cliente.getEmail());
        Document query = new Document("dni_ruc", cliente.getDni_ruc());
        Document result = collection.find(query).first();
        if (result == null) {
            collection.insertOne(document);
        } else {
            
            collection.updateOne(result, document);
        }
    }

    public void guardar(Pedido pedido) {
        MongoCollection<Document> collection = db.getCollecPedi();
        String[] pros=new String[pedido.getProductos().length];
        for (int i = 0; i < pedido.getProductos().length; i++) {
            pros[i]=pedido.getProductos()[i].getCodigo();
        }
        double[] cants=new double[pedido.getProductos().length];
        for (int i = 0; i < pedido.getProductos().length; i++) {
            cants[i]=pedido.getProductos()[i].getCantidad();
        }
        Document document = new Document("codigo", pedido.getCodigo())
                .append("cliente", pedido.getCliente().getDni_ruc())
                
                .append("productos", pros)
                .append("cantidad", cants)
                .append("fecha", pedido.getFecha())
                .append("estado", pedido.getEstado());
        Document query = new Document("codigo", pedido.getCodigo());
        Document result = collection.find(query).first();
        if (result == null) {
            collection.insertOne(document);
        } else {
            
            collection.updateOne(result, document);
        }
    }

    public void guardar(Factura factura) {
        MongoCollection<Document> collection = db.getCollecFact();
        Document document = new Document("codigo", factura.getCodigo())
                .append("pedido", factura.getPedido().getCodigo())
                .append("subtotal", factura.getSubTotal())
                .append("total", factura.getTotal())
                .append("estado", factura.getEstado());
        Document query = new Document("codigo", factura.getCodigo());
        Document result = collection.find(query).first();
        if (result == null) {
            collection.insertOne(document);
        } else {
            collection.updateOne(result, document);
        }
    }

    public void guardar(Pago pago) {
        MongoCollection<Document> collection = db.getCollecPag();
        Document document = new Document("codigo", pago.getCodigo())
                .append("pedido", pago.getPedido().getCodigo())
                .append("factura", pago.getFactura().getCodigo())
                .append("forma", pago.getForma())
                .append("valorPagado", pago.getValorPagado());
        Document query = new Document("codigo", pago.getCodigo());
        Document result = collection.find(query).first();
        if (result == null) {
            collection.insertOne(document);
        } else {
            collection.updateOne(result, document);
        }
    }

    public void guardar(Envio envio) {
        MongoCollection<Document> collection = db.getCollecEnvi();
        Document document = new Document("codigo", envio.getCodigo())
                .append("pedido", envio.getPedido().getCodigo())
                .append("fechaSalida", envio.getFechaSalida())
                .append("fechaEntrega", envio.getFechaEntrega())
                .append("direcion", envio.getDirecion())
                .append("distancia", envio.getDistancia())
                .append("tiempo", envio.getTiempo());
        Document query = new Document("codigo", envio.getCodigo());
        Document result = collection.find(query).first();
        if (result == null) {
            collection.insertOne(document);
        } else {
           collection.updateOne(result, document);
        }
    }
    public void eliminarPro(Producto pro) {
        MongoCollection<Document> collection = db.getCollecProdu();
        Document filtro = new Document("codigo", pro.getCodigo());
        collection.deleteOne(filtro);
    }
}
