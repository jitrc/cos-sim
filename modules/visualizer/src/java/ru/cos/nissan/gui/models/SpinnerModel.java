package ru.cos.nissan.gui.models;


import javax.swing.AbstractSpinnerModel;

public class SpinnerModel extends AbstractSpinnerModel {

	protected static enum Step {
		Big,Middle,Low
	}

	public static final float min = 0.1f;
	public static final float max = Float.MAX_VALUE;
	protected static float bigstep = 1f;
	protected static float middlestep = 0.5f;
	protected static float lowstep = 0.1f;
	
	protected float step = 1;
	protected float value = 1;
	protected Step stepType = Step.Low;
	
	public SpinnerModel()
	{
		checkValue();
	}
	
	@Override
	public Object getNextValue() {
		if (value == 1) stepType = Step.Middle;
		if (value == 10) stepType = Step.Big;
		updateStep();
		value += step;
		checkValue();
		return value;
	}
	
	protected void updateStep()
	{
		switch (stepType) {
		case Big:
			step = bigstep;
			break;
		case Low:
			step = lowstep;
			break;
		case Middle:
			step = middlestep;
			break;
		}
	}
	
	protected void checkValue(){
		int temp = (int) (value*10.0f);
		value = temp/10.0f;
		
		if (value > max) value = max;
		if (value < min) value = min;
		
		if (value > 10) stepType = Step.Big;
		if (value <= 10 && value >= 1 ) stepType = Step.Middle;
		if (value <= 1) stepType = Step.Low;
		
		updateStep();
		
	}

	@Override
	public Object getPreviousValue() {
		value -= step;
		checkValue();
		return value;
	}

	@Override
	public Object getValue() {
		checkValue();
		return (Float)value;
	}
	
	public int getIntValue() {
		checkValue();
		return (int)value;
	}
	
	public float getFloatValue()
	{
		checkValue();
		return value;
	}


	@Override
	public void setValue(Object arg0) {
		if (arg0 instanceof Float) value = (Float) arg0; else
	    if (arg0 instanceof Integer) value = (Integer) arg0; 	
		checkValue();
		fireStateChanged();
	}

}
