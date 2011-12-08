/**
 * 
 */
package ru.cos.cs.lengthy.impl;

import java.util.HashMap;
import java.util.Map;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.Router;

/**
 * Case router performs routing using predefined route cases - pair of join and selected lengthies.
 * @author zroslaw
 */
public class CaseRouter implements Router {

	private Map<Join, Lengthy> joinCases = new HashMap<Join, Lengthy>();
	private Map<Fork, Lengthy> forkCases = new HashMap<Fork, Lengthy>();

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.Router#chooseNextLengthy(ru.cos.cs.lengthy.Join)
	 */
	@Override
	public Lengthy chooseNextLengthy(Join join) {
		return joinCases.get(join);
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.Router#chooseNextLengthy(ru.cos.cs.lengthy.Fork)
	 */
	@Override
	public Lengthy chooseNextLengthy(Fork fork) {
		return forkCases.get(fork);
	}

	public void addCase(Fork fork, Lengthy lengthy){
		forkCases.put(fork, lengthy);
	}
	
	public void addCase(Join join, Lengthy lengthy){
		joinCases.put(join, lengthy);
	}

}
