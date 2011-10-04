package ru.cos.sim.visualizer.traffic.gui.models;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		 Component comp = table.getDefaultRenderer(table.getColumnClass(1)).getTableCellRendererComponent(
                 table, value, isSelected, hasFocus, row, column );
		return comp;
	}

}
