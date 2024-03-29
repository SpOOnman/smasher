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

    @SuppressWarnings("unchecked")
    public TwoRowMatrix(TwoRowMatrix matrix) {
        firstRow = (ArrayList<Integer>) matrix.getFirstRow().clone();
        secondRow = (ArrayList<Integer>) matrix.getSecondRow().clone();
    }

    /**
     * 
     */
    public TwoRowMatrix(int size, Integer value) {
        this();
        for (int i = 0; i < size; i++) {
            firstRow.add(value);
            secondRow.add(value);
        }
    }

    /**
     * Overlaps matrix with given matrix. Logical AND is executed on every
     * element.
     * 
     * @param matrix
     */
    public void overlap(TwoRowMatrix matrix) {
        if (matrix.getFirstRow().size() != firstRow.size() || matrix.getSecondRow().size() != secondRow.size())
            throw new IllegalArgumentException("matrix demensions do not match");

        for (int r = 0; r < 2; r++)
            for (int i = 0; i < getRow(r).size(); i++)
                getRow(r).set(i, getRow(r).get(i) == 1 && matrix.getRow(r).get(i) == 1 ? 1 : 0);
    }

    /**
     * Multiply this matrix by vector.
     * 
     * @param vector
     * @return
     */
    public ArrayList<Integer> multiply(ArrayList<Integer> vector) {
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

        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(firstSum);
        result.add(secondSum);

        return result;
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
