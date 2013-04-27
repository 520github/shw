package com.babeeta.appstore.entity;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexes;

/**
 * 统计某个实体的某种标记域的个数
 * 
 * @author chongf
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexes({ @Index("id.entityId") })
public class MarkerCounter {
	public static class MarkerCounterId {
		private String domain;
		private String entityId;

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MarkerCounterId) {
				MarkerCounterId right = (MarkerCounterId) obj;
				return (this.getDomain() + this.getEntityId() + "")
						.equals(right.getDomain() + right.getEntityId()
								+ "");
			} else {
				return super.equals(obj);
			}
		}

		public String getDomain() {
			return domain;
		}

		public String getEntityId() {
			return entityId;
		}

		@Override
		public int hashCode() {
			return (this.getDomain() + this.getEntityId() + "")
					.hashCode();
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public void setEntityId(String entityId) {
			this.entityId = entityId;
		}
	}

	private Map<String, Long> counter;
	@Id
	private MarkerCounterId id;

	public Map<String, Long> getCounter() {
		return counter;
	}

	public MarkerCounterId getId() {
		return id;
	}

	public void setCounter(Map<String, Long> counter) {
		this.counter = counter;
	}

	public void setId(MarkerCounterId id) {
		this.id = id;
	}
}
