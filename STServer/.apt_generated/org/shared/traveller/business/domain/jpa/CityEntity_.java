package org.shared.traveller.business.domain.jpa;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.shared.traveller.business.domain.CountryEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CityEntity.class)
public abstract class CityEntity_ {

	public static volatile SingularAttribute<CityEntity, Long> id;
	public static volatile SingularAttribute<CityEntity, String> name;
	public static volatile SingularAttribute<CityEntity, CountryEntity> country;

}

