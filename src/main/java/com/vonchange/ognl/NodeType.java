/**
 * 
 */
package com.vonchange.ognl;


import com.vonchange.ognl.enhance.OgnlExpressionCompiler;

/**
 * Used by some of the {@link OgnlExpressionCompiler} logic to determine the object
 * type of {@link Node}s during expression evaluation.
 */
public interface NodeType
{
    /**
     * The type returned from the expression - if any.
     *
     * @return The type.
     */
    Class getGetterClass();

    /**
     * The type used to set the value - if any.
     *
     * @return The type.
     */
    Class getSetterClass();
}
