package org.shared.traveller.business.dao;

import org.shared.traveller.business.domain.IPersistentTraveller;

public interface ITravellerDAO extends IDAO<IPersistentTraveller>
{
	IPersistentTraveller findByUsername(String inUsername);
}
