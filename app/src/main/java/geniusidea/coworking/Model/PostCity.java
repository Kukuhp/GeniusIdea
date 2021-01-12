package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class PostCity {
    @SerializedName("city")
    private String city;

    public PostCity (String city){
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
