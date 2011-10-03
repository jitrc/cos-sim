package ru.cos.nissan.information;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import ru.cos.sim.communication.dto.VehicleDTO;
import ru.cos.sim.road.init.data.LinkLocationData;
import ru.cos.sim.road.init.data.NodeLocationData;
import ru.cos.sim.road.init.data.LocationData.LocationType;

public class CarInformatioArea {

	private JTable table ;
	private CarInfoModel model;
	
	private String[] columnsName = {
			"Variable" , "Value"
	};
	
	private String[] staticFirstColumn = {
			"ID" , "Vehicle ID" , "Class" ,
			"Speed"  , "Location", "Position",
			"Shift"
	};
	
	private String[] nodeLocation = {
			"CrossRoad" , "Transition Rule"
	};
	
	private String[] linkLocation = {
			"Link ID" , "Segment ID","Lane ID"
	};
	
	
	private VehicleDTO carDto;
	
	public CarInformatioArea() {
		super();
		
		this.model = new CarInfoModel();
		this.table = new JTable(model);
		
		 for (int i = 1 ; i < table.getColumnCount(); i++) {
         	table.getColumnModel().getColumn(i).setCellRenderer( new DefaultTableCellRenderer());
         	((DefaultTableCellRenderer)table.getColumnModel().getColumn(i).getCellRenderer()).
         	setHorizontalAlignment(SwingConstants.CENTER);
         }
	}
	
	public void update(VehicleDTO dto) {
		this.carDto = dto;
		this.model.fireTableDataChanged();
	}
	
	

	public JTable getTable() {
		return table;
	}



	class CarInfoModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			return columnsName.length;
		}

		@Override
		public int getRowCount() {
			if (carDto == null) return staticFirstColumn.length; else {
				if (carDto.getLocation().
						getLocationType()==LocationType.LinkLocation) 
					return staticFirstColumn.length + linkLocation.length;
				if (carDto.getLocation().
						getLocationType()==LocationType.NodeLocation) 
					return staticFirstColumn.length + nodeLocation.length;
			}
			
			return 0;
		}
		
		private Object getValueAtFirstColumn(int row) {
			if (row < staticFirstColumn.length) {
				return staticFirstColumn[row];
			} else {
				row -= staticFirstColumn.length;
				if (carDto.getLocation().
						getLocationType()==LocationType.LinkLocation) {
					return linkLocation[row];
				}
				
				if (carDto.getLocation().
						getLocationType()==LocationType.NodeLocation) {
					return nodeLocation[row];
				}
			}
			
			return "unknown";
		}
		
		private Object getValueAtSecondColumn(int row) {
			if (carDto == null) return "unknown";
			if (row < staticFirstColumn.length) {
				switch (row) {
				case 0 : return carDto.getAgentId();
				case 1 : return carDto.getVehicleId();
				case 2 : return carDto.getVehicleClass().name();
				case 3 : return carDto.getSpeed();
				case 4 : return carDto.getLocation().getLocationType().name();
				case 5 : return carDto.getLocation().getPosition();
				case 6 : return carDto.getLocation().getShift();
				default : return "unknown";
				}
			} else {
				row -= staticFirstColumn.length;
				if (carDto.getLocation().
						getLocationType()==LocationType.LinkLocation) {
					LinkLocationData data = (LinkLocationData) carDto.getLocation();
					switch (row) {
					case 0 : return data.getLinkId();
					case 1 : return data.getSegmentId();
					case 2 : return data.getLaneIndex();
					default : return "unknown";
					}
				}
				
				if (carDto.getLocation().
						getLocationType()==LocationType.NodeLocation) {
					NodeLocationData data = (NodeLocationData) carDto.getLocation();
					switch (row) {
					case 0 : return data.getNodeId();
					case 1 : return data.getTransitionRuleId();
					default : return "unknown";
					}
				}
			}
			
			return "unknown";
		}

		@Override
		public Object getValueAt(int row, int column) {
			if (column == 0) return getValueAtFirstColumn(row);
			if (column == 1) return getValueAtSecondColumn(row);
			return "Unknown";
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			return String.class;
		}

		@Override
		public String getColumnName(int column) {
			// TODO Auto-generated method stub
			return columnsName[column];
		}
		
		
		
	}
	
	
}
