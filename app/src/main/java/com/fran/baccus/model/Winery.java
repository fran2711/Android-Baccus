package com.fran.baccus.model;

import android.os.Build;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Winery {

    public enum WineType {
        RED, WHITE, ROSE, OTHER
    }

    private static final String winesURL = "http://static.keepcoding.io/baccus/wines.json";

    private static Winery sInstance = null;

    private List<Wine> mWinesList = null;
    private HashMap<WineType, List<Wine>> mWinesByType = null;

    public static Winery getInstance() {
        if (sInstance == null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                sInstance = downloadWines();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return sInstance;
    }

    public static boolean isInstanceAvailable() {
        return sInstance != null;
    }

    private static Winery downloadWines() throws IOException, JSONException {
        Winery winery = new Winery();
        winery.mWinesList = new LinkedList<Wine>();
        winery.mWinesByType = new HashMap<>();
        for (WineType type : WineType.values()) {
            winery.mWinesByType.put(type, new LinkedList<Wine>());
        }


        URLConnection conn = new URL(winesURL).openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONArray wines = new JSONArray(response.toString());

        for (int wineIndex = 0; wineIndex < wines.length(); wineIndex++) {
            String id = null;
            String name = null;
            String type = null;
            String company = null;
            String companyWeb = null;
            String notes = null;
            int rating = 0;
            String origin = null;
            String picture = null;

            JSONObject jsonWine = wines.getJSONObject(wineIndex);
            if (jsonWine.has("name")) {
                id = jsonWine.getString("_id");
                name = jsonWine.getString("name");
                type = jsonWine.getString("type");
                company = jsonWine.getString("company");
                companyWeb = jsonWine.getString("company_web");
                notes = jsonWine.getString("notes");
                rating = jsonWine.getInt("rating");
                origin = jsonWine.getString("origin");
                picture = jsonWine.getString("picture");

                Wine wine = new Wine(id, name, type, picture, company, companyWeb, notes, origin, rating);
                JSONArray jsonGrapes = jsonWine.getJSONArray("grapes");
                for (int grapeIndex = 0; grapeIndex < jsonGrapes.length(); grapeIndex++) {
                    wine.addGrape(jsonGrapes.getJSONObject(grapeIndex).getString("grape"));
                }

                if (type.equalsIgnoreCase("tinto")) {
                    winery.mWinesByType.get(WineType.RED).add(wine);
                }
                else if (type.equalsIgnoreCase("blanco")) {
                    winery.mWinesByType.get(WineType.WHITE).add(wine);
                }
                else if (type.equalsIgnoreCase("rosado")) {
                    winery.mWinesByType.get(WineType.ROSE).add(wine);
                }
                else {
                    winery.mWinesByType.get(WineType.OTHER).add(wine);
                }
            }
        }

        for (WineType type : WineType.values()) {
            List<Wine> wineList = winery.mWinesByType.get(type);
            winery.mWinesList.addAll(wineList);
        }

        return winery;
    }

    public Wine getWine(int index) {
        return mWinesList.get(index);
    }

    public Wine getWine(WineType type, int index) {
        return mWinesByType.get(type).get(index);
    }

    public int getWineCount() {
        return mWinesList.size();
    }

    public int getWineCount(WineType type) {
        return mWinesByType.get(type).size();
    }

    public List<Wine> getWineList() {
        return mWinesList;
    }

    public int getAbsolutePosition(WineType type, int relativePosition) {
        Wine wine = getWine(type, relativePosition);
        return mWinesList.indexOf(wine);
    }
}
