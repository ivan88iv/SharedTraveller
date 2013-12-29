package org.shared.traveller.business.dao.jpa;

import java.util.ArrayList;
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

	private static final String NULL_USERNAME =
			"The username of the driver cannot be null.";

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

	@Override
	public List<String> getVehicleNames(String inUsername)
	{
		assert null != inUsername : NULL_USERNAME;

		final TypedQuery<String> query = entityManager.createNamedQuery(
				"VehicleEntity.getVehiclesForUser", String.class);
		query.setParameter("username", inUsername);
		List<String> names = query.getResultList();
		if (null == names)
		{
			names = new ArrayList<String>();
		}

		return names;
	}
}
