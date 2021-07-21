package com.bestbuyapp.bestbuyinfo;

import com.bestbuyapp.constants.EndPoints;
import com.bestbuyapp.model.CategoriesPojo;
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
public class CategoriesCURDTest extends TestBase
{
    static String name = "Radios" + TestUtils.getRandomValue();
    static String id = "abc123" + TestUtils.getRandomValue();

    @Title("This will create a new category")
    @Test
    public void test001()
    {
        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setName(name);
        categoriesPojo.setId(id);

        SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .body(categoriesPojo)  //you can pass body here as well
                .when()
                .post()
                .then().log().all().statusCode(201);
    }

    @Title("Verify if the category was added to the application")
    @Test
    public void test002() {
        String p1 = "findAll{it.id=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .when()
                        .get(EndPoints.GET_ALL_CATEGORIES)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + id + p2);

        assertThat(value, hasValue(id));
        System.out.println(value);
    }

    @Title("Update the category information and verify the updated information")
    @Test
    public void test003()
    {
        String p1 = "findAll{it.id=='";
        String p2 = "'}.get(0)";

        name = name + "_Updated";
        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setName(name);
        categoriesPojo.setId(id);

        SerenityRest.rest()
                .given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .body(categoriesPojo)  //you can pass body here as well
                .when()
                .put(EndPoints.UPDATE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);

        HashMap<String, Object> value =
                SerenityRest.rest()
                        .given().log().all()
                        .when()
                        .get(EndPoints.GET_ALL_CATEGORIES)
                        .then()
                        .statusCode(200)
                        .extract()
                        .path(p1 + id + p2);

        assertThat(value, hasValue(id));
        System.out.println(value);
    }

    @Title("Delete the category and verify if the category is deleted!")
    @Test
    public void test004()
    {
        SerenityRest.rest()
                .given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_CATEGORIES_BY_ID)
                .then()
                .statusCode(200);

        SerenityRest.rest()
                .given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then()
                .statusCode(404);
    }
}
