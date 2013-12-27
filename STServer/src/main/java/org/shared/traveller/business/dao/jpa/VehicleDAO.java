package org.shared.traveller.business.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.shared.traveller.business.dao.IVehicleDAO;
import org.shared.traveller.business.domain.IPersistentVehicle;
import org.shared.traveller.business.domain.jpa.VehicleEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDAO extends AbstractDAO<IPersistentVehicle>
		implements IVehicleDAO
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 1839402074564229621L;

	@Override
	public VehicleEntity findByDisplayName(String inName)
	{
		final TypedQuery<VehicleEntity> query = entityManager.createNamedQuery(
				"VehicleEntity.findByName", VehicleEntity.class);
		query.setParameter("name", inName).setMaxResults(1);
		VehicleEntity vehicle = null;
		final List<VehicleEntity> results = query.getResultList();
		if (null != results && !results.isEmpty())
		{
			vehicle = results.get(0);
		}

		return vehicle;
	}
}
