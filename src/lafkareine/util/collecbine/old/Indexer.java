
package lafkareine.util.collecbine.old;


import static java.util.Spliterator.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class Indexer {

	public static <T> Stream<IndexCouple<T>> stream(Collection<T> collection) {
		return StreamSupport.stream(Spliterators.spliterator(iterable(collection).iterator(), collection.size(), IMMUTABLE | NONNULL | SIZED), false);
	}

	public static <T> Iterable<IndexCouple<T>> iterable(Collection<T> collection) {
		return new IndexIterator<T>(collection);
	}

	public static <T> List<IndexCouple<T>> list(Collection<T> collection) {
		int i = 0;
		List<IndexCouple<T>> list = new ArrayList<>(collection.size());
		for (T e : collection) {
			list.add(new IndexCouple<T>(i++, e));
		}
		return list;
	}

	public static <T> IndexCouple<T>[] array(Collection<T> collection) {
		int i = 0;
		IndexCouple<T>[] array = new IndexCouple[collection.size()];
		for (T e : collection) {
			array[i] = new IndexCouple<T>(i, e);
			i += 1;
		}
		return array;
	}

	private static final class IndexIterator<T> implements Iterable<IndexCouple<T>>, Iterator<IndexCouple<T>> {

		private final Iterator<T> base_iterator;

		private int index = 0;

		public IndexIterator(Collection<T> base) {
			// TODO 自動生成されたコンストラクター・スタブ
			base_iterator = base.iterator();
		}

		@Override
		public boolean hasNext() {
			// TODO 自動生成されたメソッド・スタブ
			return base_iterator.hasNext();
		}

		@Override
		public IndexCouple<T> next() {
			// TODO 自動生成されたメソッド・スタブ
			return new IndexCouple<>(index++, base_iterator.next());
		}

		@Override
		public Iterator<IndexCouple<T>> iterator() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

	}
}
