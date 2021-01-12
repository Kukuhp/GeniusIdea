package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class GetIconResponse {
//    @SerializedName("id")
//    private String id;
//
//    @SerializedName("id_facility")
//    private String id_facility;
//
//    @SerializedName("id_room")
//    private String id_room;
//
//    @SerializedName("status")
//    private String status;

    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    public GetIconResponse(String icon,String name){
        this.icon = icon;
//        this.id = id;
//        this.id_facility = id_facility;
//        this.id_room = id_room;
        this.name = name;
    //    this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
