package org.shared.traveller.utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The class represents a utility class used for deep-copying some existing
 * objects
 *
 * @author "Ivan Ivanov"
 *
 */
public final class DeepCopier
{
    private DeepCopier()
    {
        // this class is not meant to be instantiated as it is
        // a simple utility class
    }

    /**
     * The method deep-copies the provided date. It returns the copy or null if
     * the provided date is also null
     *
     * @param inDateToCopy
     *            the date to be copied
     * @return the new date
     */
    public static Date copy(final Date inDateToCopy)
    {
        Date copy = null;

        if (null != inDateToCopy)
        {
            copy = new Date(inDateToCopy.getTime());
        }

        return copy;
    }

    /**
     * The method deep-copies the provided value. It returns the copy or null if
     * the provided value is also null
     *
     * @param inValue
     *            the value to be copied
     * @return the new copy
     */
    public static BigDecimal copy(final BigDecimal inValue)
    {
        BigDecimal copy = null;

        if (null != inValue)
        {
            copy = new BigDecimal(inValue.toString());
        }

        return copy;
    }

    /**
     * The method deep-copies the provided list. It returns the copy list or
     * null if the provided list is also null
     *
     * @param inList
     *            the list to be copied
     * @return the new copy list
     */
    public static <T> List<T> copy(final List<T> inList)
    {
        List<T> copy = null;

        if (null != inList)
        {
            if (inList instanceof LinkedList)
            {
                copy = new LinkedList<T>(inList);
            } else
            {
                copy = new ArrayList<T>(inList);
            }
        }

        return copy;
    }

    /**
     * The method deep-copies the provided map. It returns the copy map or
     * null if the provided map is also null
     *
     * @param inMap
     *            the map to be copied
     * @return the new copy map
     */
    public static <K,V> Map<K,V> copy(final Map<K,V> inMap)
    {
    	Map<K,V> copy = null;

        if (null != inMap)
        {
            if (inMap instanceof TreeMap)
            {
                copy = new TreeMap<K,V>(inMap);
            } else
            {
                copy = new HashMap<K,V>(inMap);
            }
        }

        return copy;
    }

    /**
     * The method deep-copies the provided array. It returns the copy array or
     * null if the provided array is also null
     *
     * @param inArray
     *            the array to be copied
     * @return the new copy array
     */
    public static String[] copy(final String[] inArray)
    {
        String[] copy = null;

        if (null != inArray)
        {
            final int arrLen = inArray.length;
            copy = new String[arrLen];

            for (int ind = 0; ind < arrLen; ++ind)
            {
                copy[ind] = inArray[ind];
            }
        }

        return copy;
    }
}
