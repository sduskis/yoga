package org.skyscreamer.yoga.demo.controller;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.skyscreamer.yoga.demo.dao.GenericDao;
import org.skyscreamer.yoga.populator.FieldPopulator;
import org.skyscreamer.yoga.selector.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: Carter Page
 */
public abstract class AbstractController<T>
{
	@Autowired GenericDao _genericDao;
	
	Class<T> _entityClass = returnedClass();

	@RequestMapping("/{id}")
    public @ResponseBody Map<String,Object> get( @PathVariable long id, Selector selector )
    {
        T obj = _genericDao.find( _entityClass, id);
        Map<String,Object> dto = getAbstractFieldPopulator().populateObjectFields( obj, selector );
		return dto;
    }

    // http://blog.xebia.com/2009/02/acessing-generic-types-at-runtime-in-java/
    @SuppressWarnings("unchecked")
	private Class<T> returnedClass()
    {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    protected abstract FieldPopulator<T> getAbstractFieldPopulator();
}