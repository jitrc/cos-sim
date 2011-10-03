package ru.cos.sim.ras.duo.algo.aggregating;

import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.ras.duo.utils.Extendable;
import ru.cos.sim.ras.duo.utils.ExtensionCollection;
import ru.cos.sim.ras.duo.utils.Pair;

public class PerItemDataCollector<K extends Extendable, T> implements DataCollector<Pair<K, T>> {

	private int extensionKey = ExtensionCollection.getFreeExtensionNumber();
	
	@SuppressWarnings("unchecked")
	public DataCollector<T> getCollector(K item) {
		 return (DataCollector<T>)item.getExtensions().get(extensionKey, DataCollector.class);
	}
	
	public void add(K item, DataCollector<T> collector) {
		item.getExtensions().put(extensionKey, collector);
	}
	
	public void collectData(K item, T medium) {
		collectData(new Pair<K, T>(item, medium));
	}
	
	@Override
	public void collectData(Pair<K, T> keyedMedium) {
		DataCollector<T> collector = getCollector(keyedMedium.getFirst());
		if (collector != null)
			collector.collectData(keyedMedium.getSecond());
	}
}
