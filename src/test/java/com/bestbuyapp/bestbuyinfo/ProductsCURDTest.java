package com.bestbuyapp.bestbuyinfo;

import com.bestbuyapp.constants.EndPoints;
import com.bestbuyapp.model.ProductsPojo;
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

@RunWith(SerenityRunner.class)
public class ProductsCURDTest extends TestBase
{
    static String name = "Kottak-AAA Batteries (10-Pack)" + TestUtils.getRandomValue();
    static String type = "HardGood";
    static double price = 2.99;
    static double shipping = 1.10;
    static String upc = "0987543" + TestUtils.getRandomValue();
    static String description = "Compatible with select electronic devices; 10 pack";
    static String manufacturer = "Kottak";
    static String model = "001" + TestUtils.getRandomValue();
    static String url = "http://www.bestbuy.com";
    static String image = TestUtils.getRandomValue() + ".jpg";
    static int id;

    @Title("This will create a new product")
    @Test
    public void test001()
    {
        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setShipping(shipping);
        productsPojo.setUpc(upc);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .body(productsPojo)  //you can pass body here as well
                .when()
                .post()
                .then().log().all().statusCode(201);
    }

    @Title("Verify if the product was added to the application")
    @Test
    public void test002() {
        String p1 = "data.findAll{it.name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .when()
                        .get(EndPoints.GET_ALL_PRODUCTS)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + name + p2);

        assertThat(value, hasValue(name));
        System.out.println(value);
        id = (int) value.get("id");
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003() {
        String p1 = "data.findAll{it.name=='";
        String p2 = "'}.get(0)";

        name = name + "_Updated";
        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setShipping(shipping);
        productsPojo.setUpc(upc);
        productsPojo.setDescription(description);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setModel(model);
        productsPojo.setUrl(url);
        productsPojo.setImage(image);

        SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .body(productsPojo)  //you can pass body here as well
                .when()
                .put(EndPoints.UPDATE_PRODUCTS_BY_ID)
                .then().log().all().statusCode(200);
        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .when()
                        .get(EndPoints.GET_ALL_PRODUCTS)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + name + p2);

        assertThat(value, hasValue(name));
        System.out.println(value);
        id = (int) value.get("id");
        System.out.println(value);
    }

    @Title("Delete the product and verify if the Product is deleted!")
    @Test
    public void test004()
    {
        SerenityRest.rest()
                .given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_PRODUCTS_BY_ID)
                .then()
                .statusCode(204);

        SerenityRest.rest()
                .given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCTS_BY_ID)
                .then()
                .statusCode(404);
    }
}
