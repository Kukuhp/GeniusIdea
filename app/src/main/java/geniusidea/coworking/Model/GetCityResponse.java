package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class GetCityResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("picture")
    private String picture;

    @SerializedName("status")
    private String status;

    public GetCityResponse(String id,String name,String picture,String status){
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }
}
