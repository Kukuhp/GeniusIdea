package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHomeMenu {
    @SerializedName("DailyRent")
    private List<Get_All_Picture_response> dailyRent;
    @SerializedName("eventSpace")
    private List<ListEventSpace> eventSpaces;
    @SerializedName("officeSpace")
    private List<ListOfficeSpace> officeSpaces;
    @SerializedName("livingSpace")
    private List<ListLivingSpace> livingSpaces;

    public ResponseHomeMenu(List<Get_All_Picture_response> dailyRent,List<ListEventSpace> eventSpaces,List<ListOfficeSpace> officeSpaces
            ,List<ListLivingSpace> livingSpaces){
        this.dailyRent = dailyRent;
        this.eventSpaces = eventSpaces;
        this.livingSpaces = livingSpaces;
        this.officeSpaces = officeSpaces;
    }

    public List<Get_All_Picture_response> getDailyRent() {
        return dailyRent;
    }

    public void setDailyRent(List<Get_All_Picture_response> dailyRent) {
        this.dailyRent = dailyRent;
    }

    public List<ListEventSpace> getEventSpaces() {
        return eventSpaces;
    }

    public List<ListLivingSpace> getLivingSpaces() {
        return livingSpaces;
    }

    public List<ListOfficeSpace> getOfficeSpaces() {
        return officeSpaces;
    }
}
