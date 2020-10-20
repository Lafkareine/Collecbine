
package lafkareine.util.collecbine.old;


import static java.util.Spliterator.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/** Group Byに近い動作を提供します */
public class Attributer {

	private Attributer() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static <O, A> Stream<Attribute<O, A>> stream(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return objects.stream().map(o -> new Attribute<>(o, attributes.stream().filter(a -> test.test(o, a)).collect(Collectors.toList())));
	}

	public static <O, A> Stream<Attribute<O, A>> stream2(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return StreamSupport.stream(Spliterators.spliterator(iterable(objects, attributes, test).iterator(), objects.size(), IMMUTABLE | NONNULL | SIZED), false);
	}

	public static <O, A> Iterable<Attribute<O, A>> iterable(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return new AttributeIterator<>(objects, attributes, test);
	}

	public static <O, A> List<Attribute<O, A>> list(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return stream(objects, attributes, test).collect(Collectors.toList());
	}

	public static <O, A> Attribute<O, A>[] array(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return stream(objects, attributes, test).toArray(i -> new Attribute[i]);
	}

	private static final class AttributeIterator<O, A> implements Iterable<Attribute<O, A>>, Iterator<Attribute<O, A>> {

		private final Iterator<O> iteratorO;

		private final Collection<A> collectionA;

		private final BiPredicate<O, A> test;

		public AttributeIterator(Collection<O> CollectionO, Collection<A> collectionA, BiPredicate<O, A> test) {
			super();
			this.iteratorO = CollectionO.iterator();
			this.collectionA = collectionA;
			this.test = test;
		}

		@Override
		public Iterator<Attribute<O, A>> iterator() {
			// TODO 自動生成されたメソッド・スタブ
			return this;
		}

		@Override
		public boolean hasNext() {
			// TODO 自動生成されたメソッド・スタブ
			return iteratorO.hasNext();
		}

		@Override
		public Attribute<O, A> next() {
			// TODO 自動生成されたメソッド・スタブ
			O object = iteratorO.next();
			List<A> attributes = collectionA.stream().filter(attribute -> test.test(object, attribute)).collect(Collectors.toList());
			return new Attribute<O, A>(object, attributes);
		}

	}
}
