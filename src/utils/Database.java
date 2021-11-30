package utils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;

public class Database {
    // * Atributos
    private MongoClient mongoClient;
    private MongoDatabase database;


    // * Constructores
    Database() {}

    Database(String URI) {
        this.mongoClient = new MongoClient(new MongoClientURI(URI));
    }

    public Database(String URI, String database) {
        this.mongoClient = new MongoClient(new MongoClientURI(URI));
        this.database = mongoClient.getDatabase(database);
    }


    // * Metodos
    /**
     * Metodo para obtener el cliente.
     * 
     * @database MongoDB
     * @return {@code MongoClient}
     */
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    /**
     * Retorna la base de datos.
     * 
     * @database MongoDB
     * @return {@code database}
     */
    public MongoDatabase getDatabase() {
        return database;
    }

    /**
     * Retorna una coleccion.
     * 
     * @database MongoDB
     * @param collection Nombre de la coleccion
     * @return {@code MongoCollection}
     */
    public MongoCollection<Document> getCollection(String collection) {
        return database.getCollection(collection);
    }

    /**
     * Crea colecciones en la base de datos.
     * 
     * @database MongoDB
     * @param name Nombre de nueva coleccion
     */
    public void createCollection(String name) {
        database.createCollection(name);
    }

    /**
     * Inserta un documento a una coleccion especifica
     * en la base de datos.
     * 
     * @database MongoDB
     * @param collection Nombre de la coleccion
     * @param document Documento
     */
    public void insertOne(String collection, Document document) {
        MongoCollection<Document> newItem = database.getCollection(collection);
        newItem.insertOne(document);
    }

    /**
     * Borrar especificos documentos de una coleccion
     * en la base de datos.
     * 
     * @database MongoDB
     * @param collection Nombre de la coleccion
     * @param key Llave a buscar
     * @param value Valor a buscar
     */
    public void deleteOne(String collection, String key, String value) {
        MongoCollection<Document> deleteItem = database.getCollection(collection);
        Bson filter = eq(key, value);
        deleteItem.deleteOne(filter);
    }

    /**
     * Actualiza documentos especificos de una coleccion
     * en la base de datos
     * 
     * @database MongoDB
     * @param collection Nombre de la coleccion
     * @param key Llave a buscar
     * @param value_filter Valor a buscar
     * @param value_update Valor nuevo
     */
    public void updateOne(String collection, String key, String valueFilter, String valueUpdate) {
        MongoCollection<Document> updateItem = database.getCollection(collection);
        BasicDBObject update = new BasicDBObject(key, valueUpdate);
        BasicDBObject setQuery = new BasicDBObject("$set", update);
        BasicDBObject searchQuery = new BasicDBObject(key, valueFilter);
        updateItem.updateOne(searchQuery, setQuery);
    }

    /**
     * Metodo para buscar un documento en una coleccion.
     * 
     * @database MongoDB
     * @param collection Nombre de la coleccion
     * @param key Llave a buscar
     * @param value Valor a buscar
     * @return {@code Document} Si existe la coleccion.
     */
    public Document findOne(String collection, String key, String value) {
        MongoCollection<Document> findItem = database.getCollection(collection);
        Bson query = new BasicDBObject(key, value);
        Document d1 = findItem.find(query).first();
        return d1;
    }

    /**
     * Retorna todos los documentos de una coleccion
     * de la base de datos.
     * 
     * @database MongoDB
     * @param collection Nombre de la coleccion
     * @return {@code List<Document>} Si existe la coleccion.
     */
    public List<Document> findAllDocument(String nameCollection) {
        MongoCollection<Document> collection = database.getCollection(nameCollection);
        FindIterable<Document> iterDoc = collection.find();
        Iterator<Document> it = iterDoc.iterator();

        List<Document> documents = new ArrayList<Document>();

        while (it.hasNext()) {
            documents.add((Document) it.next());
        }

        return documents;
    }
}
