package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReponseRoomDetail {
    @SerializedName("id")
    private String id;
    @SerializedName("nama_ruangan")
    private String namaRuangan;
    @SerializedName("id_office")
    private String idOffice;
    @SerializedName("main_picture")
    private String main_picture;
    @SerializedName("facilities")
    private String facilities;
    @SerializedName("price")
    private String price;
    @SerializedName("room_capacity")
    private String roomCapacity;
    @SerializedName("room_size")
    private String roomSize;
    @SerializedName("room_plan")
    private String roomPlan;
    @SerializedName("about_room")
    private String aboutRoom;

    public ReponseRoomDetail(String price,String main_picture,String id,String facilities,String aboutRoom,String idOffice,
                             String namaRuangan,String roomCapacity,String roomPlan,String roomSize){
        this.aboutRoom = aboutRoom;
        this.facilities = facilities;
        this.id = id;
        this.idOffice = idOffice;
        this.main_picture = main_picture;
        this.namaRuangan = namaRuangan;
        this.price = price;
        this.roomCapacity = roomCapacity;
        this.roomPlan = roomPlan;
        this.roomSize = roomSize;
    }

    public String getPrice() {
        return price;
    }

    public String getMain_picture() {
        return main_picture;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getId() {
        return id;
    }

    public String getAboutRoom() {
        return aboutRoom;
    }

    public String getIdOffice() {
        return idOffice;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public String getRoomCapacity() {
        return roomCapacity;
    }

    public String getRoomPlan() {
        return roomPlan;
    }

    public String getRoomSize() {
        return roomSize;
    }
}
