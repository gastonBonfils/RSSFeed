package parser;

/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 * */

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;


import subscription.SingleSubscription;
import subscription.Subscription;
 
/* El parser deberia parsear suscriptions y devolver el objeto Subscription 
(funciona como una lista de suscripciones)
Para ello se hace lo siguiente:
    Se lee el achrivo de suscripciones
    Hacemos un bucle en el que vayamos leyende cada pag con sus categorias
    Por cada pagina, se crea un objeto de tipo Subscription
    Se añade a el Objeto Suscription
    */

/* The */
public class SubscriptionParser extends GeneralParser<Subscription> {
    public Subscription parse(String path) {

        // Creation of the object Subscription
        Subscription subscription = new Subscription(path);
        try {
            // Read the JSON file
            String jsonContent = new String(Files.readAllBytes(Paths.get(path)));

            // Parse the JSON
            JSONArray jsonArray = new JSONArray(jsonContent);

            // Process each object in the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String url = jsonObject.getString("url");
                JSONArray urlParams = jsonObject.getJSONArray("urlParams");
                String urlType = jsonObject.getString("urlType");

                // Turn JSONArray to List<String>
                List<String> urlParamsList = new ArrayList<String>();
                for (int j = 0; j < urlParams.length(); j++) {
                    urlParamsList.add(urlParams.getString(j));
                }

                SingleSubscription singleSubscription = new SingleSubscription(url, urlParamsList, urlType);
                subscription.addSingleSubscription(singleSubscription);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscription;
    }
}

