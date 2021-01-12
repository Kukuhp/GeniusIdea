package geniusidea.coworking.LocalDatabase;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Data_User_Token extends SugarRecord {
@Unique
public String token;

public Data_User_Token() {

}

public Data_User_Token(String token) {
this.token = token;
}

}