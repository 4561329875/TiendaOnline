/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import envios.Envio;
import facturacion.Factura;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import pedidos.Cliente;
import pedidos.Pedido;
import productos.Producto;

/**
 *
 * @author david
 */
public class Listar {

    private DB db;

    public Listar(DB db) {
        this.db = db;
    }

    public Producto[] obtenerProductos() {
        MongoCollection<Document> collection = db.getCollecProdu();
        List<Producto> lista = new ArrayList<Producto>();

        Producto[] pro = new Producto[1];

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Producto indPro = new Producto();
                indPro.setCodigo(document.getString("codigo"));
                indPro.setNombre(document.getString("nombre"));
                indPro.setDescripcion(document.getString("descripcion"));
                indPro.setPrecio(document.getDouble("precio"));
                indPro.setIva(document.getBoolean("iva"));
                indPro.setCantidad(document.getDouble("cantidad"));
                lista.add(indPro);

            }
        }
        pro = lista.toArray(new Producto[0]);
        return pro;
    }

    public Producto obtenerProductos(String a) {
        MongoCollection<Document> collection = db.getCollecProdu();

        Document query = new Document("codigo", a);
        Document result = collection.find(query).first();

        Document document = result;
        Producto indPro = new Producto();
        indPro.setCodigo(document.getString("codigo"));
        indPro.setNombre(document.getString("nombre"));
        indPro.setDescripcion(document.getString("descripcion"));
        indPro.setPrecio(document.getInteger("precio"));
        indPro.setIva(document.getBoolean("iva"));

        return indPro;
    }

    public Producto[] obtenerProductos(String nombre, String descripcion, String codigo) {
        MongoCollection<Document> collection = db.getCollecProdu();
        List<Producto> lista = new ArrayList<Producto>();

        Producto[] pro = null;
        FindIterable<Document> result;
        MongoCursor<Document> cursor = null;

        if (nombre.equals("")) {
            if (descripcion.equals("")) {
                if (codigo.equals("")) {
                    // Si todos los campos están vacíos, recuperar todos los documentos
                    result = collection.find();
                    cursor = result.cursor();
                } else {
                    // Si solo el campo 'codigo' tiene un valor
                    result = collection.find(Filters.regex("codigo", codigo));
                    cursor = result.cursor();
                }
            } else {
                if (codigo.equals("")) {
                    // Si solo el campo 'descripcion' tiene un valor
                    result = collection.find(Filters.regex("descripcion", descripcion));
                    cursor = result.cursor();
                } else {
                    // Si 'descripcion' y 'codigo' tienen valores
                    result = collection.find(Filters.and(
                            Filters.regex("descripcion", descripcion),
                            Filters.regex("codigo", codigo)
                    ));
                    cursor = result.cursor();
                }
            }
        } else {
            if (descripcion.equals("")) {
                if (codigo.equals("")) {
                    // Si solo el campo 'nombre' tiene un valor
                    result = collection.find(Filters.regex("nombre", nombre));
                    cursor = result.cursor();
                } else {
                    // Si 'nombre' y 'codigo' tienen valores
                    result = collection.find(Filters.and(
                            Filters.regex("nombre", nombre),
                            Filters.regex("codigo", codigo)
                    ));
                    cursor = result.cursor();
                }
            } else {
                if (codigo.equals("")) {
                    // Si 'nombre' y 'descripcion' tienen valores
                    result = collection.find(Filters.and(
                            Filters.regex("nombre", nombre),
                            Filters.regex("descripcion", descripcion)
                    ));
                    cursor = result.cursor();
                } else {
                    // Si todos los campos tienen valores
                    result = collection.find(Filters.and(
                            Filters.regex("nombre", nombre),
                            Filters.regex("descripcion", descripcion),
                            Filters.regex("codigo", codigo)
                    ));
                    cursor = result.cursor();
                }
            }
        }

        while (cursor.hasNext()) {
            Document document = cursor.next();
            Producto indPro = new Producto();
            indPro.setCodigo(document.getString("codigo"));
            indPro.setNombre(document.getString("nombre"));
            indPro.setDescripcion(document.getString("descripcion"));
            indPro.setPrecio(document.getInteger("precio"));
            indPro.setIva(document.getBoolean("iva"));
            lista.add(indPro);

        }
        pro = lista.toArray(new Producto[0]);
        return pro;

    }

    public Pedido[] obtenerPedidos() {
        MongoCollection<Document> collection = db.getCollecProdu();
        List<Pedido> lista = new ArrayList<Pedido>();

        Pedido[] pro = null;

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                Pedido ind = new Pedido();
                ind.setCodigo(document.getInteger("codigo"));
                ind.setCliente(obtenerCliente(document.getInteger("cliente")));

                Producto[] produc = new Producto[((String[]) document.get("productos")).length];
                for (int i = 0; i < produc.length; i++) {
                    produc[i] = obtenerProductos(((String[]) document.get("productos"))[i]);
                    produc[i].setCantidad(((double[]) document.get("cantidad"))[i]);
                }
                ind.setProductos(produc);
                ind.setFecha(document.getString("fecha"));
                ind.setEstado(document.getString("estado"));
                lista.add(ind);

            }
        }
        pro = lista.toArray(new Pedido[0]);
        return pro;
    }

    public Pedido obtenerPedido(Integer a) {
        MongoCollection<Document> collection = db.getCollecClient();

        Document query = new Document("codigo", a);
        Document result = collection.find(query).first();

        Document document = result;

        Pedido ind = new Pedido();
        ind.setCodigo(document.getInteger("codigo"));
        ind.setCliente(obtenerCliente(document.getInteger("cliente")));

        Producto[] produc = new Producto[((String[]) document.get("productos")).length];
        for (int i = 0; i < produc.length; i++) {
            produc[i] = obtenerProductos(((String[]) document.get("productos"))[i]);
        }
        ind.setProductos(produc);
        ind.setFecha(document.getString("fecha"));
        ind.setEstado(document.getString("estado"));

        return ind;
    }

    public Cliente obtenerCliente(int ruc) {

        MongoCollection<Document> collection = db.getCollecClient();

        Document query = new Document("dni_ruc", ruc);
        Document result = collection.find(query).first();

        Document document = result;
        Cliente ind = new Cliente();
        ind.setDni_ruc(document.getInteger("dni_ruc"));
        ind.setDirecion(document.getString("direcion"));
        ind.setNombre(document.getString("nombre"));
        ind.setEmail(document.getString("email"));
        ind.setTelefono(document.getInteger("telefono"));

        return ind;
    }
    public Factura obteneFacturaPed(int i){
        MongoCollection<Document> collection = db.getCollecFact();

        Document query = new Document("pedido", i);
        Document result = collection.find(query).first();

        Document document = result;
        Factura fac = new Factura(obtenerPedido(i));
        fac.setCodigo(document.getInteger("codigo"));
        fac.setEstado(document.getString("estado"));
        return fac;
    }
    public Factura obteneFacturaCod(int i){
        MongoCollection<Document> collection = db.getCollecFact();

        Document query = new Document("codigo", i);
        Document result = collection.find(query).first();

        Document document = result;
        Factura fac = new Factura(obtenerPedido(i));
        fac.setCodigo(document.getInteger("codigo"));
        fac.setEstado(document.getString("estado"));
        return fac;
    }
    public Envio[] obteEnvios(){
        MongoCollection<Document> collection = db.getCollecEnvi();
        List<Envio> lista = new ArrayList<Envio>();

        Envio[] pro = null;

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                Envio ind = new Envio();
                ind.setCodigo(document.getInteger("codigo"));
                ind.setPedido(obtenerPedido(document.getInteger("pedido")));
                ind.setFechaSalida(document.getString("fechaSalida"));
                ind.setFechaEntrega(document.getString("fechaEntrega"));
                ind.setDirecion(document.getString("direcion"));
                ind.setDistancia(document.getDouble("distancia"));
                ind.setTiempo(document.getString("tiempo"));
                lista.add(ind);

            }
        }
        pro = lista.toArray(new Envio[0]);
        return pro;
    
    }
}
