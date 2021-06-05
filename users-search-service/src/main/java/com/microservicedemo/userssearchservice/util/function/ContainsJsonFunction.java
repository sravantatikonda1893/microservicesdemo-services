package com.microservicedemo.userssearchservice.util.function;

import java.util.List;
import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

public class ContainsJsonFunction implements SQLFunction {

  @Override
  public String render(Type type, List arguments, SessionFactoryImplementor factory)
      throws QueryException {
    if (arguments != null && arguments.size() < 3) {
      throw new IllegalArgumentException("The function must be passed 3 arguments");
    }

    String fragment = null;
    if (arguments.size() == 3) {
      String rootPath = (String) arguments.get(0);
      String field = (String) arguments.get(1);
      String value = (String) arguments.get(2);
      fragment =
          "jsonb_extract_path_text(" + rootPath + ", " + field + ")::jsonb @> " + value + "::jsonb";
    }

    return fragment;
  }

  @Override
  public boolean hasArguments() {
    return true;
  }

  @Override
  public boolean hasParenthesesIfNoArguments() {
    return false;
  }

  @Override
  public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
    return new BooleanType();
  }
}
