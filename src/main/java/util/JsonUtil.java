package util;

import javafx.scene.image.Image;
import model.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by LocalAccount on 2016/11/16.
 * a util class, singleton access. providing some functions to read configurations from the json file
 */

public class JsonUtil {

    /**
     * use the inner class to achieve singleton access, read the whole json String from the json file during the
     * the creation of the JsonUtil object.
     */
    private JsonUtil() {
        this.parser = new JSONParser();
        get_text();
    }
    private static class Nested {
        private static JsonUtil instance = new JsonUtil();
    }
    public static JsonUtil getInstance() {
        return Nested.instance;
    }


    /**
     * two attributes. 1>. parser: JSONParser to parse json object 2>. text: the whole json String read from the json file
     */
    private JSONParser parser = null;
    private String text = null;

    private JSONParser getParser() {
        return this.parser;
    }


    /**
     * read the whole json String from the json file to initialise the text attribute
     */
    private void get_text() {
        InputStream inputStream = JsonUtil.class.getClassLoader().getResourceAsStream("data.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.text = sb.toString().trim();
    }


    /**
     * get all the items of a particular theme from the json file
     *
     * @param index
     * @return
     */
    public List<String> get_all_items(int index) {
        List<String> list = new ArrayList<>();
        try {
            Object obj = JsonUtil.getInstance().getParser().parse(this.text);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray array = (JSONArray) jsonObject.get("skin");
            JSONObject subObject = (JSONObject) array.get(index);
            JSONArray item_list = (JSONArray) subObject.get("item_list");
            Iterator<String> iterator = item_list.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * get the picker of a particular theme from the json file
     *
     * @param index
     * @return
     */
    public String get_picker(int index) {
        String picker = null;
        try {
            Object obj = JsonUtil.getInstance().getParser().parse(this.text);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray array = (JSONArray) jsonObject.get("skin");
            JSONObject subObject = (JSONObject) array.get(index);
            picker = (String) subObject.get("picker");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picker;
    }


    /**
     * get the initial location and initial direction from the json file
     *
     * @return
     */
    public String get_loc_and_direc() {
        try {
            Object obj = JsonUtil.getInstance().getParser().parse(this.text);
            JSONObject jsonObject = (JSONObject) obj;
            String location = (String) jsonObject.get("location");
            String direction = (String) jsonObject.get("direction");
            return location + "-" + direction;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get one particular picture by the picture name
     * @param picture_name
     * @return
     */
    public Image get_one_picture(String picture_name) {
        Image image = new Image("pictures/" + picture_name);
        return image;
    }


    /**
     * return a set containing the names of all pictures
     * @return
     */
    public Set<Location> get_all_locations() {
        Set<Location> locations = new HashSet<Location>();
        try {
            Object obj = JsonUtil.getInstance().getParser().parse(this.text);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray array = (JSONArray) jsonObject.get("pictures");
            Iterator<String> iterator = array.iterator();
            while (iterator.hasNext()) {
                locations.add(new Location(iterator.next()));
            }
            return locations;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
