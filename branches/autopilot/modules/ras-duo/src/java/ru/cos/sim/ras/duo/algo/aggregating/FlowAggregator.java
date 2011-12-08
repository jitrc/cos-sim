package ru.cos.sim.ras.duo.algo.aggregating;

import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.DataAggregator;
import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.ras.duo.utils.ExtensionCollection;
import ru.cos.sim.ras.duo.utils.SimpleFactory;
import ru.cos.sim.services.ServiceLocator;
import ru.cos.sim.utils.ExpiringList;

public class FlowAggregator implements DataCollector<DataSources>, DataAggregator {
	private static int extensionKey = ExtensionCollection.getFreeExtensionNumber();
	
	public FlowAggregator(int linkId, Parameters parameters) {
		this.linkId = linkId;
		this.parameters = parameters;
	}
	
	private int linkId;
	protected int getLinkId() {
		return linkId;
	}
	
	protected float getCurrentTime() {
		return ServiceLocator.getInstance().getTimeSerivce().getAbsoluteTime();
	}
	
	private Parameters parameters;
	
	protected float getExpirationPeriod() {
		return parameters.getExpiration().getExpirationPeriod();
	}

	private ExpiringList<Integer> lastVehicles = new ExpiringList<Integer>();
	
	private int countVehicles() {
		int count = 0;
		for (@SuppressWarnings("unused") int id : lastVehicles)
			count++;
		return count;
	}
	
	private static class Registration {
		private int lastLinkId = -1;
		public int getLastLinkId() {
			return lastLinkId;
		}
		public void setLastLinkId(int lastLinkId) {
			this.lastLinkId = lastLinkId;
		}

		public static Registration get(DataSources medium) {
			return medium.getVehicleData().getExtensions().get(extensionKey, Registration.class, new SimpleFactory<Registration>() {
				@Override
				public Registration createNew() {
					return new Registration();
				}
			});
		}
	}
	
	@Override
	public void collectData(DataSources medium) {
		Registration registration = Registration.get(medium); 
		if (registration.getLastLinkId() != getLinkId()) {
			registration.setLastLinkId(getLinkId());
			accountVehicle(medium);
		}
	}
	
	private void accountVehicle(DataSources medium) {
		lastVehicles.add(medium.getTimestamp(), medium.getVehicleId());
	}

	@Override
	public float getAggregate() {
		lastVehicles.extractAllOlderThan(getCurrentTime() - getExpirationPeriod());
		return countVehicles() / getExpirationPeriod();
	}
	
	@Override
	public String toString() {
		return "FLW: " + getAggregate() + "\tFCNT: " + countVehicles();
	}
}
