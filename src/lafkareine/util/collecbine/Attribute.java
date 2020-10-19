
package lafkareine.util.collecbine;


import java.util.List;


public class Attribute<O, A> {
	
	public final O object;
	
	public final List<A> attribute;
	
	public Attribute(O object, List<A> attributes) {
		super();
		this.object = object;
		this.attribute = attributes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Attribute [object=");
		builder.append(object);
		builder.append(", attribute=");
		builder.append(attribute);
		builder.append("]");
		return builder.toString();
	}
}
