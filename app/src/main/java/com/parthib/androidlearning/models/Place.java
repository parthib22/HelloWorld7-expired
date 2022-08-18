package com.parthib.androidlearning.models;

import java.io.Serializable;

public class Place implements Serializable {
    private String p_name, location, type, pic;


    public Place(){

    }

    public String getP_name() {
        return p_name;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getPic() {
        return pic;
    }
}
