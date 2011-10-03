package ru.cos.frame;


/**
 * Represents frame data about link. Contains segments, that are currently visible.
 *  
 * @author Dudinov Ivan
 */
public class LinkFrameData extends CommonFrameData {

	protected int visibleCount = 0;
	protected Integer uuid;
	
	/**
	 * Simple Constructor. Initialize data.
	 * @param id - id of the Link
	 */
	public LinkFrameData(int id) {
		super();
		this.uuid = new Integer(id);
	}

	/**
	 * Returns true if there are any visible segments in the link; If no visible
	 * segments in the link , returns false;
	 * @return
	 */
	public boolean isVisible()
	{
		return visibleCount > 0;
	}
	
	/**
	 * @return id of the link
	 */
	public Integer getUuid() {
		return uuid;
	}
	
	@Override
	public void changeToInVisible(ViewableObjectInformation info)
	{
		if (info.visible == true) {
			super.changeToInVisible(info);
			
			this.visibleCount--;
		}
	}
	
	@Override
	public void changeToVisible(ViewableObjectInformation info)
	{
		if (info.visible == false) {
			super.changeToVisible(info);
			this.visibleCount++;
		}
	}
	
}
