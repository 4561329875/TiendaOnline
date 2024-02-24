/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
/**
 *
 * @author david
 */
public class DB {
    private MongoClient mongoClient = null;
    private MongoCollection<Document> collecProdu = null;
    private MongoCollection<Document> collecPedi = null;
    private MongoCollection<Document> collecClient = null;
    private MongoCollection<Document> collecFact = null;
    private MongoCollection<Document> collecPag = null;
    private MongoCollection<Document> collecEnvi = null;

    public void iniciar() {
        String connectionString = "mongodb+srv://fdtoro:WjykdQ47BQOqZoDm@cluster0.mgdhlmc.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient mongoClientTem = MongoClients.create(settings);

        this.mongoClient = mongoClientTem;
        MongoDatabase database = mongoClient.getDatabase("TiendaOnline");

        collecProdu=database.getCollection("Productos");
        collecClient=database.getCollection("Clientes");
        collecPedi=database.getCollection("Pedidos");
        collecFact=database.getCollection("Facturas");
        collecPag=database.getCollection("Pagos");
        collecEnvi=database.getCollection("Envios");
    }

    public void cerrar() {

        this.mongoClient.close();
    }

    public MongoCollection<Document> getCollecProdu() {
        return collecProdu;
    }

    public MongoCollection<Document> getCollecPedi() {
        return collecPedi;
    }

    public MongoCollection<Document> getCollecClient() {
        return collecClient;
    }

    public MongoCollection<Document> getCollecFact() {
        return collecFact;
    }

    public MongoCollection<Document> getCollecPag() {
        return collecPag;
    }

    public MongoCollection<Document> getCollecEnvi() {
        return collecEnvi;
    }
	
}
