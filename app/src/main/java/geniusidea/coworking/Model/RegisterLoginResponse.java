package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterLoginResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("id")
    private String id;

    public RegisterLoginResponse(String status,String id){
        this.status = status;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
