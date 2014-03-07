package org.shared.traveller.business.domain.jpa;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TravellerEntity.class)
public abstract class TravellerEntity_ {

	public static volatile SingularAttribute<TravellerEntity, Long> id;
	public static volatile SingularAttribute<TravellerEntity, Integer> rating_sum;
	public static volatile SingularAttribute<TravellerEntity, String> lastName;
	public static volatile SingularAttribute<TravellerEntity, String> fbAuthToken;
	public static volatile SingularAttribute<TravellerEntity, String> username;
	public static volatile SingularAttribute<TravellerEntity, String> travelCount;
	public static volatile SingularAttribute<TravellerEntity, String> phoneNumber;
	public static volatile SingularAttribute<TravellerEntity, Integer> ratingCount;
	public static volatile ListAttribute<TravellerEntity, NotificationEntity> notifications;
	public static volatile SingularAttribute<TravellerEntity, String> email;
	public static volatile ListAttribute<TravellerEntity, VehicleEntity> vehicles;
	public static volatile SingularAttribute<TravellerEntity, String> firstName;
	public static volatile SingularAttribute<TravellerEntity, String> password;

}

