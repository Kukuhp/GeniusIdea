package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SearchOffice {
    @SerializedName("id")
    private String id;
    @SerializedName("nama_tempat")
    private String nama_tempat;
    @SerializedName("main_picture")
    private String main_picture;
    @SerializedName("facilities")
    private String facilities;
    @SerializedName("city")
    private String city;
    @SerializedName("latitude")
    private String lat;
    @SerializedName("longitude")
    private String longi;
    @SerializedName("address")
    private String address;
    @SerializedName("price")
    private String price;
    @SerializedName("description")
    private String description;
    @SerializedName("floorPlans")
    private String floorPlans;
    @SerializedName("photos")
    private List<ListOfficeSpace> photos;
    @SerializedName("availableRoom")
    private List<ReponseRoomDetail> availableRoom;
    @SerializedName("room_facilities")
    private List<GetIconResponse> IconResponses;

    public SearchOffice(String address,String city,String description,String facilities,String floorPlans,String id,String lat,String longi,
                         String main_picture,String nama_tempat,String price,List<ListOfficeSpace> photos,List<ReponseRoomDetail> availableRoom,
                        List<GetIconResponse> IconResponses){
        this.address = address;
        this.city = city;
        this.description = description;
        this.facilities = facilities;
        this.floorPlans = floorPlans;
        this.id = id;
        this.lat = lat;
        this.longi = longi;
        this.main_picture = main_picture;
        this.nama_tempat = nama_tempat;
        this.price = price;
        this.photos = photos;
        this.availableRoom = availableRoom;
        this.IconResponses = IconResponses;
    }

    public List<GetIconResponse> getIconResponses() {
        return IconResponses;
    }

    public List<ListOfficeSpace> getPhotos() {
        return photos;
    }

    public List<ReponseRoomDetail> getAvailableRoom() {
        return availableRoom;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getFloorPlans() {
        return floorPlans;
    }

    public String getLat() {
        return lat;
    }

    public String getLongi() {
        return longi;
    }

    public String getMain_picture() {
        return main_picture;
    }

    public String getNama_tempat() {
        return nama_tempat;
    }

    public String getPrice() {
        return price;
    }
}
