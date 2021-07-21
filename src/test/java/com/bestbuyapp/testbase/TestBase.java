package com.bestbuyapp.testbase;


import com.bestbuyapp.constants.Path;
import com.bestbuyapp.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class TestBase {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init(){
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
        RestAssured.port = Integer.parseInt(propertyReader.getProperty("port"));
       RestAssured.basePath = Path.PRODUCTS;
//        RestAssured.basePath = Path.STORES;
//        RestAssured.basePath = Path.SERVICES;
//        RestAssured.basePath = Path.CATEGORIES;
    }

}
