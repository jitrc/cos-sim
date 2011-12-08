package ru.cos.sim.parameters.xml;

import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.parameters.data.ModelParametersData;

public class ModelParametersDataReader {

	public static final Namespace NS = MDFReader.MDF_NAMESPACE;

	public static final String RANDOM_SEED = "randomSeed";

	public static ModelParametersData read(Element modelParametersElement) {
		ModelParametersData modelParametersData = new ModelParametersData();
		if (modelParametersElement==null) return modelParametersData;
		Element randomSeedElement = modelParametersElement.getChild(RANDOM_SEED, NS);
		if (randomSeedElement!=null)
			modelParametersData.setRandomSeed(Long.parseLong(randomSeedElement.getText()));
		return modelParametersData;
	}

}
