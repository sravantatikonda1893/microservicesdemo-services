package com.microservicedemo.usersservice.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicedemo.usersservice.model.Address;
import com.microservicedemo.usersservice.model.AddressGen;
import com.microservicedemo.usersservice.model.AddressUnit;
import com.microservicedemo.usersservice.entity.User;
import com.microservicedemo.usersservice.repo.UserRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author sravantatikonda
 */
@Component
public class UsersGenerator {

  private static final Random RANDOM = new Random();
  public static final String ADDRESSES_JSON = "addresses.json";
  private final Map<String, List<String>> cacheMap = new HashMap<>();
  private final List<Address> addresses = new ArrayList<>();
  public static final String FIRST_NAMES = "firstname";
  public static final String LAST_NAMES = "lastname";
  public static final String EMAILS = "emails";

  @Autowired
  private UserRepository userRepository;

  public void loadCSVValues() throws Exception {
    List<String> randomStrings = new ArrayList<>();
    randomStrings.add(FIRST_NAMES);
    randomStrings.add(LAST_NAMES);
    randomStrings.add(EMAILS);
    for (String randomString : randomStrings) {
      if (cacheMap.get(randomString) == null) {
        List<String> values = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(
            UsersGenerator.class.getClassLoader()
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

  private void createAddressModels() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    AddressGen addressGen = objectMapper
        .readValue(UsersGenerator.class.getClassLoader()
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


  public List<User> loadUserTable(int count) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      User user = new User();
      Address address = addresses.get(getRandomInt(0, addresses.size()));
      user.setCity(address.getCity());
      user.setState(address.getState());
      user.setCountry("US");
      user.setZipCode(address.getZipCode());
      user.setFirstName(
          cacheMap.get(FIRST_NAMES).get(new Random().nextInt(cacheMap.get(FIRST_NAMES).size())));
      user.setLastName(
          cacheMap.get(LAST_NAMES).get(new Random().nextInt(cacheMap.get(LAST_NAMES).size())));
      // Empty for USER Table

      user
          .setEmail(cacheMap.get(EMAILS).get(new Random().nextInt(cacheMap.get(EMAILS).size())));

      String randomYear = String.valueOf(getRandomInt(1965, 2002));
      String randomDay = String.valueOf(getRandomInt(1, 30));
      String randomMonth = String.valueOf(getRandomInt(1, 12));
      user.setDateOfBirth(parseDate(randomYear + "-" + randomMonth + "-" + randomDay));
      user.setCreatedAt(ZonedDateTime.now());
      user.setUpdatedAt(ZonedDateTime.now());

      users.add(user);
    }
    return userRepository.saveAll(users);
  }

  public static Date parseDate(String date) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {
      return null;
    }
  }

}
