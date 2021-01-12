package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class RoomFacilitiesResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("id_facilty")
    private String id_facility;

    @SerializedName("id_room")
    private String id_room;

    @SerializedName("status")
    private String status;

    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    public  RoomFacilitiesResponse(String id,String id_facility,String id_room,String status,String name,String icon){
        this.icon = icon;
        this.id = id;
        this.id_facility = id_facility;
        this.id_room = id_room;
        this.name = name;
        this.status = status;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getId_facility() {
        return id_facility;
    }

    public String getId_room() {
        return id_room;
    }
}
