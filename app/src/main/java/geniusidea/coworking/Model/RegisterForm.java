package geniusidea.coworking.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterForm {
    @SerializedName("flag")
    private String flag;

    @SerializedName("name")
    private String nama;

    @SerializedName("nama_perusahaan")
    private String nama_perusahaan;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("domisili")
    private String domisili;

    @SerializedName("alamat_perusahaan")
    private String alamat_perusahaan;

    @SerializedName("password")
    private String password;;

    @SerializedName("token")
    private String token;

    @SerializedName("email")
    private String email;

    @SerializedName("noHp")
    private String noHp;


    public RegisterForm(String flag,String email,String alamat_perusahaan,String domisili,String jabatan,String nama,String nama_perusahaan,String password
            ,String token,String noHp){
        this.alamat_perusahaan = alamat_perusahaan;
        this.domisili = domisili;
        this.jabatan = jabatan;
        this.nama = nama;
        this.nama_perusahaan = nama_perusahaan;
        this.password = password;
        this.token = token;
        this.flag = flag;
        this.email = email;
        this.noHp = noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setAlamat_perusahaan(String alamat_perusahaan) {
        this.alamat_perusahaan = alamat_perusahaan;
    }

    public void setDomisili(String domisili) {
        this.domisili = domisili;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNama_perusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
