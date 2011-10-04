package ru.cos.sim.visualizer.traffic.information;

import javax.swing.table.AbstractTableModel;

import ru.cos.sim.communication.dto.StatisticsData;

public class CarsTableModel extends AbstractTableModel {

	private String[] columnNames = { "Variable", "Value" };

	private String[] firstColumn = { "Universe Time", "Total Time", "Vehicles",
			"Arrived vehicles", "Alive origins", "Average speed", "TotalStops" };

	private StatisticsData data;

	public CarsTableModel() {
		super();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {

		return firstColumn.length;
	}

	private Object getData(int rowCount) {
		if (data == null)
			return null;
		switch (rowCount) {
		case 0:
			return data.getUniverseTime();
		case 1:
			return data.getTotalTime();
		case 2:
			return data.getNumberOfVehicles();
		case 3:
			return data.getNumberOfArrivedVehicles();
		case 4:
			return data.getNumberOfAliveOrigins();
		case 5:
			return data.getAverageSpeed();
		case 6:
			return data.getTotalStops();
		}

		return null;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (arg1 == 0) {
			return firstColumn[arg0];
		}

		if (arg1 == 1 && data != null) {
			return getData(arg0);
		}
		return "unknown";
	}

	public void update(StatisticsData data) {
		this.data = data;
		this.fireTableRowsUpdated(0, firstColumn.length - 1);
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int c) {
		return String.class;
	}

}
