package org.shared.traveller.producer.jpa;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.shared.traveller.business.dao.ICityDAO;
import org.shared.traveller.business.dao.ITravellerDAO;
import org.shared.traveller.business.dao.IVehicleDAO;
import org.shared.traveller.business.domain.IPersistentAnnouncement;
import org.shared.traveller.business.domain.jpa.AnnouncementEntity.BusinessAnnouncementBuilder;
import org.shared.traveller.business.domain.jpa.CityEntity;
import org.shared.traveller.business.domain.jpa.TravellerEntity;
import org.shared.traveller.business.domain.jpa.VehicleEntity;
import org.shared.traveller.business.exception.IncorrectInputException;
import org.shared.traveller.client.domain.IAnnouncement;
import org.shared.traveller.client.domain.IAnnouncement.Status;
import org.shared.traveller.transformer.IPersistentAnnouncementProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementProducer implements
		IPersistentAnnouncementProducer,
		Serializable
{
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = -2917519439959013354L;

	private static final String NULL_ANNOUNCEMENT =
			"The announcement cannot be null!";

	private static final String NO_START_CITY =
			"No start city found with the name: {0}.";

	private static final String NO_END_CITY =
			"No end city found with the name: {0}.";

	private static final String NO_DRIVER =
			"No driver with username {0} found.";

	private IPersistentAnnouncement persistentAnnouncement;

	@Autowired
	private ICityDAO cityDao;

	@Autowired
	private IVehicleDAO vehicleDao;

	@Autowired
	private ITravellerDAO travellerDao;

	@Override
	public void visit(IAnnouncement inAnnouncement)
	{
		assert null != inAnnouncement : NULL_ANNOUNCEMENT;

		final CityEntity startCity = extractCity(inAnnouncement.getFrom(),
				NO_START_CITY);
		final CityEntity endCity = extractCity(inAnnouncement.getTo(),
				NO_END_CITY);
		final VehicleEntity vehicle = extractVehicle(
				inAnnouncement.getVehicleName());
		final TravellerEntity driver = extractDriver(
				inAnnouncement.getDriverUsername(), NO_DRIVER);

		final BusinessAnnouncementBuilder builder =
				new BusinessAnnouncementBuilder(startCity, endCity,
						inAnnouncement.getDepartureDate(),
						inAnnouncement.getSeats(),
						Status.ACTIVE, driver);
		builder.address(inAnnouncement.getDepAddress())
				.departureTime(inAnnouncement.getDepartureTime())
				.price(inAnnouncement.getPrice())
				.vehicle(vehicle);

		final List<String> intermediateCityNames =
				inAnnouncement.getIntermediatePts();
		if (null != intermediateCityNames)
		{
			List<CityEntity> intermediateCities = new ArrayList<CityEntity>();
			for (final String cityName : intermediateCityNames)
			{
				final CityEntity currCity =
						(CityEntity) cityDao.findCityByName(cityName);
				intermediateCities.add(currCity);
			}

			builder.intermediatePoints(intermediateCities);
		}
		persistentAnnouncement = builder.build();
	}

	@Override
	public IPersistentAnnouncement produce()
	{
		return persistentAnnouncement;
	}

	private CityEntity extractCity(final String inCityName,
			final String inErrorMsg)
	{
		final CityEntity city = (CityEntity) cityDao.findCityByName(inCityName);
		if (null == city)
		{
			throw new IncorrectInputException(MessageFormat.format(
					inErrorMsg, inCityName), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return city;
	}

	private VehicleEntity extractVehicle(final String inVehicleName)
	{
		return (VehicleEntity) vehicleDao.findByDisplayName(inVehicleName);
	}

	private TravellerEntity extractDriver(final String inDriverUsername,
			final String inErrorMsg)
	{
		final TravellerEntity driver = (TravellerEntity) travellerDao
				.findByUsername(inDriverUsername);

		if (null == driver)
		{
			throw new IncorrectInputException(inErrorMsg,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return driver;
	}
}
