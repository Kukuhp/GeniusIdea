package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class PostIdOffice {
    @SerializedName("flag")
    private String flag;

    @SerializedName("id_office")
    private String idOffice;

    public PostIdOffice(String idOffice,String flag){
        this.idOffice = idOffice;
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(String idOffice) {
        this.idOffice = idOffice;
    }
}
