package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class HomeResponse {
    @SerializedName("flag")
    private String flag;

    public HomeResponse(String flag){
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
