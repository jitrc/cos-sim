package ru.cos.sim.road.init.factories;

import ru.cos.sim.road.init.data.SignData;
import ru.cos.sim.road.init.data.SpeedLimitSignData;
import ru.cos.sim.road.objects.NoSpeedLimitSign;
import ru.cos.sim.road.objects.Sign;
import ru.cos.sim.road.objects.Sign.SignType;
import ru.cos.sim.road.objects.SpeedLimitSign;

/**
 * 
 * @author zroslaw
 */
public class SignFactory {

	public static Sign createSign(SignData signData) {
		SignType signType = signData.getSignType();
		Sign sign = null;
		
		switch (signType){
		case SpeedLimitSign:
			SpeedLimitSign speedLimitSign = new SpeedLimitSign();
			SpeedLimitSignData speedLimitSignData = (SpeedLimitSignData)signData;
			speedLimitSign.setSpeedLimit(speedLimitSignData.getSpeedLimit());
			sign = speedLimitSign;
			break;
		case NoSpeedLimitSign:
			sign = new NoSpeedLimitSign();
			break;
		}
		
		return sign;
	}

}
