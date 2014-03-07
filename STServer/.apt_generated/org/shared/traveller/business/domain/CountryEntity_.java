package org.shared.traveller.business.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.shared.traveller.business.domain.jpa.CityEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CountryEntity.class)
public abstract class CountryEntity_ {

	public static volatile SingularAttribute<CountryEntity, Long> id;
	public static volatile ListAttribute<CountryEntity, CityEntity> cities;
	public static volatile SingularAttribute<CountryEntity, String> name;

}

