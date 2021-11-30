package utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

public class Form {
    Database database = new Database("mongodb://localhost:27017", "tarea-4-java");
    Encryption encryption = new Encryption();
    private Pattern patternPhoneNumber = Pattern.compile("^[0-9]{10}$");
    private Pattern patternEmail = Pattern.compile("^(.+)@(.+)$");

    /**
     * Verifica si el formato del parametro coincide con el formato
     * de un correo electronico.
     * 
     * @param email Correo Electronico
     * @return {@code true} Si el formato es correcto.
     */
    public Boolean checkEmail(String email) {
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }

    /**
     * Verifica si el formato del parametro coincide con el formato
     * de un telefono.
     * 
     * @param number Numero de telefono
     * @return {@code true} Si el formato es correcto. 
     */
    public Boolean checkPhoneNumber(String number) {
        Matcher matcher = patternPhoneNumber.matcher(number);
        return matcher.matches();
    }

    /**
     * Metodo inicio de sesion que evalua los datos ingresados por los parametros.
     * 
     * @param username Nombre del usuario
     * @param password Contraseña del usuario
     * s
     * @return {@code true} si existe el usuario y su contraseña es correcta.
     */
    public Boolean Login(String username, String password) {
        Document response = database.findOne("users", "username", username);
        String passwordEncrypted = encryption.getMD5(password);
        
        if(response == null) {
            return false;
        } else {
            try {
                JSONObject mainObject = new JSONObject(response.toJson().toString());
                
                if(passwordEncrypted.equals(mainObject.getString("password"))) {
                    return true;
                } else {
                    return false;
                }
    
            } catch (JSONException e) {
                return false;
            }
        }
    }

    /**
     * Metodo registro que crea un documento y lo manda hacia
     * la base de datos.
     * 
     * @param firstname Nombre
     * @param lastname Apellido
     * @param username Nombre de usuario
     * @param email Correo Electronico
     * @param phone_number Numero de telefono
     * @param password Contraseña del usuario
     * 
     * @return {@code true} Si los datos del nuevo usuario no estan en uso por otro usuario.
     */
    public Boolean Register(String firstname, String lastname, String username, String email, String phone_number, String password) {
        try {
        String passwordEncrypted = encryption.getMD5(password);

        Document newUser = new Document("_id", new ObjectId())
        .append("firstname", firstname)
        .append("lastname", lastname)
        .append("username", username)
        .append("email", email)
        .append("phone_number", phone_number)
        .append("password", passwordEncrypted);

        database.insertOne("users", newUser);
        return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Metodo registro que crea un documento y lo manda hacia
     * la base de datos.
     * 
     * @param firstname Nombre
     * @param lastname Apellido
     * @param username Nombre de usuario
     * @param email Correo Electronico
     * @param phone_number Numero de telefono
     * 
     * @return {@code true} Si los datos del nuevo usuario no estan en uso por otro usuario.
     */
    public Boolean Register(String firstname, String lastname, String username, String email, String phone_number) {
        try {
        Document newUser = new Document("_id", new ObjectId())
        .append("firstname", firstname)
        .append("lastname", lastname)
        .append("username", username)
        .append("email", email)
        .append("phone_number", phone_number);

        database.insertOne("users", newUser);
        return true;
        } catch (Exception e) {
            return false;
        }
    }
}
