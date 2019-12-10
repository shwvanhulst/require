/*
 * Copyright (C) Stephan van Hulst.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package nl.nibsi.util.require;

import java.util.function.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static java.lang.String.format;

/**
 * Utility methods to assert that method and constructor arguments meet specified requirements.
 */
public final class Require {

  private Require() {}

  /**
   * Asserts that an argument passed to a method or constructor meets the specified requirement.
   *
   * @param <T>              the type of the method or constructor parameter to validate.
   * @param <X>              the type of the exception to throw if the argument doesn't meet the requirement.
   *
   * @param parameterName    the name of the method or constructor parameter to validate.
   * @param argument         the value of the parameter with name {@code parameterName}.
   * @param requirement      a matcher representing the requirement that {@code argument} must meet.
   * @param exceptionFactory a function that takes an exception message and creates the exception to throw
   *                         when {@code argument} doesn't meet {@code requirement}.
   *
   * @return {@code argument}
   *
   * @throws X if {@code argument} doesn't meet {@code requirement}.
   */
  public static <T, X extends Throwable> T requireThat(
    String parameterName,
    T argument,
    Matcher<? super T> requirement,
    Function<? super String, ? extends X> exceptionFactory
  ) throws X {
    if (requirement.matches(argument))
      return argument;
    
    Description description = new StringDescription()
      .appendText("Expected ")
      .appendDescriptionOf(requirement)
      .appendText(format(" for parameter '%s', but found ", parameterName))
      .appendValue(argument);

    throw exceptionFactory.apply(description.toString());
  }

  /**
   * Asserts that an argument passed to a method or constructor meets the specified requirement.
   *
   * <p>
   *   Calling this method is is equivalent to calling
   *   {@code requireThat(parameterName, argument, requirement, IllegalArgumentException::new)}.
   * </p>
   *
   * @param <T>           the type of the method or constructor parameter to validate.
   *
   * @param parameterName the name of the method or constructor parameter to validate.
   * @param argument      the value of the parameter with name {@code parameterName}.
   * @param requirement   a matcher representing the requirement that {@code argument} must meet.
   *
   * @return {@code argument}
   *
   * @throws IllegalArgumentException if {@code argument} doesn't meet {@code requirement}.
   */
  public static <T> T requireThat(String parameterName, T argument, Matcher<? super T> requirement) {
    return requireThat(parameterName, argument, requirement, IllegalArgumentException::new);
  }

  /**
   * Asserts that an index passed to a method or constructor meets the specified requirement.
   *
   * <p>
   *   Calling this method is is equivalent to calling
   *   {@code requireThat(parameterName, index, requirement, IndexOutOfBoundsException::new)}.
   * </p>
   *
   * @param <T>           the type of the method or constructor parameter to validate.
   *
   * @param parameterName the name of the method or constructor parameter to validate.
   * @param index         the value of the parameter with name {@code parameterName}.
   * @param requirement   a matcher representing the requirement that {@code index} must meet.
   *
   * @return {@code index}
   *
   * @throws IndexOutOfBoundsException if {@code index} doesn't meet {@code requirement}.
   */
  public static <T> T requireThatIndex(String parameterName, T index, Matcher<? super T> requirement) {
    return requireThat(parameterName, index, requirement, IndexOutOfBoundsException::new);
  }
}
