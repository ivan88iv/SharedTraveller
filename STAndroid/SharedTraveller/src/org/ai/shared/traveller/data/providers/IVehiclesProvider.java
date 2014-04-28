package org.ai.shared.traveller.data.providers;

import org.ai.shared.traveller.ui.preparator.IVehicleComponentsPreparator;

public interface IVehiclesProvider
{
	void provideVehicleNames(final String inUsername,
			final IVehicleComponentsPreparator inPreparator);
}
