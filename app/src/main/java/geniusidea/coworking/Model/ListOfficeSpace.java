package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class ListOfficeSpace {
    @SerializedName("url_pict")
    private String url_pict;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("city")
    private String city;
    public ListOfficeSpace(String url_pict,String kategori,String city){
        this.city = city;
        this.kategori = kategori;
        this.url_pict = url_pict;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getUrl_pict() {
        return url_pict;
    }

    public void setUrl_pict(String url_pict) {
        this.url_pict = url_pict;
    }
}
