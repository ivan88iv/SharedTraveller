package org.shared.traveller.business.domain.jpa;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VehicleEntity.class)
public abstract class VehicleEntity_ {

	public static volatile SingularAttribute<VehicleEntity, Long> id;
	public static volatile SingularAttribute<VehicleEntity, Boolean> airbag;
	public static volatile SingularAttribute<VehicleEntity, String> model;
	public static volatile SingularAttribute<VehicleEntity, String> regNumber;
	public static volatile SingularAttribute<VehicleEntity, String> desc;
	public static volatile SingularAttribute<VehicleEntity, String> color;
	public static volatile SingularAttribute<VehicleEntity, Date> yearOfProduction;
	public static volatile SingularAttribute<VehicleEntity, TravellerEntity> owner;
	public static volatile SingularAttribute<VehicleEntity, Short> seats;
	public static volatile SingularAttribute<VehicleEntity, String> displayName;
	public static volatile SingularAttribute<VehicleEntity, String> make;
	public static volatile SingularAttribute<VehicleEntity, Boolean> ccu;

}

