/*
 * Copyright (C) Stephan van Hulst.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package nl.nibsi.util.require;

import java.util.*;
import java.util.function.*;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Utility methods to create common requirements represented by {@linkplain Matcher Hamcrest matchers}.
 */
public final class Requirements {

  private Requirements() {}

  /**
   * Creates a requirement from a description and a predicate.
   *
   * @param <T>         the type of objects to match.
   *
   * @param description a description of objects that meet the requirement.
   * @param predicate   a predicate that determines whether an object meets the requirement.
   *                    A {@code null} reference will never meet the created requirement and
   *                    the predicate is only applied to actual object instances.
   *
   * @return a matcher with the given {@code description} that will yield a positive match
   *         if and only if the object reference is not {@code null}
   *         and {@code predicate} returns {@code true} when applied to the object.
   */
  public static <T> Matcher<T> createRequirement(String description, Predicate<? super T> predicate) {
    return new CustomTypeSafeMatcher<T>(description) {
      @Override
      protected boolean matchesSafely(T argument) {
        return predicate.test(argument);
      }
    };
  }

  /**
   * Creates the requirement that a string is stripped of leading and trailing whitespace.
   *
   * @return a matcher that will positively match a string if and only if the string is not {@code null}
   *         and is {@linkplain String#strip() stripped} of leading and trailing whitespace characters.
   */
  public static Matcher<String> strippedString() {
    return createRequirement(
      "a string stripped of leading and trailing whitespace",
      string -> string.strip().equals(string)
    );
  }

  /**
   * Creates the requirement that a string is stripped of leading and trailing whitespace and is not empty.
   *
   * @return a matcher that will positively match a string if and only if the string is not {@code null},
   *         is {@linkplain String#strip() stripped} of leading and trailing whitespace characters
   *         and is not {@linkplain String#isEmpty() empty}.
   */
  public static Matcher<String> nonEmptyStrippedString() {
    return both(strippedString()).and(not(emptyString()));
  }
  
  /**
   * Creates the requirement that an int array has the specified size.
   * 
   * @param size the length that int arrays must have to yield a positive match.
   * 
   * @return a matcher that will positively match an int array if and only if the array is not {code null}
   *         and its length is equal to {@code size}.
   */
  public static Matcher<int[]> arrayOfIntsWithSize(int size) {
    return createRequirement(
      "an int array with size " + size,
      array -> array.length == size
    );
  }
  
  /**
   * Creates the requirement that a long array has the specified size.
   * 
   * @param size the length that long arrays must have to yield a positive match.
   * 
   * @return a matcher that will positively match a long array if and only if the array is not {code null}
   *         and its length is equal to {@code size}.
   */
  public static Matcher<long[]> arrayOfLongsWithSize(int size) {
    return createRequirement(
      "an int array with size " + size,
      array -> array.length == size
    );
  }

  /**
   * Creates the requirement that an array does not contain any {@code null} elements.
   * 
   * @return a matcher that will positively match an array if and only if the array is not {@code null}
   *         and does not contain any {@code null} elements.
   */
  public static Matcher<Object[]> notHasNullInArray() {
    return not(hasItemInArray(nullValue()));
  }

  /**
   * Creates the requirement that a collection does not contain any {@code null} elements.
   *
   * @return a matcher that will positively match a collection if and only if the collection is not {@code null}
   *         and does not contain any {@code null} elements.
   */
  public static Matcher<Collection<?>> notHasNull() {
    return createRequirement(
      "a collection without any null elements",
      collection -> {
        try {
          return !collection.contains(null);
        }
        
        catch (NullPointerException | IllegalArgumentException ex) {
          return true;
        }
      }
    );
  }
  
  /**
   * Creates the requirement that a map does not contain a {@code null} key.
   *
   * @return a matcher that will positively match a map if and only if the map is not {@code null}
   *         and does not contain a {@code null} key.
   */
  public static Matcher<Map<?,?>> notHasNullKey() {
    return createRequirement(
      "a map without a null key",
      map -> {
        try {
          return !map.containsKey(null);
        }
        
        catch (NullPointerException | IllegalArgumentException ex) {
          return true;
        }
      }
    );
  }
  
  /**
   * Creates the requirement that a map does not contain any {@code null} values.
   *
   * @return a matcher that will positively match a map if and only if the map is not {@code null}
   *         and does not contain any {@code null} values.
   */
  public static Matcher<Map<?,?>> notHasNullValues() {
    return createRequirement(
      "a map without any null values",
      map -> {
        try {
          return !map.containsValue(null);
        }
        
        catch (NullPointerException | IllegalArgumentException ex) {
          return true;
        }
      }
    );
  }

  /**
   * Creates the requirement that a map does not contain any {@code null} keys or values.
   *
   * @return a matcher that will positively match a map if and only if that map is not {@code null}
   *         and does not contain a {@code null} key or any {@code null} values.
   */
  public static Matcher<Map<?,?>> notHasNullKeyOrValues() {
    return both(notHasNullKey()).and(notHasNullValues());
  }
}
