package ru.cos.sim.visualizer.traffic.parser.trace.location;

public class RuleLocation extends NodeLocation {

	protected int ruleId;
	protected boolean wp;
	
	protected RuleLocation() {
		super();
	}

	public RuleLocation(int nodeId,int ruleId,boolean wp) {
		super(Type.Rule,nodeId);
		this.wp = wp;
		this.ruleId = ruleId;
	}

	public RuleLocation(Type type,int nodeId,int ruleId) {
		super(type,nodeId);
		
		
	}

	public int getRuleId() {
		return ruleId;
	}

	public boolean isWp() {
		return wp;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public void setWp(boolean wp) {
		this.wp = wp;
	}

	
}
