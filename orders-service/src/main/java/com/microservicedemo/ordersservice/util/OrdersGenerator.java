package com.microservicedemo.ordersservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicedemo.ordersservice.model.Address;
import com.microservicedemo.ordersservice.model.AddressGen;
import com.microservicedemo.ordersservice.model.AddressUnit;
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
public class OrdersGenerator {

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
              OrdersGenerator.class.getClassLoader()
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
        .readValue(OrdersGenerator.class.getClassLoader()
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

  public void genOrderXmlFiles(int filesCount, int recordCount) throws IOException {
    for (int i = 1; i <= filesCount; i++) {
      genOrderXmlFile(recordCount);
    }
  }

  void genOrderXmlFile(int recordCount) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    sb.append("<orders>\n");
    for (int i = 1; i < recordCount; i++) {
      sb.append(generateOrderRecord());
      sb.append("\n");
    }
    sb.append("</orders>\n");
    String uuid = UUID.randomUUID().toString();
    FileWriter fw = new FileWriter(
        "orders/order-" + uuid + ".xml");
    fw.write(sb.toString());
    fw.close();
  }

  private StringBuilder generateOrderRecord() {
    StringBuilder orderRecord = new StringBuilder();

    orderRecord.append("<order id=").append("\"").append(UUID.randomUUID().toString())
        .append("\"")
        .append(">")
        .append("\n");

    String fileCreatedDateTime = "20200" + getRandomInt(1, 9) + getRandomInt(10, 30)
        + "0" + getRandomInt(1, 9) + getRandomInt(10, 59) + getRandomInt(10, 59);

    orderRecord.append("<FileCreatedDateTime>").append(fileCreatedDateTime)
        .append("</FileCreatedDateTime>").append("\n");

    orderRecord.append("<Firstname>").append(cacheMap.get(FIRST_NAMES)
        .get(new Random().nextInt(cacheMap.get(FIRST_NAMES).size()))).append("</Firstname>")
        .append("\n");
    orderRecord.append("<Lastname>").append(cacheMap.get(LAST_NAMES)
        .get(new Random().nextInt(cacheMap.get(LAST_NAMES).size()))).append("</Lastname>")
        .append("\n");

    Address address = addresses.get(getRandomInt(0, addresses.size()));
    orderRecord.append("<Address>").append(address.getAddress1()).append("</Address>")
        .append("\n");
    orderRecord.append("<City>").append(address.getCity()).append("</City>").append("\n");
    orderRecord.append("<State>").append(address.getState()).append("</State>")
        .append("\n");
    orderRecord.append("<Zip>").append(address.getZipCode()).append("</Zip>")
        .append("\n");

    orderRecord.append("<orderTotal>").append(getRandomInt(10, 1000)).append("</orderTotal>")
        .append("\n");
    orderRecord.append("<orderItems>").append(getRandomInt(10, 100)).append("</orderItems>")
        .append("\n");

    String expectedDeliveryDate = "20200" + getRandomInt(1, 9) + getRandomInt(10, 30)
        + "0" + getRandomInt(1, 9) + getRandomInt(10, 59) + getRandomInt(10, 59);

    orderRecord.append("<expectedDelivery>").append(expectedDeliveryDate)
        .append("</expectedDelivery>")
        .append("\n");
    orderRecord.append("<orderPaymentType>").append("VISA").append("</orderPaymentType>")
        .append("\n");

    // Whitespace for XML file
    if (getRandomBoolean()) {
      orderRecord.append("<Email>")
          .append(cacheMap.get(EMAILS).get(new Random().nextInt(cacheMap.get(EMAILS).size())))
          .append("</Email>").append("\n");
    } else {
      orderRecord.append("<Email>")
          .append(" ")
          .append("</Email>").append("\n");
    }

    orderRecord.append("</order>");

    return orderRecord;
  }
}
