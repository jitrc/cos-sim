/**
 * 
 */
package ru.cos.zroslaw.utils;

/**
 * Left or right hand, side, direction, etc.
 * @author zroslaw
 */
public enum Hand {
	Left,
	Right;

	public Hand invert() {
		if(this==null) return null;
		return this.equals(Left)?Right:Left;
	}
}
