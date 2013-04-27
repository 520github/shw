package com.babeeta.appstore.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 标记
 * 
 * @author chongf
 * 
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Marker {
	public static class MarkerId {
		private String deviceId;
		private String domain;
		private String entityId;

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MarkerId) {
				MarkerId right = (MarkerId) obj;
				return (this.deviceId + this.domain + this.entityId + "")
						.equals(right.deviceId + right.domain + right.entityId
								+ "");
			} else {
				return super.equals(obj);
			}
		}

		public String getDeviceId() {
			return deviceId;
		}

		public String getDomain() {
			return domain;
		}

		public String getEntityId() {
			return entityId;
		}

		@Override
		public int hashCode() {
			return (this.deviceId + this.domain + this.entityId + "")
					.hashCode();
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public void setEntityId(String entityId) {
			this.entityId = entityId;
		}
	}

	private Date date;
	@Id
	private MarkerId id;

	private String value;

	public Date getDate() {
		return date;
	}

	public MarkerId getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(MarkerId id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
