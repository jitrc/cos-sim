package ru.cos.sim.visualizer.traffic.information;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import ru.cos.sim.visualizer.trace.item.Meter;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;

public class MetersList {
	
	public JTable list;
	protected MetersTableModel metersModel;
	protected boolean initialised = false;
	
	private String[] columnNames = {
		"ID" , "Name" , "Type"	
	};
	
	private Class[] columnTypes = {
			 Integer.class, String.class , String.class	
		};
	
	public MetersList()
	{
		this.metersModel = new MetersTableModel();
		this.list = new JTable(metersModel);
		
		for (int i = 0 ; i < list.getColumnCount(); i++) {
			list.getColumn(columnNames[i]).setCellRenderer(new DefaultTableCellRenderer());
			((DefaultTableCellRenderer)list.getColumn(columnNames[i]).getCellRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		}
		//this.list.setBorder(javax.swing.BorderFactory.createTitledBorder("Meters"));

		this.list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					if (list.isRowSelected(arg0.getFirstIndex())) {
						addGraph(metersModel.getMeter(arg0.getFirstIndex()));
						return;
					}
					if (list.isRowSelected(arg0.getLastIndex())) {
						addGraph(metersModel.getMeter(arg0.getLastIndex()));
					}
				}
			}
		});
	}
	
	
	public void init()
	{	
		this.metersModel.update(SimulationSystemManager.getInstance().getTraceHandler().getMetersManager().getMeters());
	}
/*	public void update()
	{
		if (ConditionManager.getInstance().getMapState() != States.MAP_LOADED) return;
		if (!initialised) updateData();
	}
	*/
	
	public void dispose()
	{
		this.initialised = false;
	}
	

	
/*	public void updateData()
	{
		VisualController.getInstance().requestAvailableMeters();
		List<ShortMeterDTO> meters =  VisualController.getInstance().getAvailableMeters();
		
		if (meters == null) {
			this.metersModel.setData(new Meter[0]);
			//VisualController.getInstance().requestAvailableMeters();
			return;
		}

		Meter[] items = new Meter[meters.size()];
		int index = 0;
		for (ShortMeterDTO meter : meters) {
			Meter item = new Meter(meter.getName(), meter.getId());
			items[index] = item;
			index++;
		}
		this.metersModel.setData(items);
		initialised = true;
	}*/
	
	protected void addGraph(Meter meter)
	{
		SimulationSystemManager.getInstance().getTraceHandler().getMetersManager().addGraph(meter);
	}
	
	protected class MetersTableModel extends AbstractTableModel {

		private ArrayList<Meter> meters;
		
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			if (meters != null) return meters.size();
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (meters == null) return null;
			
			switch (columnIndex) {
			case 0 : return meters.get(rowIndex).id();
			case 1 : return meters.get(rowIndex).name;
			case 2 : return meters.get(rowIndex).type; 
			}
			
			return "unknown";
		}
		
		
	
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			return columnTypes[columnIndex];
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		public void update(ArrayList<Meter> meters) {
			this.meters = meters;

			fireTableRowsUpdated(0,meters.size());
			fireTableRowsInserted(0, meters.size());
		}
		
		public Meter getMeter(int row) {
			return meters.get(row);
		}
		
	}
}
	

