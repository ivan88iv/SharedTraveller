package org.shared.traveller.business.domain.jpa;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.shared.traveller.business.domain.enumeration.RequestStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RequestEntity.class)
public abstract class RequestEntity_ {

	public static volatile SingularAttribute<RequestEntity, Long> id;
	public static volatile SingularAttribute<RequestEntity, AnnouncementEntity> announcement;
	public static volatile SingularAttribute<RequestEntity, RequestStatus> status;
	public static volatile SingularAttribute<RequestEntity, TravellerEntity> traveller;

}

