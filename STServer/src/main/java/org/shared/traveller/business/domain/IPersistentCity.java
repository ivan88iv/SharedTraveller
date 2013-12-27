package org.shared.traveller.business.domain;

public interface IPersistentCity
{
	long getId();

	String getName();

	CountryEntity getCountry();
}
