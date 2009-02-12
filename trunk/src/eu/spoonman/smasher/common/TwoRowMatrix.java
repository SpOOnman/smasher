/*
 * This file is part of Smasher.
 * Copyright 2008, 2009 Tomasz 'SpOOnman' Kalkosiński <spoonman@op.pl>
 * 
 * Smasher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Smasher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Smasher.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.spoonman.smasher.common;

import java.util.ArrayList;

/**
 * Simple two row matrix with few convenience methods.
 * 
 * @author Tomasz Kalkosińśki
 * 
 */
public class TwoRowMatrix {

    private ArrayList<Integer> firstRow;

    private ArrayList<Integer> secondRow;

    public TwoRowMatrix() {
        firstRow = new ArrayList<Integer>();
        secondRow = new ArrayList<Integer>();
    }

    /**
     * 
     */
    public TwoRowMatrix(int size, Integer value) {
        for (int i = 0; i < size; i++) {
            firstRow.add(value);
            secondRow.add(value);
        }
    }

    /**
     * Multiply this matrix by vector.
     * 
     * @param vector
     * @return
     */
    public TwoRowMatrix multiply(ArrayList<Integer> vector) {
        if (vector == null)
            throw new IllegalArgumentException("vector is null");

        if (vector.size() != firstRow.size() || vector.size() != secondRow.size())
            throw new IllegalArgumentException("vector size is not equal to matrix dimension");

        int firstSum = 0;
        int secondSum = 0;

        for (int i = 0; i < vector.size(); i++) {
            firstSum += firstRow.get(i) * vector.get(i);
            secondSum += secondRow.get(i) * vector.get(i);
        }

        TwoRowMatrix twoRowMatrix = new TwoRowMatrix();
        twoRowMatrix.getFirstRow().add(firstSum);
        twoRowMatrix.getSecondRow().add(secondSum);

        return twoRowMatrix;
    }

    /**
     * @return the firstRow
     */
    public ArrayList<Integer> getFirstRow() {
        return firstRow;
    }

    /**
     * @return the secondRow
     */
    public ArrayList<Integer> getSecondRow() {
        return secondRow;
    }

    public ArrayList<Integer> getRow(int index) {
        switch (index) {
        case 0:
            return getFirstRow();
        case 1:
            return getSecondRow();
        default:
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

}
