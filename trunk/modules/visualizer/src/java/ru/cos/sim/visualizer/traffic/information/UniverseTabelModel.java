package ru.cos.sim.visualizer.traffic.information;

import javax.swing.tree.TreePath;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import ru.cos.sim.communication.dto.StatisticsData;

public class UniverseTabelModel extends AbstractTreeTableModel
{
	private TreeItem myroot;
	private TreeItem universe;
	protected TreeItem universeTime;
	protected TreeItem numberOfVehicles;
	protected TreeItem numberOfAliveOrigins;
	protected TreeItem averageSpeed;
	protected TreeItem totalTime;
	private TreeItem totalStops;
	
	private TreeItem linkId;
	private TreeItem nodeId;
	private TreeItem segId;
	private TreeItem speed;
	private TreeItem carUID;
	private TreeItem laneId;
	private TreeItem carinfoNode;
	private TreeItem carinfoLink;
	protected StatisticsData data;
	

	public UniverseTabelModel()
	{
            //JXTreeTable t = new JXTreeTable(this);
		myroot = new TreeItem( "root", "Information" );

		carinfoNode = new TreeItem("Car","");
		carinfoLink = new TreeItem("Car","");
		
		nodeId = new TreeItem("Node ID","");
		speed = new TreeItem("Speed","");
		linkId = new TreeItem("Link ID","");
		segId = new TreeItem("Seg ID","");
		laneId = new TreeItem("Lane ID","");
		carUID = new TreeItem("ID","");

		carinfoNode.getChildren().add(carUID);
		carinfoNode.getChildren().add(nodeId);
		carinfoNode.getChildren().add(speed);
		
		carinfoLink.getChildren().add(carUID);
		carinfoLink.getChildren().add(linkId);
		carinfoLink.getChildren().add(segId);
		carinfoLink.getChildren().add(laneId);
		carinfoLink.getChildren().add(speed);

		
		TreeItem universe = new TreeItem( "Universe",
		  "" );
		universeTime =new TreeItem( "Time",
		  "" );
		universe.getChildren().add( universeTime );
		this.numberOfVehicles =  new TreeItem( "Number of vehicles",
		  "" );
		universe.getChildren().add( this.numberOfVehicles);
		this.numberOfAliveOrigins = new TreeItem( "Number of alive origins",
		  "" );
		universe.getChildren().add( this.numberOfAliveOrigins );
		this.averageSpeed = new TreeItem( "Average speed",
		  "" );
		universe.getChildren().add( this.averageSpeed );
		this.totalTime = new TreeItem( "Total time",
		  "" );
		universe.getChildren().add( this.totalTime );
		this.totalStops = new TreeItem( "Total stops",
		  "" );
		universe.getChildren().add(this.totalStops  );
		
		myroot.getChildren().add( universe );
		
	}
	
	public void update(StatisticsData data,TreePath path)
	{
		String def = "";
		this.universeTime.value = (data == null) ? def : String.valueOf(data.getUniverseTime());
		this.numberOfAliveOrigins.value = (data == null) ? def : String.valueOf(data.getNumberOfAliveOrigins());
		this.numberOfVehicles.value = (data == null) ? def : String.valueOf(data.getNumberOfVehicles());
		this.averageSpeed.value = (data == null) ? def : String.valueOf(data.getAverageSpeed());
		this.totalStops.value = (data == null) ? def : String.valueOf(data.getTotalStops());
		this.totalTime.value = (data == null) ? def : String.valueOf(data.getTotalTime());
		this.modelSupport.firePathLeafStateChanged(path);
	}

	
	public void updateCarInformation(TreePath path,int linkId,int segId, float speed,int laneId,int uid)
	{
		this.linkId.value = String.valueOf(linkId);
		this.segId.value = String.valueOf(segId);
		this.speed.value = String.valueOf(speed);
		this.laneId.value = String.valueOf(laneId);
		this.carUID.value = String.valueOf(uid);
		if (this.myroot.getChildren().contains(this.carinfoNode)) {
			this.myroot.getChildren().remove(this.carinfoNode);
			this.myroot.getChildren().add(this.carinfoLink);
		} else {
			if (!this.myroot.getChildren().contains(this.carinfoLink)) {
				this.myroot.getChildren().add(this.carinfoLink);
			}
		}
		this.modelSupport.firePathLeafStateChanged(path);
	}


	public void updateCarInformation(TreePath path,int nodeId, float speed, int uid)
	{
		this.nodeId.value = String.valueOf(nodeId);
		this.speed.value = String.valueOf(speed);
		this.carUID.value = String.valueOf(uid);
		if (this.myroot.getChildren().contains(this.carinfoLink)) {
			this.myroot.getChildren().remove(this.carinfoLink);
			this.myroot.getChildren().add(this.carinfoNode);
		} else {
			if (!this.myroot.getChildren().contains(this.carinfoNode)) {
				this.myroot.getChildren().add(this.carinfoNode);
			}
		}
		
		this.modelSupport.firePathLeafStateChanged(path);
	}
	
	public int getColumnCount()
	{
		return 2;
	}

	@Override
	public String getColumnName( int column )
	{
		switch( column )
		{
		case 0: return "Information";
		case 1: return "Value";
		default: return "Unknown";
		}
	}

	@Override
	public Object getValueAt( Object node, int column )
	{
		TreeItem treenode = ( TreeItem )node;
		switch( column )
		{
		case 0: return treenode.getName();
		case 1: return treenode.getDescription();
		case 2: return treenode.getChildren().size();
		default: return "Unknown";
		}
	}

	@Override
	public Object getChild( Object node, int index )
	{
		TreeItem treenode = ( TreeItem )node;
		return treenode.getChildren().get( index );
	}

	@Override
	public int getChildCount( Object parent )
	{
		TreeItem treenode = ( TreeItem )parent;
		return treenode.getChildren().size();
	}

	@Override
	public int getIndexOfChild( Object parent, Object child )
	{
		TreeItem treenode = ( TreeItem )parent;
		for( int i=0; i>treenode.getChildren().size(); i++ )
		{
			if( treenode.getChildren().get( i ) == child )
			{
				return i;
			}
		}

		return 0;
	}

	 public boolean isLeaf( Object node )
	 {
		 TreeItem treenode = ( TreeItem )node;
		 if( treenode.getChildren().size() > 0 )
		 {
			 return false;
		 }
		 return true;
	 }

	 @Override
	 public Object getRoot()
	 {
		 return myroot;
	 }
}
