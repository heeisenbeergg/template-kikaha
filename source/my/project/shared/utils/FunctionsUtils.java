package my.project.shared.utils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class FunctionsUtils {

	public static <I, T> Map<I, T> index(Iterable<T> list, Function<T, I> keyFunction ) {
		return index( list, keyFunction, item -> item );
	}

	public static <I, T, X> Map<I, T> index( Iterable<X> list, Function<X, I> keyFunction, Function<X, T> dataFunction ) {
		final Map<I, T> map = new HashMap<>();
		for ( final X item : list )
			map.put( keyFunction.apply( item ), dataFunction.apply( item ) );
		return map;
	}

	public static <K, V> Map<K, List<V>> groupBy(Iterable<V> values, Function<V, K> keyMapper ) {
		final Map<K, List<V>> grouped = map();
		for ( final V value : values ) {
			final K key = keyMapper.apply( value );
			List<V> list = grouped.get( key );
			if ( list == null )
				grouped.put( key, list = list() );
			list.add( value );
		}
		return grouped;
	}

	private static <K> boolean isNotEmptyString( K key ) {
		return ( key instanceof String ) && !( (String)key ).isEmpty();
	}

	public static <T> List<T> filter( Iterable<T> items, Function<T, Boolean> matcher ) {
		final List<T> list = new ArrayList<>();
		for ( final T item : items )
			if ( matcher.apply( item ) )
				list.add( item );
		return list;
	}

	public static <T, I> Collection<T> filterUnique(Iterable<T> items, Function<T, I> keyFunction ) {
		final Map<I, T> map = new HashMap<>();
		for ( final T item : items )
			map.putIfAbsent( keyFunction.apply( item ), item );
		return map.values();
	}

	public static <T, I> Collection<I> filterUniqueByAttribute( Iterable<T> items, Function<T, I> keyFunction ) {
		final Map<I, T> map = new HashMap<>();
		for ( final T item : items )
			map.putIfAbsent( keyFunction.apply( item ), item );
		return new ArrayList<>(map.keySet());
	}

	public static <T, I> List<I> convert( Iterable<T> items, Function<T, I> keyFunction ) {
		final List<I> list = new ArrayList<>();
		for ( final T item : items )
			list.add( keyFunction.apply( item ) );
		return list;
	}

	public static <T> void sort( List<T> items, Comparator<T> comparator ) {
		Collections.sort( items, comparator );
	}

	public static <T> T first( List<T> items ) {
		for ( final T item : items )
			return item;
		return null;
	}

	public static boolean isUndefined( Long l ) {
		return l == null || l == 0L;
	}

	public static boolean isUndefined( String string ) {
		return string == null || string.isEmpty();
	}

	public static boolean isNull( Object l ) {
		return l == null;
	}

	public static <T> T value( T nullable, Supplier<T> defaultValue ) {
		return isNull( nullable ) ? defaultValue.get() : nullable;
	}

	public static <T> T value( T nullable, T defaultValue ) {
		return isNull( nullable ) ? defaultValue : nullable;
	}

	@SafeVarargs
	public static <T> Set<T> asSet( T... ts ) {
		final Set<T> set = new HashSet<>();
		for ( final T t : ts )
			set.add( t );
		return set;
	}

	public static <T> T first( Iterable<T> ts ) {
		for ( final T t : ts )
			return t;
		return null;
	}

	@SafeVarargs
	public static <T> T firstNonNull( T... ts ) {
		for ( final T t : ts )
			if ( t != null )
				return t;
		return null;
	}

	public static <T> T getOrCreate( T t, Supplier<T> constructor ) {
		if ( t == null )
			t = constructor.get();
		return t;
	}

	public static <E, V> V getIfNotNullOrDefault(E entity, V defaultValue, Function<E, V> function) {
		try {
			final V value = function.apply(entity);

			return value == null ? defaultValue : value;
		} catch (Throwable e) {
			return defaultValue;
		}
	}

	public static <K, V> Map<K, V> map() {
		return new HashMap<>();
	}

	public static <K, V> Map<K, V> map( K key, V value ) {
		final Map<K, V> map = new HashMap<>(){{
			put(key, value);
		}};

		return map;
	}

	public static <T> List<T> list() {
		return new ArrayList<>();
	}

	@SafeVarargs
	public static <T> List<T> list( T... ts ) {
		return Arrays.asList( ts );
	}

	public static <T> List<T> listFrom( Iterable<T> items ) {
		final List<T> list = list();
		for ( final T item : items )
			list.add( item );
		return list;
	}

	public static <T> String join( Iterable<T> ts, String delimiter ) {
		final StringBuilder buffer = new StringBuilder();
		for ( final T t : ts ) {
			if ( buffer.length() != 0 )
				buffer.append( delimiter );
			buffer.append( t.toString() );
		}
		return buffer.toString();
	}

}
