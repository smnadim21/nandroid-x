package com.smnadim21.nadx.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Routes {

    String version = "v2_1";

    @Headers({"Accept: application/json"})
    @POST("api/login")
    Call<String> login(
            @Query("email") String email,
            @Query("password") String password,
            @Query("fcm_token") String fcm_token);


    //http://army-pharma.datasysbd.net/api/drug-stores
    //--------CREATE DRUG STORE----------
    @Headers({"Accept: application/json; Content-Type: application/json"})
    @POST("api/drug-stores")
    Call<String> createDrugStore(
            @Query("store_name") String store_name,
            @Query("contact_name") String contact_name,
            @Query("contact_number") String contact_number,
            @Query("address") String address,
            @Query("lat") String lat,
            @Query("long") String lng,
            @Header("Authorization") String auth
    );

    @Headers({"Accept: application/json"})
    @GET("api/drug-stores")
    Call<String> getDrugStore(@Header("Authorization") String auth);


    //getAvailable Medicine
    @Headers({"Accept: application/json; Content-Type: application/json"})
    @GET("api/drugs/available")
    Call<String> getAvailableMedicine(@Header("Authorization") String auth);


 /*   @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/orders")
    Call<JSONObject> orderDrug(
            @Body OrderModel orderString,
            @Header("Authorization") String auth);

    //{{base_url}}/api/orders/calculate
    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/orders/calculate")
    Call<JsonObject> calculate(
            @Body OrderModel orderString,
            @Header("Authorization") String auth);*/


    //{{base_url}}/api/orders?status=approved&delivery_status=delivered

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/orders")
    Call<String> getOrderList(
            @Query("orders") String orders,
            @Query("delivery_status") String delivery_status,
            @Header("Authorization") String auth);


    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/orders")
    Call<String> getOrderList(
            @Header("Authorization") String auth);

    //{{base_url}}/api/orders/1/canceled

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/orders/{id}/canceled")
    Call<String> cancelOrderList(
            @Path("id") String id,
            @Header("Authorization") String auth);

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/orders/{id}/canceled")
    Call<String> cancelOrderList(
            @Path("id") String id,
            @Query("code") String code,
            @Header("Authorization") String auth);


    ////////////DM//////////////////////////////////


    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/deliveries")
    Call<String> getDeliverList(
            @Header("Authorization") String auth);

    //{{base_url}}/api/deliveries/1

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/deliveries/{id}")
    Call<String> getSingleDeliverDetail(
            @Path("id") String id,
            @Header("Authorization") String auth);

    //{{base_url}}/api/deliveries/1/delivered

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/deliveries/{id}/delivered")
    Call<String> confirmDelivery(
            @Path("id") String id,
            @Header("Authorization") String auth);

    //{{base_url}}/api/deliveries/1/canceled

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/deliveries/{id}/canceled")
    Call<String> cancelDelivery(
            @Path("id") String id,
            @Header("Authorization") String auth);


    //{{base_url}}/api/invoices

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/invoices")
    Call<String> getInvoiceList(
            @Header("Authorization") String auth);

    //{{base_url}}/api/invoices/1

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/invoices/{id}")
    Call<String> getSingleInvoiceDetail(
            @Path("id") String id,
            @Header("Authorization") String auth);

    //{{base_url}}/api/invoices/1/collected

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/invoices/{id}/collected")
    Call<String> collectInvoice(
            @Path("id") String id,
            @Query("payment_type") String payment_type,
            @Header("Authorization") String auth);


    //===============GLOBALS========================

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/{action}")
    Call<String> getList(
            @Path("action") String action,
            @Header("Authorization") String auth);

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/{action}")
    Call<String> getList(
            @Path("action") String action,
            @Query("status") String status,
            @Header("Authorization") String auth);


    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/{action}/{id}")
    Call<String> getSingleDetail(
            @Path("action") String action,
            @Path("id") String id,
            @Header("Authorization") String auth);


    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/logout")
    Call<String> logOut(
            @Query("fcm_token") String token,
            @Header("Authorization") String auth);

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/")
    Call<String> getSession(
            @Header("Authorization") String auth);

    //{{base_url}}/api/notifications
    @Headers({"Accept: application/json;Content-Type: application/json"})
    @GET("api/{action}")
    Call<String> getNotification(
            @Path("action") String action,
            @Header("Authorization") String auth);

    // edit Orders========================================================================

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/{action}/{id}/canceled")
    Call<String> cancelRequest(
            @Path("action") String action,
            @Path("id") String id,
            @Header("Authorization") String auth);

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/{action}/{id}/canceled")
    Call<String> cancelConfirm(
            @Path("action") String action,
            @Path("id") String id,
            @Query("code") String code,
            @Header("Authorization") String auth);

    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/{action}/{id}")
    Call<String> editRequest(
            @Path("action") String action,
            @Path("id") String id,
            @Header("Authorization") String auth);


/*
    @Headers({"Accept: application/json;Content-Type: application/json"})
    @POST("api/{action}/{id}")
    Call<JSONObject> editConfirm(
            @Path("action") String action,
            @Path("id") String id,
            @Body  OrderModel orderString,
            @Header("Authorization") String auth);
*/



/*

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/patient_profile_get")
        //TODO /v1/patient/profile/get
    Call<PatientProfileModel> getPatientProfile(@Query("id") String id, @Header("Authorization") String auth);


    // @Headers({"Accept: application/json"})
    @Multipart
    @POST("api/patient_update")
    Call<UpdateProfileDataModel> updateProfilewithImage(@PartMap HashMap<String, RequestBody> partMap, @Part MultipartBody.Part file, @Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @POST("api/patient_update")
    Call<UpdateProfileDataModel> updateProfile(@Body UpdateProfileDataModel updateProfileDataModel, @Header("Authorization") String auth);


    @Headers({"Accept: application/json"})
    @GET("api/v1/nearby/doctors/get")
    Call<List<NearbyDoctorModel>> getNearByDoctors(@Query("longitude") String longitude, @Query("latitude") String latitude, @Query("category_id") String catid, @Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @GET("api/v1/categories/get")
    Call<List<CategoriesModel>> getMenuCategories(@Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @POST("api/consultation")
    Call<ConsultModel.Consult> consultDoc(@Query("doctor_id") String doc_id, @Query("patient_id") String pat_id, @Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @GET("api/patient_consultation_history")
    Call<List<ConsultationModel>> requestConsultationHistory(@Query("id") String id, @Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @POST("api/consultation")
    Call<ConsultModel.Consult> changeConsultationStatus(@Query("id") String id, @Query("status") String status, @Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @POST("api/consultation")
    Call<ConsultModel.Consult> sendPatientReview(@Query("id") String id, @Query("is_happy") int is_happy, @Query("patient_feedback") String feedback, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/v1/patient/doctor_profile/get")
        //TODO api/v1/patient/doctor_profile/get --- doctor_profile_get
    Call<DoctorProfileDataModel> getDoctorProfile(@Query("id") String id, @Header("Authorization") String auth);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/doctor_feedback_get")
    Call<FeedbackModel> getDoctorFeedback(@Query("id") String id, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/v1/patient/favorites/get")
    Call<List<DoctorProfileDataModel2>> getFavDoctor(@Query("id") String id, @Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @POST("api/v1/patient/favorite/update")
    Call<FavModel> addToFav(@Query("id") String id, @Query("doctor_id") String doc_id, @Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @GET("api/v1/patient/messages/get")
    Call<List<MessageData>> getMessage(@Query("id") String id, @Header("Authorization") String auth);

    //===================================video Call=================================================================================================
    @Headers({"Accept: application/json"})
    @GET("api/" + version + "/video_call/doctors/list")
    Call<DoctorsList> getDoctorList(@Query("id") String id, @Query("category_id") String category_id, @Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @GET("api/" + version + "/video_call/make")
    Call<String> makeCall(@Query("patient_id") String id, @Query("doctor_id") String doc_id, @Header("Authorization") String auth);


    @Headers({"Accept: application/json"})
    @GET("api/" + version + "/video_call/payment/make")
    Call<Base> makePayment(@Query("patient_id") String id, @Query("doctor_id") String doc_id, @Query("payment_amount") String payment_amount, @Header("Authorization") String auth);

    // {{base_url}}/api/v1/video_call/doctors/list?api_token={{api_token_pat}}&patient_id=1&category_id=1
    //fetch doctors


    //{{base_url}}/api/v2_1/video_call/min/track?api_token={{api_token_pat}}&call_id=7&use_min=1
    // track min //

    @Headers({"Accept: application/json"})
    @GET("api/" + version + "/video_call/min/track")
    Call<String> trackMin(@Query("call_id") String call_id, @Query("use_min") String use_min, @Header("Authorization") String auth);


    //{{base_url}}/api/v2_1/video_call/finish?patient_id=7&call_id=1

    @Headers({"Accept: application/json"})
    @GET("api/" + version + "/video_call/finish")
    Call<String> finish(@Query("call_id") String call_id, @Query("patient_id") String patient_id, @Header("Authorization") String auth);*/


}


