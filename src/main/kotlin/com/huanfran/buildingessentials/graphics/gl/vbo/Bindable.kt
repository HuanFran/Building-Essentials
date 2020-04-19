package com.huanfran.buildingessentials.graphics.gl.vbo

/**
 * When using objects in OpenGL, it is common to have to 'bind' objects for use. Bound objects should then be 'unbound'
 * following their use. This interface represents an object that can have this functionality. It contains two
 * inheritable methods: [bind] and [unbind]. These methods are not typically used by themselves. Rather, they are
 * encapsulated in the [getBound] and [doBound] methods. These methods use lambdas that are executed while the object
 * is bound, making the process of binding, using, and unbinding the object far simpler.
 */
interface Bindable {


    fun bind()

    fun unbind()



    fun<T> getBound(thing: () -> T) : T {
        bind()
        val value = thing.invoke()
        unbind()
        return value
    }



    fun doBound(thing: () -> Unit) {
        bind()
        thing.invoke()
        unbind()
    }


}