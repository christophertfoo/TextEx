/*
 *   Copyright (C) 2013  Christopher Foo
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package controllers;

import com.avaje.ebean.annotation.EnumValue;

/**
 * The condition of the {@link models.Book}s being sold (see {@link models.Offer}) or requested (see
 * {@link models.Request}) in the book exchange application.
 * 
 * @author Christopher Foo
 * 
 */
public enum Condition {
  @EnumValue("N")
  NEW,

  @EnumValue("S")
  SLIGHTLY_USED,

  @EnumValue("H")
  HEAVILY_USED;

  /**
   * Returns the {@link String} representation of this {@link Condition}.
   */
  @Override
  public String toString() {
    switch (this) {
    case HEAVILY_USED:
      return "Heavily Used";
    case NEW:
      return "New";
    case SLIGHTLY_USED:
      return "Slightly Used";
    default:
      return "Unknown";
    }
  }
}
