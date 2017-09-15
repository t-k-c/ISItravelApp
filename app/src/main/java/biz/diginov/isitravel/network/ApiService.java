package biz.diginov.isitravel.network;

import biz.diginov.isitravel.network.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login/")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("username") String username, @Field("code") String code);

}
