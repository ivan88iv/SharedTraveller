package org.ai.shared.traveller.validator;

public interface IScreenValidator
{
    IScreenValidator validate(final String inValue, final String inFieldName);

    <T> IScreenValidator validate(final T inValue, final String inFieldName);

    boolean getResult();
}
