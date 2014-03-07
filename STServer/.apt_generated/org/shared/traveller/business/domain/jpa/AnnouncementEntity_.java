package org.shared.traveller.business.domain.jpa;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.shared.traveller.business.domain.jpa.AnnouncementEntity.Status;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AnnouncementEntity.class)
public abstract class AnnouncementEntity_ {

	public static volatile SingularAttribute<AnnouncementEntity, Long> id;
	public static volatile SingularAttribute<AnnouncementEntity, BigDecimal> price;
	public static volatile ListAttribute<AnnouncementEntity, CityEntity> interPoints;
	public static volatile SingularAttribute<AnnouncementEntity, CityEntity> endPoint;
	public static volatile SingularAttribute<AnnouncementEntity, Status> status;
	public static volatile SingularAttribute<AnnouncementEntity, Date> departureDate;
	public static volatile SingularAttribute<AnnouncementEntity, String> address;
	public static volatile SingularAttribute<AnnouncementEntity, VehicleEntity> vehicle;
	public static volatile SingularAttribute<AnnouncementEntity, Short> freeSeats;
	public static volatile SingularAttribute<AnnouncementEntity, Date> departureTime;
	public static volatile SingularAttribute<AnnouncementEntity, TravellerEntity> driver;
	public static volatile SingularAttribute<AnnouncementEntity, CityEntity> startPoint;

}

