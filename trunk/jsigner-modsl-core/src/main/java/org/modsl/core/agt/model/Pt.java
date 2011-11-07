/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.agt.model;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

import java.util.Random;

/**
 * Represents 2D coordinates
 * @author avishnyakov
 */
public class Pt {

    /**
     * "Epsilon" value
     */
    public final static double EPSILON = 0.0000001d;

    public double x = 0d;

    public double y = 0d;

    /**
     * New instance with <code>x = y = 0</code>
     */
    public Pt() {
        zero();
    }

    /**
     * New instance with given x, y
     */
    public Pt(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * New instance as a copy of P
     * 
     * @param p
     */
    public Pt(Pt p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Decrements this instance by given value.
     * 
     * @param p
     */
    public Pt decBy(double d) {
        x -= d;
        y -= d;
        return this;
    }

    public Pt decBy(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Decrements this instance by given value.
     * 
     * @param p
     */
    public Pt decBy(Pt p) {
        x -= p.x;
        y -= p.y;
        return this;
    }

    /**
     * Divides this instance by parameter and returns the result as a new
     * instance
     * 
     * @param p
     * @return new instance
     */
    public Pt div(double k) {
        return new Pt(x / k, y / k);
    }

    /**
     * Divides this instance by given value.
     * 
     * @param d
     */
    public Pt divBy(double d) {
        x /= d;
        y /= d;
        return this;
    }

    /**
     * Divides this instance by given value.
     * 
     * @param p
     */
    public Pt divBy(Pt p) {
        x /= p.x;
        y /= p.y;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        Pt xy = (Pt) obj;
        return abs(this.x - xy.x) < EPSILON && abs(this.y - xy.y) < EPSILON;
    }

    /**
     * @return rounded value
     */
    public Pt getRounded() {
        return new Pt(round(x), round(y));
    }

    @Override
    public int hashCode() {
        long bits = 7 + 31 * Double.doubleToLongBits(x) + Double.doubleToLongBits(y);
        return (int) (bits ^ (bits >>> 32));
    }

    /**
     * Increments this instance by given value.
     * 
     * @param p
     */
    public Pt incBy(double d) {
        x += d;
        y += d;
        return this;
    }

    public Pt incBy(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Increments this instance by given value
     * 
     * @param p
     */
    public Pt incBy(Pt p) {
        x += p.x;
        y += p.y;
        return this;
    }

    /**
     * @return true if both x and y are less than EPS
     */
    public boolean isZero() {
        return abs(x) < EPSILON && abs(y) < EPSILON;
    }

    /**
     * Calculates the length of this vector
     * 
     * @return
     */
    public double len() {
        return sqrt(x * x + y * y);
    }

    /**
     * Calculates safe length. If length is 0 it will return length of a vector
     * with <code>x = y = EPSILON</code>
     * 
     * @return length of XY vector, EPS of length is 0
     */
    public double lenSafe() {
        return Math.max(len(), EPSILON);
    }

    /**
     * Max of this point and given x, y point
     * @param x1
     * @param y1
     * @return (max(x), max(y))
     */
    public Pt max(double x1, double y1) {
        return new Pt(Math.max(x, x1), Math.max(y, y1));
    }

    public Pt minus(double x, double y) {
        return new Pt(this.x - x, this.y - y);
    }

    /**
     * Subtracts parameter from this instance and returns the result as a new
     * instance
     * 
     * @param p
     * @return new instance
     */
    public Pt minus(Pt p) {
        return new Pt(x - p.x, y - p.y);
    }

    /**
     * Multiplies this instance by given value.
     * 
     * @param p
     */
    public Pt mulBy(double d) {
        x *= d;
        y *= d;
        return this;
    }

    /**
     * Multiplies this instance by given value.
     * 
     * @param p
     */
    public Pt mulBy(Pt p) {
        x *= p.x;
        y *= p.y;
        return this;
    }

    /**
     * Multiplies this instance by parameter and returns the result as a new
     * instance
     * 
     * @param p
     * @return new instance
     */
    public Pt mult(double k) {
        return new Pt(k * x, k * y);
    }

    public Pt plus(double x, double y) {
        return new Pt(this.x + x, this.y + y);
    }

    /**
     * Adds p to this instance and returns the result as a new instance
     * 
     * @param p
     * @return new instance
     */
    public Pt plus(Pt p) {
        return new Pt(x + p.x, y + p.y);
    }

    /**
     * Randomizes x and y within given range
     * 
     * @param range
     */
    public void randomize(Random random, Pt range) {
        x = random.nextDouble() * range.x;
        y = random.nextDouble() * range.y;
    }

    @Override
    public String toString() {
        return "(" + (float) x + "," + (float) y + ")";
    }

    /**
     * Zeroes x and y
     */
    public void zero() {
        x = 0d;
        y = 0d;
    }

}
