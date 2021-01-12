package geniusidea.coworking.LocalDatabase;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Data_User_Login extends SugarRecord {
    @Unique
    public boolean Login;

    public Data_User_Login(){

    }
    public Data_User_Login(boolean Login){
        this.Login = Login;
    }
}
