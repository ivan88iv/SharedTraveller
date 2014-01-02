package org.ai.shared.traveller.validator.required;

import java.text.MessageFormat;

import org.ai.shared.traveller.validator.IScreenValidator;
import org.ai.sharedtraveller.R;

import android.content.Context;
import android.widget.Toast;

/**
 * The class validates that all the required fields on a specific screen are
 * present. For each field the method {@link #validate(String, String)} is
 * called and after it is called for all the required fields on a specific
 * screen the {@link #getResult()} method should be called to produce the result
 * whether the screen is valid or not.
 * 
 * @author "Ivan Ivanov"
 * 
 */
public class ScreenRequiredFieldValidator implements IScreenValidator
{
    private static final String NULL_CONTEXT =
            "The context may not be null.";

    private final String templateValidationMsg;

    private final Context context;

    private boolean isValidationSuccessful = true;

    private String invalidFieldNames;

    private final String fieldNamesSeparator;

    public ScreenRequiredFieldValidator(final Context inContext)
    {
        assert null != inContext : NULL_CONTEXT;

        context = inContext;
        templateValidationMsg = context.getResources().getString(
                R.string.required_validator_template);
        fieldNamesSeparator = context.getResources().getString(
                R.string.field_names_separator);
    }

    @Override
    public IScreenValidator validate(final String inValue,
            final String inFieldName)
    {
        if (null == inValue || "".equals(inValue))
        {
            invalidValue(inFieldName);
        }

        return this;
    }

    @Override
    public <T> IScreenValidator validate(final T inValue,
            final String inFieldName)
    {
        if (null == inValue)
        {
            invalidValue(inFieldName);
        }

        return this;
    }

    @Override
    public boolean getResult()
    {
        if (!isValidationSuccessful)
        {
            Toast.makeText(context,
                    MessageFormat.format(templateValidationMsg,
                            invalidFieldNames), Toast.LENGTH_LONG).show();
        }

        return isValidationSuccessful;
    }

    private void invalidValue(final String inFieldName)
    {
        if (isValidationSuccessful)
        {
            isValidationSuccessful = false;
            invalidFieldNames = inFieldName;
        } else
        {
            invalidFieldNames += fieldNamesSeparator + inFieldName;
        }
    }
}
