package com.yzchat.socket.utils

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

object RxBus {

    private val eventBusProvider = hashMapOf<Class<*>, FlowableProcessor<*>>()

    @JvmStatic
    fun <T : Any> register(clazz: Class<T>): Flowable<T> {
        if (eventBusProvider[clazz] == null) {
            eventBusProvider[clazz] = PublishProcessor.create<T>().toSerialized()
        }
        @Suppress("UNCHECKED_CAST")
        return eventBusProvider[clazz]!! as Flowable<T>
    }

    @JvmStatic
    fun <T : Any> post(message: T) {
        @Suppress("UNCHECKED_CAST")
        (eventBusProvider[message.javaClass] as FlowableProcessor<T>?)?.onNext(message)
    }

    @JvmStatic
    fun <T : Any> unRegister(clazz: Class<T>) {
        if (null != eventBusProvider[clazz]) {
            eventBusProvider.remove(clazz)
        }
    }
}