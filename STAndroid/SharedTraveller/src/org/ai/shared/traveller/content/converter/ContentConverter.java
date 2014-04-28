package org.ai.shared.traveller.content.converter;

import java.math.BigDecimal;

import android.widget.EditText;
import android.widget.TextView;

/**
 * The class represents a converter of the content of various UI android
 * components
 * 
 * @author "Ivan Ivanov"
 * 
 */
public final class ContentConverter
{
	private static final String NULL_COMPONENT = "The component must not be null.";

	private ContentConverter()
	{
		// This class is not meant to be instantiated nor extended.
		// It is only a simple utility class used by other classes to
		// perform the task of conversion of some android components' content
	}

	/**
	 * The method extracts the content of the given component as a {@link Short}
	 * value
	 * 
	 * @param inComponent
	 *            the component which content is extracted. The component may
	 *            not be null.
	 * @return the extracted and converted content of the component or null if
	 *         the content is empty
	 */
	public static Short toShort(final EditText inComponent)
	{
		Short result = null;
		final String content = getContent(inComponent);
		if (!"".equals(content))
		{
			result = Short.valueOf(content);
		}

		return result;
	}

	/**
	 * The method extracts the content of the given component in the form of a
	 * {@link BigDecimal} value
	 * 
	 * @param inComponent
	 *            the component which content is extracted. The component may
	 *            not be null.
	 * @return the extracted and converted content or null if the content is
	 *         empty
	 */
	public static BigDecimal toBigDecimal(final EditText inComponent)
	{
		BigDecimal result = null;
		final String content = getContent(inComponent);
		if (!"".equals(content))
		{
			result = new BigDecimal(content);
		}

		return result;
	}

	/**
	 * The method extracts the textual content of the given component and
	 * returns it
	 * 
	 * @param inComponent
	 *            the component whose content is extracted. It may not be null
	 * @return the content of the component
	 */
	public static String toString(final TextView inComponent)
	{
		return getContent(inComponent);
	}

	/**
	 * The method extracts the content of a component as a string
	 * 
	 * @param inComponent
	 *            the edit text component which content is extracted. The
	 *            component may not be null.
	 * @return the string content of the edit text component
	 */
	private static String getContent(final TextView inComponent)
	{
		assert null != inComponent : NULL_COMPONENT;

		return inComponent.getText().toString();
	}
}
