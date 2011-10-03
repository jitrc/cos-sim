package ru.cos.sim.ras.duo.algo;

import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.PathExtensions;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.Weighter;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;
import ru.cos.sim.ras.duo.digraph.roadnet.TransitionEdge;
import ru.cos.sim.ras.duo.utils.ExtensionCollection;
import ru.cos.sim.ras.duo.utils.SimpleFactory;

public class StandardWeightProvider implements WeightProvider, DataCollector<DataSources> {
	
	public StandardWeightProvider() {
		this(null, null);
	}
	
	public StandardWeightProvider(LinkWeighterPack linkWeighterFactory) {
		this(linkWeighterFactory, null);
	}
	
	public StandardWeightProvider(LinkWeighterPack linkWeighterFactory, TransitionWeighterPack transitionWeighterFactory) {
		this.setLinkWeighterFactory(linkWeighterFactory);
		this.setTransitionWeighterFactory(transitionWeighterFactory);
	}
	
	public static interface LinkWeighterPack extends DataCollector<DataSources> {
		public Weighter createWeighter(LinkEdge link);
	}
	
	public static interface TransitionWeighterPack extends DataCollector<DataSources> {
		public Weighter createWeighter(TransitionEdge transition);
	}
	
	public static interface LinkReportingPack {
		public float report(LinkEdge link, int reportType);
	}
	
	public static interface TransitionReportingPack {
		public float report(TransitionEdge transition, int reportType);
	}

	private int extensionKey = ExtensionCollection.getFreeExtensionNumber();
	
	private LinkWeighterPack linkWeighterFactory;
	public LinkWeighterPack getLinkWeighterFactory() {
		return linkWeighterFactory;
	}
	public void setLinkWeighterFactory(LinkWeighterPack linkWeighterFactory) {
		this.linkWeighterFactory = linkWeighterFactory;
	}
	
	private TransitionWeighterPack transitionWeighterFactory;
	public TransitionWeighterPack getTransitionWeighterFactory() {
		return transitionWeighterFactory;
	}
	public void setTransitionWeighterFactory(TransitionWeighterPack transitionWeighterFactory) {
		this.transitionWeighterFactory = transitionWeighterFactory;
	}
	
	@Override
	public void collectData(DataSources medium) {
		if (getLinkWeighterFactory() != null)
			getLinkWeighterFactory().collectData(medium);
		if (getTransitionWeighterFactory() != null)
			getTransitionWeighterFactory().collectData(medium);
	}
	
	protected Weighter createWeighter(Edge edge) {
		if (edge instanceof LinkEdge && getLinkWeighterFactory() != null)
			return getLinkWeighterFactory().createWeighter((LinkEdge)edge);
		else if (edge instanceof TransitionEdge && getTransitionWeighterFactory() != null)
			return getTransitionWeighterFactory().createWeighter((TransitionEdge)edge);
		else
			return new Weighter() {
				@Override
				public float getWeight(PathExtensions extensions) {
					return 0;
				}
			};
	}
	
	protected Weighter getWeighter(final Edge edge) {
		return edge.getExtensions().get(extensionKey, Weighter.class, new SimpleFactory<Weighter>() {
			@Override
			public Weighter createNew() {
				return createWeighter(edge);
			}
		});
	}
	
	@Override
	public float getWeight(Edge edge, PathExtensions extensions) {
		return getWeighter(edge).getWeight(extensions);
	}
}
