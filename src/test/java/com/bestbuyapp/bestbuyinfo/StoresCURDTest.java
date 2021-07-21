package com.bestbuyapp.bestbuyinfo;

import com.bestbuyapp.constants.EndPoints;
import com.bestbuyapp.model.StoresPojo;
import com.bestbuyapp.testbase.TestBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasValue;

//@RunWith(SerenityRunner.class)
public class StoresCURDTest extends TestBase
{
    static String name = "Shamz";
    static String type = "online store";
    static String address = "34 rugby avanue";
    static String address2 = "ealing road";
    static String city = "Wembley";
    static String state = "middlesex";
    static String zip = "ka1 3kd";
    static int lat = 0;
    static int lng = 0;
    static String hours = "247";
    static HashMap<String,Object> services = new HashMap<>();
    static int id;

    @Title("This will create a new store")
    @Test
    public void test001()
    {
        services.put("Name","toys");

        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(name);
        storesPojo.setType(type);
        storesPojo.setAddress(address);
        storesPojo.setAddress2(address2);
        storesPojo.setCity(city);
        storesPojo.setState(state);
        storesPojo.setZip(zip);
        storesPojo.setLat(lat);
        storesPojo.setLng(lng);
        storesPojo.setHours(hours);
        storesPojo.setServices(services);

        SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .body(storesPojo)  //you can pass body here as well
                .when()
                .post()
                .then().log().all().statusCode(201);
    }

    @Title("Verify if the store was added to the application")
    @Test
    public void test002() {
        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .when()
                        .get(EndPoints.GET_ALL_STORES)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + name + p2);

        assertThat(value, hasValue(name));
        System.out.println(value);
        id = (int) value.get("id");
    }

    @Title("Update the store information and verify the updated information")
    @Test
    public void test003() {
        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";

        name = name + "_Updated";
        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(name);
        storesPojo.setType(type);
        storesPojo.setAddress(address);
        storesPojo.setAddress2(address2);
        storesPojo.setCity(city);
        storesPojo.setState(state);
        storesPojo.setZip(zip);
        storesPojo.setLat(lat);
        storesPojo.setLng(lng);
        storesPojo.setHours(hours);
        storesPojo.setServices(services);

        SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .body(storesPojo)  //you can pass body here as well
                .when()
                .put(EndPoints.UPDATE_STORES_BY_ID)
                .then().log().all().statusCode(200);
        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .when()
                        .get(EndPoints.GET_ALL_STORES)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + name + p2);

        assertThat(value, hasValue(name));
        System.out.println(value);
        id = (int) value.get("id");
        System.out.println(value);
    }

    @Title("Delete the store and verify if the Product is deleted!")
    @Test
    public void test004()
    {
        SerenityRest.rest()
                .given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_STORES_BY_ID)
                .then()
                .statusCode(204);

        SerenityRest.rest()
                .given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_STORES_BY_ID)
                .then()
                .statusCode(404);
    }
}
