package road;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.impl.CaseRouter;
import ru.cos.cs.lengthy.impl.objects.PointImpl;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.node.NodeFork;
import ru.cos.sim.road.node.NodeJoin;
import ru.cos.sim.road.node.TransitionRule;

public class Play {

	
	@Test
	public void main() throws IOException, InterruptedException{
		Lane lane = new Lane(0, 100);
		NodeFork nFork = new NodeFork();
		TransitionRule tr = new TransitionRule(0, 20);
		NodeJoin nJoin = new NodeJoin();
		
		lane.setNext(nFork);
		nFork.setPrev(lane);
		nFork.forkTo(tr);
		tr.setPrev(nFork);
		tr.setNext(nJoin);
		nJoin.setNext(lane);
		
		tr.putPoint(new PointImpl(), 5);
		
		CaseRouter router = new CaseRouter();
		router.addCase(nFork, tr);
		
		List<Observation> observations = lane.observeForward(90, 40, router);
		assertTrue(observations.size()==3);
		
	}
	
}
