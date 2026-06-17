package app.siphondsp.model.root

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import app.siphondsp.interop.JamesDspRemoteEngine
import app.siphondsp.model.IEffectSession

data class RemoteEffectSession(
    override var packageName: String,
    override var uid: Int,
    var effect: JamesDspRemoteEngine?
) : CoroutineScope by CoroutineScope(Dispatchers.Default), IEffectSession {
    override fun toString(): String {
        return "package=$packageName; uid=$uid"
    }
}