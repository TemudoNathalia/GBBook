package br.com.books.enums;

import java.io.Serializable;

/**
 * Default interface of enums
 * 
 * @author Nathalia Temudo
 * @param <T>
 */
public interface EnumValue<T extends Serializable> {

	/**
	 * Method that returns a value of Enum
	 * @return
	 */
	T getValue();
}

