package org.shared.traveller.business.domain.jpa;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NotificationEntity.class)
public abstract class NotificationEntity_ {

	public static volatile SingularAttribute<NotificationEntity, Long> id;
	public static volatile SingularAttribute<NotificationEntity, Date> creationDate;
	public static volatile SingularAttribute<NotificationEntity, String> text;
	public static volatile SingularAttribute<NotificationEntity, TravellerEntity> traveller;
	public static volatile SingularAttribute<NotificationEntity, Boolean> seen;

}

