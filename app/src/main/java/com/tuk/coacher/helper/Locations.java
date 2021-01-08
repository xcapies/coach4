package com.tuk.coacher.helper;

import com.tuk.coacher.R;

import java.util.ArrayList;
import java.util.List;

public class Locations {
    private ArrayList<String> locations = new ArrayList<>();
    private String[] locs = {"Ugunja", "Kaloleni", "kiamiko", "isinya", "Nakuru", "Nairobi", "Lamu",
            "Mombasa", "Munde", "Chaichai", "Komemn", "Nyeri", "kariko", "Eldoret", "Kinoo",
            "Naivasha", "Nyahururu", "Chuja", "Mbuzi", "Cow", "Paka", "kamlesh", "Ruto", "Covid", "China",
            "Haleluya", "Amen", "Moto", "Mtoot", "Mndi", "Honk", "Kibra", "Southee", "Manaje"
    };

    public Locations() {
        locations.add(0, "Choose a location");
    }

    public ArrayList<String> getFromLocations() {
        for (String t:locs) {
            locations.add(t);
        }
        return locations;
    }

    public List<String> getToLocations(String from) {
        locations = new ArrayList<>();
        locations.add(0, "Choose a location");
        for (String t:locs) {
            if(!t.toLowerCase().contentEquals(from.toLowerCase())){
                locations.add(t);
            }
        }
        return locations;
    }


}