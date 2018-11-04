/**
 *  ____                          _   _       _    __
 * | __ )  __ _ _ __   ___ ___   | | | |_ __ (_) _/_/ _ __
 * |  _ \ / _` | '_ \ / __/ _ \  | | | | '_ \| |/ _ \| '_ \
 * | |_) | (_| | | | | (_| (_) | | |_| | | | | | (_) | | | |
 * |____/ \__,_|_| |_|\___\___/   \___/|_| |_|_|\___/|_| |_|
 *  ____                            _           ____
 * |  _ \ _ __ ___  _   _  ___  ___| |_ ___    / ___|___  _ __ ___
 * | |_) | '__/ _ \| | | |/ _ \/ __| __/ _ \  | |   / _ \| '__/ _ \
 * |  __/| | | (_) | |_| |  __/ (__| || (_) | | |__| (_) | | |  __/
 * |_|   |_|  \___/ \__, |\___|\___|\__\___/   \____\___/|_|  \___|
 *                  |___/
 *
 * Copyright © 2017 - http://union.dev/licence.txt#yracnet
 */
package bo.union.platform.i16d.manager.data;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import bo.union.police.PoliceBase;
import java.util.ArrayList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Grouper extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name;
	@XmlElement
	private String description;
	@XmlElement
	private Grouper parent;
	@XmlElement
	private Long grouperId;
	@XmlElement
	private List<Grouper> grouperList;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}

	public Long getIdParent() {
		return parent == null ? null : parent.grouperId;
	}

	public Grouper getParent() {
		return parent;
	}

	public void setParent(Grouper parent) {
		this.parent = parent;
	}

	public Long getGrouperId() {
		return grouperId;
	}

	public void setGrouperId(Long value) {
		grouperId = value;
	}

	public List<Grouper> getGrouperList() {
		return grouperList;
	}

	public void setGrouperList(List<Grouper> grouperList) {
		this.grouperList = grouperList;
	}

	public void addGrouper(Grouper grouper) {
		if (grouperList == null) {
			grouperList = new ArrayList<>();
		}
		grouperList.add(grouper);
	}
}
