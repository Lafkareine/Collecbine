
package lafkareine.util.collecbine;


public class IndexCouple<T> {

	public final int index;

	public final T object;

	public IndexCouple(int index, T object) {
		super();
		this.index = index;
		this.object = object;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IndexTapple [index=");
		builder.append(index);
		builder.append(", t=");
		builder.append(object);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		IndexCouple other = (IndexCouple) obj;
		if (index != other.index)
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		}
		return true;
	}


}
