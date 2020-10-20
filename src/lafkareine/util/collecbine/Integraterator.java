package lafkareine.util.collecbine;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public abstract class Integraterator<T> implements Iterator<T>, Spliterator<T> {

	@Override
	public final void forEachRemaining(Consumer<? super T> action) {
		if (hasNext()) {
			action.accept(next());
		}
	}

	@Override
	public final boolean tryAdvance(Consumer<? super T> action) {
		if (hasNext()) {
			action.accept(next());
			return true;
		}
		return false;
	}
}
