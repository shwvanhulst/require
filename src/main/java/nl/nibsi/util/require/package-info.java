/*
 * Copyright (C) Stephan van Hulst.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/**
 * Contains classes for asserting that method and constructor arguments meet specified requirements.
 * 
 * <p>
 *   Use the {@link Require} class to validate method and constructor parameters.
 *   The {@link Requirements} class contains factory methods for common requirements.
 * </p>
 * 
 * <p>
 *   The programmer can easily create their own requirements by calling {@link Requirements#createRequirement}
 *   or by composing requirements created by the {@link Requirements} and {@link org.hamcrest.Matchers} classes.
 * </p>
 */
package nl.nibsi.util.require;
