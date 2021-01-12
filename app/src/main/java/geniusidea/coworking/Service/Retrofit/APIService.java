package geniusidea.coworking.Service.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import geniusidea.coworking.Model.GetCityResponse;
import geniusidea.coworking.Model.Get_All_Picture_response;
import geniusidea.coworking.Model.HomeResponse;
import geniusidea.coworking.Model.ListCoworking;
import geniusidea.coworking.Model.Login;
import geniusidea.coworking.Model.PostCity;
import geniusidea.coworking.Model.PostIdOffice;
import geniusidea.coworking.Model.RegisterForm;
import geniusidea.coworking.Model.RegisterLoginResponse;
import geniusidea.coworking.Model.ResponseHomeMenu;
import geniusidea.coworking.Model.SearchOffice;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST(" ")
    Call<ResponseHomeMenu> getPict(@Field("flag")String flag,
                                   @Field("userId")String userId,
                                   @Field("token")String token);

    @FormUrlEncoded
    @POST(" ")
    Call<RegisterLoginResponse> Register(@Field("flag") String flag,
                                         @Field("name") String nama,
                                         @Field("nama_perusahaan") String nama_perusahaan,
                                         @Field("jabatan") String jabatan,
                                         @Field("domisili") String domisili,
                                         @Field("alamat_perusahaan") String alamat_perusahaan,
                                         @Field("password") String password,
                                         @Field("token") String token,
                                         @Field("email") String email,
                                         @Field("noHp") String noHp);

    @FormUrlEncoded
    @POST(" ")
    Call<RegisterLoginResponse> Login(@Field("flag") String flag,
                                      @Field("email") String email,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST(" ")
    Call<List<ListCoworking>> getListCoworking (@Field("city")String city,
                                                @Field("flag")String flag);

    @FormUrlEncoded
    @POST(" ")
    Call<SearchOffice> detailOffice (@Field("flag") String flag,
                                     @Field("id_office") String idOffice);

    @FormUrlEncoded
    @POST(" ")
    Call<List<GetCityResponse>> getCity (@Field("flag")String flag);

}
