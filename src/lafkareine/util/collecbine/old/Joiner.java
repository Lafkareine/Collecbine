package lafkareine.util.collecbine.old;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Joiner {

	private static class SpliteratorImpl<T> implements Spliterator<T>{
		private final Spliterator<T>[] origin;
		private int origin_max;
		private int origin_index;
		private Spliterator<T> current;
		private long estimateSize;
		private int characteristics;

		private final static int mask = CONCURRENT|/*DISTINCT|*/IMMUTABLE|NONNULL|/*ORDERED|*/SIZED|/*SORTED|*/SUBSIZED;

		public SpliteratorImpl(Collection<T>[] origin) {
			this.origin = new Spliterator[origin.length];
			origin_max = origin.length;
			origin_index = 0;

			characteristics = mask;
			estimateSize = 0;
			for(int i =0;i<origin.length;i++){
				Collection<T> e = origin[i];
				Spliterator<T> f = e.spliterator();
				estimateSize += f.estimateSize();
				characteristics &= f.characteristics();
				this.origin[i] = f;
			}
		}

		private SpliteratorImpl(Spliterator<T>[] origin,int index) {
			this.origin = origin;
			this.origin_index = index;
			origin_max = origin.length;

			characteristics = mask;
			estimateSize = 0;
			for(int i =0;i<origin.length;i++){
				Spliterator<T> f = origin[i];
				estimateSize += f.estimateSize();
				characteristics &= f.characteristics();
			}
		}

		@Override
		public boolean tryAdvance(Consumer<? super T> action) {
			if(current.tryAdvance(action)){
				decreaseEstimateSize(1);
				return true;
			}else{
					origin_index += 1;
					current = origin[origin_index];
					if(origin_index == origin_max){
						return false;
					}
					return tryAdvance(action);

			}
		}

		private void decreaseEstimateSize(long i) {
			if (estimateSize != Long.MAX_VALUE) {
				estimateSize -= i;
			}
		}

		@Override
		public Spliterator<T> trySplit() {
			if(origin_index == origin_max){
				Spliterator<T> spl = current.trySplit();
				estimateSize = spl.estimateSize();
				return spl;
			}else if(origin_index + 1 == origin_max){
				Spliterator<T> spl = origin[--origin_max];
				decreaseEstimateSize(spl.estimateSize());
				return spl;
			}else{
				int sprit = (origin_index + origin_index) >> 2;
				Spliterator<T> spl = new SpliteratorImpl<T>(origin,sprit);
				origin_max = sprit;
				decreaseEstimateSize(spl.estimateSize());
				return spl;
			}
		}

		@Override
		public long estimateSize() {
			return ((characteristics&SIZED)==SIZED)?estimateSize:Long.MAX_VALUE;
		}

		@Override
		public int characteristics() {
			return characteristics;
		}
	}

	public static <T> Stream stream(Collection<T>... arg) {
		return StreamSupport.stream(new SpliteratorImpl<>(arg),false);
	}

	public static <T> Stream parallelStream(Collection<T>... arg) {
		return StreamSupport.stream(new SpliteratorImpl<>(arg),true);
	}

	private static class IteratorImpl<T> implements Iterable<T>, Iterator<T> {

		private final Collection<T>[] origin;
		private int origin_index = 0;
		private Iterator<T> current;

		public IteratorImpl(Collection<T>[] origin) {
			this.origin = origin;

		}

		@Override
		public Iterator<T> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			if(current.hasNext()){
				return true;
			}else {
				origin_index += 1;
				if (origin_index == origin.length) {
					return false;
				}else{
					current = origin[origin_index].iterator();
					return hasNext();
				}
			}
		}

		@Override
		public T next() {
			return current.next();
		}
	}

	public static <T> Iterable Iterator(Collection<T>... arg) {
		return new IteratorImpl(arg);
	}

	public static <T> List<T> list(Collection<T>... arg) {
		int size = 0;
		for(Collection e:arg){
			size += e.size();
		}
		ArrayList<T> result = new ArrayList<>(size);
		for(Collection e:arg){
			result.addAll(e);
		}
		return result;
	}
}
