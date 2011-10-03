package ru.cos.nissan.core;

public interface Controller {

	void actionPerformed(String action);
	ConditionManager.Action getActionType();
	
}
