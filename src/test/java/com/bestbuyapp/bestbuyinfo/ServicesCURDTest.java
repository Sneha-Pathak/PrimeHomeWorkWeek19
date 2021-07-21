package com.bestbuyapp.bestbuyinfo;

import com.bestbuyapp.constants.EndPoints;
import com.bestbuyapp.model.CategoriesPojo;
import com.bestbuyapp.model.ProductsPojo;
import com.bestbuyapp.model.ServicesPojo;
import com.bestbuyapp.testbase.TestBase;
import com.bestbuyapp.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasValue;

//@RunWith(SerenityRunner.class)
public class ServicesCURDTest extends TestBase
{
    static String name = "Micro Services" + TestUtils.getRandomValue();
    static int id;

    @Title("This will create a new services")
    @Test
    public void test001()
    {
        ServicesPojo servicesPojo = new ServicesPojo();
        servicesPojo.setName(name);

        SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .body(servicesPojo)  //you can pass body here as well
                .when()
                .post()
                .then().log().all().statusCode(201);
    }

    @Title("Verify if the services was added to the application")
    @Test
    public void test002() {
        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .when()
                        .get(EndPoints.GET_ALL_SERVICES)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + name + p2);

        assertThat(value, hasValue(name));
        System.out.println(value);
        id = (int) value.get("id");
    }

    @Title("Update the service information and verify the updated information")
    @Test
    public void test003() {
        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";

        name = name + "_Updated";
        ServicesPojo servicesPojo = new ServicesPojo();
        servicesPojo.setName(name);

        SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .body(servicesPojo)  //you can pass body here as well
                .when()
                .put(EndPoints.UPDATE_SERVICES_BY_ID)
                .then().log().all().statusCode(200);
        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .when()
                        .get(EndPoints.GET_ALL_SERVICES)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + name + p2);

        assertThat(value, hasValue(name));
        System.out.println(value);
        id = (int) value.get("id");
    }

    @Title("Delete the services and verify if the services is deleted!")
    @Test
    public void test004()
    {
        SerenityRest.rest()
                .given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_SERVICES_BY_ID)
                .then()
                .statusCode(200);

        SerenityRest.rest()
                .given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then()
                .statusCode(200);
    }
}
