package com.microservicedemo.product.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicedemo.product.model.Address;
import com.microservicedemo.product.model.AddressGen;
import com.microservicedemo.product.model.AddressUnit;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author sravantatikonda
 */
@Component
public class ProductsGenerator {

  private static final Random RANDOM = new Random();
  public static final String ADDRESSES_JSON = "addresses.json";
  private final Map<String, List<String>> cacheMap = new HashMap<>();
  private final List<Address> addresses = new ArrayList<>();
  public static final String FIRST_NAMES = "firstname";
  public static final String LAST_NAMES = "lastname";
  public static final String EMAILS = "emails";
  public static final String PRODUCTS = "products";
  public static final String PRODUCT_DESCS = "productdescriptions";

  public void loadCSVValues() throws Exception {
    if (cacheMap.isEmpty()) {
      List<String> randomStrings = new ArrayList<>();
      randomStrings.add(PRODUCTS);
      randomStrings.add(FIRST_NAMES);
      randomStrings.add(LAST_NAMES);
      randomStrings.add(EMAILS);
      randomStrings.add(PRODUCT_DESCS);

      for (String randomString : randomStrings) {
        if (cacheMap.get(randomString) == null) {
          List<String> values = new ArrayList<>();

          BufferedReader br = new BufferedReader(new InputStreamReader(
              ProductsGenerator.class.getClassLoader()
                  .getResourceAsStream("loaderinput/" + randomString + ".csv")));

          String line;
          while ((line = br.readLine()) != null) {
            values.add(line);
          }
          cacheMap.put(randomString, values);
        }
      }
      if (addresses.isEmpty()) {
        createAddressModels();
      }
    }
  }

  private double getRandomDouble(double min, double max) {
    return RANDOM.doubles(min, max).findFirst().getAsDouble();
  }

  private void createAddressModels() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    AddressGen addressGen = objectMapper
        .readValue(ProductsGenerator.class.getClassLoader()
            .getResourceAsStream("loaderinput/" + ADDRESSES_JSON), AddressGen.class);

    for (AddressUnit addressUnit : addressGen.getAddresses()) {
      Address address = new Address();
      if (!StringUtils.isEmpty(addressUnit.getCity())) {
        address.setCity(addressUnit.getCity());
        address.setState(addressUnit.getState());
        address.setZipCode(addressUnit.getPostalCode());
        address.setAddress1(
            addressUnit.getAddress1().contains("&") ? "sample address" : addressUnit.getAddress1());
        address.setAddress2(addressUnit.getAddress2());
        address.setCountry("US");
      } else {
        address.setCity("Waltham");
        address.setState("MA");
        address.setZipCode("02453");
        address.setCountry("US");
      }
      addresses.add(address);
    }
  }

  private int getRandomInt(int min, int max) {
    return RANDOM.ints(min, max).findFirst().getAsInt();
  }

  private Boolean getRandomBoolean() {
    return RANDOM.nextBoolean();
  }

  public void genProductXmlFile() throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    sb.append("<Products>\n");

    for (String prodNameAndCategory : cacheMap.get(PRODUCTS)) {
      String prodName = prodNameAndCategory.substring(0, prodNameAndCategory.indexOf(','));
      String prodCategory = prodNameAndCategory.substring(prodNameAndCategory.indexOf(','));
      sb.append(generateProductRecord(prodName, prodCategory));
      sb.append("\n");
    }

    sb.append("</Products>\n");
    FileWriter fw = new FileWriter(
        "products/product.xml");
    fw.write(sb.toString());
    fw.close();
  }

  private StringBuilder generateProductRecord(String prodName, String prodCategory) {
    StringBuilder productRecord = new StringBuilder();

    productRecord.append("<product productId=").append("\"").append(UUID.randomUUID())
        .append("\"")
        .append(">")
        .append("\n");

    productRecord.append("<productName>").append(prodName).append("</productName>");
    productRecord.append("<productCategory>").append(prodCategory).append("</productCategory>");

    productRecord.append("<productCost>").append(getRandomDouble(1.0, 1000))
        .append("</productCost>");
    productRecord.append("<productDescription>").append(cacheMap.get(PRODUCT_DESCS)
        .get(new Random().nextInt(cacheMap.get(PRODUCT_DESCS).size())))
        .append("</productDescription>").append("\n");
    productRecord.append("</product>");

    return productRecord;
  }

}
