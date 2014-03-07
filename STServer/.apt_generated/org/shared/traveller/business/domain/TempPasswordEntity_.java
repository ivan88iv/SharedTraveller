package org.shared.traveller.business.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.shared.traveller.business.domain.jpa.TravellerEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TempPasswordEntity.class)
public abstract class TempPasswordEntity_ {

	public static volatile SingularAttribute<TempPasswordEntity, Long> id;
	public static volatile SingularAttribute<TempPasswordEntity, String> tempPassword;
	public static volatile SingularAttribute<TempPasswordEntity, TravellerEntity> owner;

}

