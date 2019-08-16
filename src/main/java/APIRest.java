import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static spark.Spark.*;

public class APIRest {

    public static void main(String[] args) {

        port(4567);

        IUserService service = new UserServiceMapImpl();

        try {
            service.addUser(new User(1, "Leanne Graham", new Login("Bret", "1234"), "Sincere@april.biz", new Adress("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", new Geo("-37.3159", "81.1496")), "1-770-736-8031 x56442", "hildegard.org", new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")));
        } catch (APIException e) {
            e.printStackTrace();
        }

        get("Sites/:id/categories", (req, res) -> {

            res.type("application/json");
            User user = service.getUser(req.headers("username"));
            long token = Long.parseLong(req.headers("token"));
            if (user.validateToken(token)) {
                URL urlSites = new URL("http://localhost:8085/sites/" + req.params(":id") + "/categories");
                URLConnection urlConnectionSites = urlSites.openConnection();

                return conexion(urlConnectionSites,"GET");
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Token inválido."));
            }

        });

        get("/Sites", (req, res) -> {

            res.type("application/json");
            User user = service.getUser(req.headers("username"));
            long token = Long.parseLong(req.headers("token"));
            if (user.validateToken(token)) {
                URL urlSites = new URL("http://localhost:8085/sites");
                URLConnection urlConnectionSites = urlSites.openConnection();

                return conexion(urlConnectionSites,"GET");
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Token inválido."));
            }

        });

        post("/Users", (req, res) -> {

            res.type("application/json");
            Login login = new Login(req.headers("username"), req.headers("password"));
            User user = service.getUser((req.headers("username")));
            if (user.validate(login)) {
                user.setToken((long) (Math.random() * 100000));
                service.updateUser((req.headers("username")), user);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(user.getToken())));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Usuario o contraseña incorrectos."));
            }

        });

        post("/Users/user", (req, res) -> {

            res.type("application/json");
            User user = new Gson().fromJson(req.body(), User.class);
            if (!service.userExists(user.getLogin().getUsername())) {
                service.addUser(user);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(user)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El nombre de usuario ya existe."));
            }

        });

        put("/Users/user", (req, res) -> {

            res.type("application/json");
            User user = new Gson().fromJson(req.body(), User.class);
            if (service.userExists(user.getLogin().getUsername())&&user.validate(new Login(req.headers("username"),req.headers("password")))) {
                service.updateUser(user.getLogin().getUsername(),user);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(user)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Usuario y/o contraseña incorrectos."));
            }

        });

        delete("/Users/user", (req, res) -> {

            res.type("application/json");
            User user = new Gson().fromJson(req.body(), User.class);
            if (service.userExists(user.getLogin().getUsername())&&user.validate(new Login(req.headers("username"),req.headers("password")))) {
                service.deleteUser(user.getLogin().getUsername());
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(user)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Usuario y/o contraseña incorrectos."));
            }

        });

    }

    private static String conexion(URLConnection urlConnectionSites,String metodo) throws IOException {
        if (urlConnectionSites instanceof HttpURLConnection) {

            urlConnectionSites.setRequestProperty("Accept", "application/json");
            urlConnectionSites.setRequestProperty("User-Agent", "Mozilla/5.0");
            HttpURLConnection connectionSites = (HttpURLConnection) urlConnectionSites;
            connectionSites.setRequestMethod(metodo);
            BufferedReader inSites = new BufferedReader(new InputStreamReader(connectionSites.getInputStream()));

            Gson gsonSites = new Gson();
            Object[] categories = gsonSites.fromJson(inSites, Object[].class);

            //StringBuilder sb = new StringBuilder();
            //String line;
            //while ((line = inSites.readLine()) != null) {
            //sb.append(line);
            //}

            return new Gson().toJson(categories,Object[].class);
        } else {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "URL inválida."));
        }
    }

}
